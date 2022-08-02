package com.yubo.lox;

public class Return extends RuntimeException {
    final Object val;

    Return(Object val) {
        // this disabled jvm features, e.g., write to stack,
        // suppress this, default msg and throwable.
        super(null, null, false, false);
        this.val = val;
    }
}
