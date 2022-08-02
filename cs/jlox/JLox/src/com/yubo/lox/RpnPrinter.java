package com.yubo.lox;

public abstract class RpnPrinter implements Expr.Visitor<String> {
    public String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visit(Expr.Binary binary) {
        return String.format("%s %s %s",
                binary.left.accept(this),
                binary.right.accept(this),
                binary.operator.lexeme());
    }

    @Override
    public String visit(Expr.Grouping grouping) {
        return String.format("(%s)",
                grouping.expression.accept(this));
    }

    @Override
    public String visit(Expr.Literal literal) {
        return literal.value.toString();
    }

    @Override
    public String visit(Expr.Unary unary) {
        return String.format("(%s%s)", unary.operator.lexeme(), unary.right.accept(this));
    }
}
