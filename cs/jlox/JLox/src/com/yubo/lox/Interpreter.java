package com.yubo.lox;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

/**
 * Use post-order travel to evaluate the AST. Notice each node's children
 * must be evaluated before itself. Therefore, it is post-order traversal.
 */
public class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {
    /**
     * Represents current environment. May not be global env.
     */
    final Environment globals = new Environment();
    private Environment env = globals;
    /**
     * Side table to store extra information about tree Node. By doing so,
     * we don't have to mess around with Tree data structure.
     */
    private final Map<Expr, Integer> locals = new HashMap<>();

    {
        globals.define("clock", new LoxCallable() {
            @Override
            public Object call(Interpreter interpreter, List<Object> args) {
                return (double) System.currentTimeMillis() / 1000.0;
            }

            @Override
            public int arity() {
                return 0;
            }

            @Override
            public String toString() {
                return "<native fn clock>";
            }
        });
    }

    public void interpret(List<Stmt> stmts) {
        try {
            for (Stmt stmt : stmts) {
                execute(stmt);
            }
        } catch (RuntimeError e) {
            Lox.runtimeError(e);
        }
    }

    private String stringify(Object o) {
        if (o == null) return "nil";
        if (o instanceof Double) {
            String text = o.toString();
            if (text.endsWith(".0")) {
                // Just get rid of .0 for integers.
                text = text.substring(0, text.length() - 2);
            }
            return text;
        }
        return o.toString();
    }

    @Override
    public Object visit(Expr.Assign assign) {
        Object val = evaluate(assign.value);
        Integer dist = locals.getOrDefault(assign, null);
        // We want always resolve to same variable, and assign them.
        if (dist != null) {
            env.assignAt(dist, assign.name, val);
        } else {
            globals.assign(assign.name, val);
        }

        // Since we allow nested assignment, e.g.,
        // a = 2 return 2 to enable that.
        return val;
    }

    /**
     * Evaluate binary expression. Notice left is evaluated before right,
     * because sometimes, user put some stuff with side effect in the expression.
     *
     * @param binary to be evaluated
     * @return result
     */
    @Override
    public Object visit(Expr.Binary binary) {
        Object left = evaluate(binary.left);
        Object right = evaluate(binary.right);

        return switch (binary.operator.type()) {
            case MINUS:
                checkNumberOperand(binary.operator, left, right);
                yield (double) left - (double) right;
            case SLASH:
                checkNumberOperand(binary.operator, left, right);
                if ((double) right == 0.0) {
                    throw new RuntimeError(binary.operator, "Divide by zero.");
                }
                yield (double) left / (double) right;
            case STAR:
                checkNumberOperand(binary.operator, left, right);
                yield (double) left * (double) right;
            case PLUS:
                // I like implicit conversion to str
                if (left instanceof String || right instanceof String) {
                    yield stringify(left) + stringify(right);
                } else {
                    checkNumberOperand(binary.operator, left, right);
                    yield (double) left + (double) right;
                }
            case GREATER:
                yield compare(binary, left, right, r -> r > 0);
            case GREATER_EQUAL:
                yield compare(binary, left, right, r -> r >= 0);
            case LESS:
                yield compare(binary, left, right, r -> r < 0);
            case LESS_EQUAL:
                yield compare(binary, left, right, r -> r <= 0);
            case BANG_EQUAL:
                yield compare(binary, left, right, r -> r != 0);
            case EQUAL_EQUAL:
                yield compare(binary, left, right, r -> r == 0);
            default:
                // Unreachable
                yield null;
        };
    }

    private boolean compare(Expr.Binary binary, Object left, Object right, IntFunction<Boolean> predicate) {
        checkComparableOperand(binary.operator, left, right);
        try {
            return predicate.apply(((Comparable) left).compareTo(right));
        } catch (ClassCastException e) {
            throw new RuntimeError(binary.operator, "Operand must be same type to be compared.");
        }
    }

    private void checkNumberOperand(Token operator, Object... operands) {
        for (Object operand : operands)
            if (!(operand instanceof Double)) throw new RuntimeError(operator, "Operand must be a number.");
    }

    private void checkComparableOperand(Token operator, Object... operands) {
        for (Object operand : operands)
            if (!(operand instanceof Comparable<?>)) throw new RuntimeError(operator, "Operand must be comparable.");
    }


    @Override
    public Object visit(Expr.Grouping grouping) {
        return evaluate(grouping.expression);
    }

    @Override
    public Object visit(Expr.Literal literal) {
        return literal.value;
    }

    @Override
    public Object visit(Expr.Unary unary) {
        Object right = evaluate(unary.right);
        return switch (unary.operator.type()) {
            case MINUS:
                checkNumberOperand(unary.operator, right);
                yield -(double) right;
            case PLUS:
                checkNumberOperand(unary.operator, right);
                yield (double) right;
            case BANG:
                yield !isTruthy(right);
            default:
                // This never happen.
                yield null;
        };
    }

    @Override
    public Object visit(Expr.Variable variable) {
        return lookUpVariable(variable.name, variable);
    }

    @Override
    public Object visit(Expr.Logical logical) {
        Object left = evaluate(logical.left);

        // Short circuit. We don't always evaluate right!
        // By doing that, one can do "" or "hello" to get "hello"
        // and "" and "hello" to get ""
        // isTruthy helps!
        if (logical.operator.type() == TokenType.OR) {
            if (isTruthy(left)) return left;
        } else {
            if (!isTruthy(left)) return left;
        }

        return evaluate(logical.right);
    }

    @Override
    public Object visit(Expr.Set set) {
        // Evaluate the object
        Object obj = evaluate(set.object);
        // If it is not an instance of a class, raise RuntimeError
        if (!(obj instanceof LoxInstance instance)) {
            throw new RuntimeError(set.name, "Only instances have fields.");
        } else {
            // Evaluate the value to be assigned
            Object val = evaluate(set.val);
            instance.set(set.name, val);
            return val;
        }
    }

    @Override
    public Object visit(Expr.ThisKw thiskw) {
        return lookUpVariable(thiskw.kw, thiskw);
    }

    @Override
    public Object visit(Expr.Anonymous anonymous) {
        return new LoxAnonymousFunc(anonymous, env);
    }

    @Override
    public Object visit(Expr.Call call) {
        Object callee = evaluate(call.callee);
        // In this implementation, user should not be surprised if
        // their args are not evaluated in order, through Stream usually do.
        if (!(callee instanceof LoxCallable function)) {
            throw new RuntimeError(call.paren, "Can only call functions and classes.");
        }
        if (call.arguments.size() != function.arity()) {
            throw new RuntimeError(call.paren, "Expected %d arguments but got %d.".formatted(call.arguments.size(), function.arity()));
        }
        return function.call(this, call.arguments.stream().map(this::evaluate).toList());
    }

    @Override
    public Object visit(Expr.Get get) {
        Object obj = evaluate(get.object);
        if (obj instanceof LoxInstance) {
            return ((LoxInstance) obj).get(get.name);
        }
        throw new RuntimeError(get.name, "Only instances have properties.");
    }

    @Override
    public Object visit(Expr.SuperKw superkw) {
        int dist = locals.get(superkw);
        // This is always directly enclosed inside super
        LoxClass superclass = (LoxClass) env.getAt(dist, "super");
        LoxInstance obj = (LoxInstance) env.getAt(dist - 1, "this");
        LoxFunction method = superclass.findMethod(superkw.method.lexeme());
        if (method == null) {
            throw new RuntimeError(superkw.method, "Undefined property '" + superkw.method.lexeme() + "'.");
        }
        return method.bind(obj);
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean) object;
        if (object instanceof String) return ((String) object).length() == 0;
        if (object instanceof Double) return (double) object == 0.0;
        return true; // This is unreachable, pretty much we covered all types.
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public Void visit(Stmt.Block block) {
        executeBlock(block.stmts, new Environment(env));
        return null;
    }

    public void executeBlock(List<Stmt> stmts, Environment env) {
        Environment previous = this.env;
        try {
            this.env = env;
            for (Stmt stmt : stmts) {
                execute(stmt);
            }
        } finally {
            this.env = previous;
        }
    }

    @Override
    public Void visit(Stmt.Expression expression) {
        evaluate(expression.expression);
        return null;
    }

    @Override
    public Void visit(Stmt.IfStmt ifstmt) {
        if (isTruthy(evaluate(ifstmt.condition))) execute(ifstmt.thenBranch);
        else if (ifstmt.elseBranch != null) execute(ifstmt.elseBranch);
        return null;
    }

    @Override
    public Void visit(Stmt.Print print) {
        Object value = evaluate(print.expression);
        System.out.println(stringify(value));
        return null;
    }

    @Override
    public Void visit(Stmt.Var var) {
        Object value = null;
        if (var.initializer != null) {
            value = evaluate(var.initializer);
        }
        env.define(var.name.lexeme(), value);
        return null;
    }

    @Override
    public Void visit(Stmt.WhileStmt whilestmt) {
        while (isTruthy(evaluate(whilestmt.condition))) {
            execute(whilestmt.body);
        }
        return null;
    }

    @Override
    public Void visit(Stmt.Function function) {
        LoxFunction fn = new LoxFunction(function, env, false);
        env.define(function.name.lexeme(), fn);
        return null;
    }

    @Override
    public Void visit(Stmt.ReturnStmt returnstmt) {
        Object val = null;
        if (returnstmt.val != null)
            val = evaluate(returnstmt.val);
        throw new Return(val);
    }

    @Override
    public Void visit(Stmt.ClassStmt classstmt) {
        Object superclass = null;
        if (classstmt.superclass != null) {
            superclass = evaluate(classstmt.superclass);
            if (!(superclass instanceof LoxClass)) {
                throw new RuntimeError(classstmt.superclass.name, "Superclass must be a class.");
            }
        }

        env.define(classstmt.name.lexeme(), null);

        if (classstmt.superclass != null) {
            env = new Environment(env);
            env.define("super", superclass);
        }

        Map<String, LoxFunction> methods = new HashMap<>();
        for (Stmt.Function method : classstmt.methods) {
            // If superclass != null, super class env/closure is passed to loxFunction
            LoxFunction function = new LoxFunction(method, env, method.name.lexeme().equals("init"));
            methods.put(method.name.lexeme(), function);
        }

        if (superclass != null) {
            env = env.enclosing;
        }

        LoxClass cls = new LoxClass(classstmt.name.lexeme(), (LoxClass) superclass, methods);
        env.assign(classstmt.name, cls);
        return null;
    }

    private void execute(Stmt stmt) {
        stmt.accept(this);
    }

    public void resolve(Expr expr, int depth) {
        locals.put(expr, depth);
    }

    private Object lookUpVariable(Token name, Expr expr) {
        Integer distance = locals.getOrDefault(expr, null);
        if (distance != null) {
            return env.getAt(distance, name.lexeme());
        } else {
            return globals.get(name);
        }
    }
}
