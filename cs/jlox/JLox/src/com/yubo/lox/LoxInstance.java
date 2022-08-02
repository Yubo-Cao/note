package com.yubo.lox;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LoxInstance {
    private LoxClass cls;
    // Methods are bind methods, i.e., with instance. Therefore, method remember the object.
    private final Map<String, Object> attrs = new HashMap<>();

    public LoxInstance(LoxClass cls) {
        this.cls = cls;
    }

    @Override
    public String toString() {
        return cls + "(" + attrs
                .entrySet().stream()
                .map(kv -> "%s=%s".formatted(kv.getKey().toString(), kv.getValue().toString()))
                .collect(Collectors.joining(", ")) + ")";
    }

    public Object get(Token name) {
        if (attrs.containsKey(name.lexeme())) {
            return attrs.get(name.lexeme());
        }

        // Instance field shadows cls method
        LoxFunction method = cls.findMethod(name.lexeme());
        if(method != null) return method.bind(this);

        throw new RuntimeError(name, "Undefined property '" + name.lexeme() + "'.");
    }

    public void set(Token name, Object val) {
        attrs.put(name.lexeme(), val);
    }
}
