package com.yubo.lox;

import java.util.List;
import java.util.stream.IntStream;

public class LoxAnonymousFunc implements LoxCallable {
    private final Expr.Anonymous declaration;
    private final Environment closure;

    public LoxAnonymousFunc(Expr.Anonymous declaration, Environment closure) {
        this.closure = closure;
        this.declaration = declaration;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        Environment env = new Environment(closure);
        IntStream.range(0, arity()).forEach(i -> env.define(declaration.params.get(i).lexeme(), args.get(i)));
        try {
            interpreter.executeBlock(declaration.body, env);
        } catch (Return val) {
            return val.val;
        }
        return null;
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }
}
