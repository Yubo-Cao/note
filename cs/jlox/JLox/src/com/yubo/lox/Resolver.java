package com.yubo.lox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Resolver implements Expr.Visitor<Void>, Stmt.Visitor<Void> {

    private final Interpreter interpreter;
    /**
     * True and False refer to whether we find initializer for that variable.
     */
    private final Stack<Map<String, Boolean>> scopes = new Stack<>();

    private FunctionType currentFunction = FunctionType.NONE;
    private ClassType currentClass = ClassType.NONE;

    public Resolver(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public Void visit(Expr.Assign assign) {
        resolve(assign.value);
        resolveLocal(assign, assign.name);
        return null;
    }

    @Override
    public Void visit(Expr.Binary binary) {
        resolve(binary.left);
        resolve(binary.right);
        return null;
    }

    @Override
    public Void visit(Expr.Grouping grouping) {
        resolve(grouping.expression);
        return null;
    }

    @Override
    public Void visit(Expr.Literal literal) {
        return null;
    }

    @Override
    public Void visit(Expr.Unary unary) {
        resolve(unary.right);
        return null;
    }

    @Override
    public Void visit(Expr.Variable variable) {
        if (!scopes.isEmpty() && scopes.peek().get(variable.name.lexeme()) == Boolean.FALSE) {
            // it has been declared, but not initialized.
            Lox.error(variable.name, "Can't refer uninitialized variable %s.".formatted(variable.name));
        }
        resolveLocal(variable, variable.name);
        return null;
    }

    private void resolveLocal(Expr expr, Token name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name.lexeme())) {
                // Return hop count to find that variable, e.g., 0 for in insert most.
                // expr is value of name.
                interpreter.resolve(expr, scopes.size() - 1 - i);
                return;
            }
        }
    }

    private void declare(Token name) {
        if (scopes.isEmpty()) return;
        Map<String, Boolean> scope = scopes.peek();
        // since scope only store local envs. You are not allowed
        // to re-declare a variable in local scope, but in global, its ok.
        if (scope.containsKey(name.lexeme())) {
            Lox.error(name, "Already a variable with this name in this scope.");
        }
        scope.put(name.lexeme(), false);
    }

    @Override
    public Void visit(Expr.Logical logical) {
        resolve(logical.right);
        resolve(logical.left);
        return null;
    }

    @Override
    public Void visit(Expr.Set set) {
        resolve(set.val);
        resolve(set.object);
        return null;
    }

    @Override
    public Void visit(Expr.ThisKw thiskw) {
        if (currentClass == ClassType.NONE) {
            Lox.error(thiskw.kw, "Can't use 'this' outside of a class");
        } else {
            resolveLocal(thiskw, thiskw.kw);
        }
        return null;
    }

    @Override
    public Void visit(Expr.Anonymous anonymous) {
        return null;
    }

    @Override
    public Void visit(Expr.Call call) {
        resolve(call.callee);
        for (Expr argument : call.arguments) {
            resolve(argument);
        }
        return null;
    }

    @Override
    public Void visit(Expr.Get get) {
        resolve(get.object); // object before has to be existed
        // actual stuff happens in interpreter
        return null;
    }

    @Override
    public Void visit(Expr.SuperKw superkw) {
        if (currentClass == ClassType.NONE) {
            Lox.error(superkw.method, "Can't use 'super' outside of a class.");
        } else if (currentClass != ClassType.SUBCLASS) {
            Lox.error(superkw.method, "Can't use 'super' in a class with no super class.");
        }
        resolveLocal(superkw, superkw.kw);
        return null;
    }

    @Override
    public Void visit(Stmt.Block block) {
        beginScope();
        resolve(block.stmts);
        endScope();
        return null;
    }

    private void endScope() {
        scopes.pop();
    }

    private void resolve(Stmt stmt) {
        stmt.accept(this);
    }

    private void resolve(Expr expr) {
        expr.accept(this);
    }

    void resolve(List<Stmt> stmts) {
        for (Stmt stmt : stmts) {
            resolve(stmt);
        }
    }

    private void beginScope() {
        scopes.push(new HashMap<>());
    }

    @Override
    public Void visit(Stmt.Expression expression) {
        resolve(expression.expression);
        return null;
    }

    @Override
    public Void visit(Stmt.IfStmt ifstmt) {
        // Notice is analyze all possible branches
        resolve(ifstmt.condition);
        resolve(ifstmt.thenBranch);
        if (ifstmt.elseBranch != null) resolve(ifstmt.elseBranch);
        return null;
    }

    @Override
    public Void visit(Stmt.Print print) {
        resolve(print.expression);
        return null;
    }

    @Override
    public Void visit(Stmt.Var var) {
        declare(var.name);
        if (var.initializer != null) {
            resolve(var.initializer);
        }
        define(var.name);
        return null;
    }

    private void define(Token name) {
        if (scopes.isEmpty()) return;
        scopes.peek().put(name.lexeme(), true);
    }

    @Override
    public Void visit(Stmt.WhileStmt whilestmt) {
        resolve(whilestmt.condition);
        resolve(whilestmt.body);
        return null;
    }

    @Override
    public Void visit(Stmt.Function function) {
        declare(function.name);
        define(function.name);

        resolveFunction(function, FunctionType.FUNCTION);
        return null;
    }


    private void resolveFunction(Stmt.Function function, FunctionType type) {
        FunctionType enclosingFunction = currentFunction;
        currentFunction = type;

        beginScope();
        for (Token param : function.params) {
            declare(param);
            define(param);
        }
        resolve(function.body);
        endScope();

        currentFunction = enclosingFunction;
    }

    @Override
    public Void visit(Stmt.ReturnStmt returnstmt) {
        if (currentFunction == FunctionType.NONE) {
            Lox.error(returnstmt.kw, "Can't return from top-level code.");
        }
        if (returnstmt.val != null) {
            if (currentFunction == FunctionType.INITIALIZER) {
                Lox.error(returnstmt.kw, "Can't return a value from an initializer.");
            }
            resolve(returnstmt.val);
        }
        return null;
    }

    @Override
    public Void visit(Stmt.ClassStmt classstmt) {
        ClassType enclosingClass = currentClass;
        currentClass = ClassType.CLASS;
        declare(classstmt.name);
        define(classstmt.name);

        if (classstmt.superclass != null && classstmt.name.lexeme().equals(classstmt.superclass.name.lexeme())) {
            Lox.error(classstmt.superclass.name, "A class can't inherit from itself.");
        }

        if (classstmt.superclass != null) {
            currentClass = ClassType.SUBCLASS;
            resolve(classstmt.superclass);
        }

        if (classstmt.superclass != null) {
            beginScope();
            scopes.peek().put("super", true);
        }

        beginScope();
        scopes.peek().put("this", true);
        for (Stmt.Function method : classstmt.methods) {
            FunctionType declaration = FunctionType.METHOD;
            if (method.name.lexeme().equals("init")) {
                declaration = FunctionType.INITIALIZER;
            }
            resolveFunction(method, declaration);
        }
        endScope();

        if (classstmt.superclass != null) endScope();

        currentClass = enclosingClass;
        return null;
    }
}
