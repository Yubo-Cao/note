package com.yubo.lox;

import java.util.List;
import java.util.Map;

public class LoxClass implements LoxCallable {
    private final String name;
    private final Map<String, LoxFunction> methods;
    private final LoxClass superclass;

    public LoxClass(String name, LoxClass superclass, Map<String, LoxFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    @Override
    public String toString() {
        return "<cls " + name + ">";
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        var inst = new LoxInstance(this);
        LoxFunction init = findMethod("init");
        if (init != null) {
            init.bind(inst).call(interpreter, args);
        }
        return inst;
    }

    @Override
    public int arity() {
        LoxFunction init = findMethod("init");
        if (init == null) return 0;
        return init.arity();
    }

    public LoxFunction findMethod(String name) {
        var res = methods.getOrDefault(name, null);
        if (res == null && superclass != null) {
            return superclass.findMethod(name);
        }
        return res;
    }
}
