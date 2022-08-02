package com.yubo.lox;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> values = new HashMap<>();
    // We do not use token, because only name matter.
    final Environment enclosing;

    public Environment() {
        enclosing = null;
    }

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    public void define(String identifier, Object val) {
        // Internal use only.
        values.put(identifier, val);
    }

    public Object get(Token name) {
        // This is checked as a RuntimeError
        // because, you don't want to forward declaration like C and Pascal do.
        if (values.containsKey(name.lexeme()))
            return values.get(name.lexeme());
        else if (enclosing != null) return enclosing.get(name);
        else
            throw new RuntimeError(name, "Undefined variable '%s'.".formatted(name.lexeme()));
    }

    public void assign(Token name, Object val) {
        if (values.containsKey(name.lexeme())) {
            values.put(name.lexeme(), val);
            return;
        }
        // If not found in this environment, probably it refers to inner.
        if (enclosing != null) {
            enclosing.assign(name, val);
            return;
        }
        throw new RuntimeError(name, "Undefined variable '%s'.".formatted(name.lexeme()));
    }

    public Object getAt(Integer distance, String name) {
        return ancestor(distance).values.get(name);
    }

    private Environment ancestor(int dist) {
        Environment env = this;
        for (int i = 0; i < dist; i++) {
            assert env != null;
            env = env.enclosing;
        }
        return env;
    }

    public void assignAt(Integer dist, Token name, Object val) {
        ancestor(dist).values.put(name.lexeme(), val);
    }


    /*
    Handle lexical scope/static scope now!
    - Static scope: know in compile time
    - Dynamic scope: can't know until runtime

    Parent pointer tree. Pointer to outer environment.
     */
}
