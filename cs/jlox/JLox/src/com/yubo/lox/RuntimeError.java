package com.yubo.lox;

public class RuntimeError extends RuntimeException {
    public final Token token;
    static final long serialVersionUID = -703123143423234324L;

    public RuntimeError(Token token, String msg) {
        super(msg);
        this.token = token;
    }
}
