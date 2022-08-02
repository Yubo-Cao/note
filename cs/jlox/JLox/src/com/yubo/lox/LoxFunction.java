package com.yubo.lox;

import java.util.List;

public class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Environment closure;
    private final boolean isInit;

    public LoxFunction(Stmt.Function declaration, Environment closure, boolean isInit) {
        this.closure = closure;
        this.declaration = declaration;
        this.isInit = isInit;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        // Each function get it's own environment, otherwise
        // we will break recursion. Also, concurrency demand that.
        Environment env = new Environment(this.closure);
        for (int i = 0; i < declaration.params.size(); i++) {
            env.define(declaration.params.get(i).lexeme(), args.get(i));
        }
        try {
            interpreter.executeBlock(declaration.body, env);
        } catch (Return returnVal) {
            if (isInit) return closure.getAt(0, "this");
            return returnVal.val;
        }
        if (isInit) return closure.getAt(0, "this");
        // Default return null.
        return null;
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public String toString() {
        return "<fn %s >".formatted(declaration.name.lexeme());
    }

    public LoxFunction bind(LoxInstance loxInstance) {
        Environment env = new Environment(closure);
        env.define("this", loxInstance);
        return new LoxFunction(declaration, env, this.isInit);
    }
}
