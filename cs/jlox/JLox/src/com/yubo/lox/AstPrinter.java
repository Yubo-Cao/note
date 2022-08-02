package com.yubo.lox;

import java.util.Arrays;

/**
 * Basically, it recursively converts the tree structure
 * to parenthesized string. Every expression itself, accept
 * it, which iterate the tree down to top, and itself, accumulate
 * those stuff together to yield result.
 */
public abstract class AstPrinter implements Expr.Visitor<String> {
    public String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visit(Expr.Binary binary) {
        return parenthesize(binary.operator.lexeme(), binary.left, binary.right);
    }

    @Override
    public String visit(Expr.Grouping grouping) {
        return parenthesize("group", grouping.expression);
    }

    @Override
    public String visit(Expr.Literal literal) {
        if (literal.value == null) return "nil";
        return literal.value.toString();
    }

    @Override
    public String visit(Expr.Unary unary) {
        return parenthesize(unary.operator.lexeme(), unary.right);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder(exprs.length << 2);
        builder.append("(").append(name);
        Arrays.stream(exprs).forEach(expr -> {
            builder.append(" ");
            builder.append(expr.accept(this));
        });
        builder.append(")");
        return builder.toString();
    }
}
