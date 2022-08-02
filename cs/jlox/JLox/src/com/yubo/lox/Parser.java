package com.yubo.lox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.yubo.lox.TokenType.*;


public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Parser promise to return Syntax tree if input eis legit. However,
     * null is returned if input is wrong.
     *
     * @return usable syntax tree to best attempt.
     */
    public List<Stmt> parse() {
        List<Stmt> stmts = new ArrayList<>();
        while (!isAtEnd()) {
            stmts.add(declaration());
        }
        return stmts;
    }

    private Stmt declaration() {
        try {
            if (match(CLASS)) return classDeclaration();
            if (match(VAR)) return varDeclaration();
            if (match(FUN))
                if (check(IDENTIFIER)) {
                    return function("function");
                } else {
                    current--; // Back to fun
                    return exprStmt();
                }
            return statement();
        } catch (ParseError e) {
            // Our parser repeatedly try to parse Declaration/Statements
            // therefore, here is right place to put synchronize/error recovery
            synchronize();
            return null;
        }
    }

    private Stmt classDeclaration() {
        Token name = consume(IDENTIFIER, "Expect class name after 'class'.");
        Expr.Variable superclass = null;
        if (match(LESS)) {
            consume(IDENTIFIER, "Expect super class name.");
            superclass = new Expr.Variable(previous());
        }
        consume(LEFT_BRACE, "Expect '{' before class body.");

        List<Stmt.Function> methods = new ArrayList<>();
        while (!check(RIGHT_BRACE) && !isAtEnd()) {
            methods.add(function("method"));
        }
        consume(RIGHT_BRACE, "Expect '}' after class body");
        return new Stmt.ClassStmt(name, superclass, methods);
    }

    private Stmt.Function function(String kind) {
        Token name = consume(IDENTIFIER, "Expect %s name.".formatted(kind));
        List<Token> parameters = new ArrayList<>();
        consume(LEFT_PAREN, "Expect '(' for %s declaration.".formatted(kind));
        if (!check(RIGHT_PAREN)) {
            do {
                if (parameters.size() >= 255) {
                    error(peek(), "Can't have more than 255 parameters.");
                }
                parameters.add(consume(IDENTIFIER, "Expect parameter name."));
            } while (match(COMMA));
        }
        consume(RIGHT_PAREN, "Expect ')' after parameters");

        consume(LEFT_BRACE, "Expect '{' before %s body.".formatted(kind));
        List<Stmt> body = block();
        return new Stmt.Function(name, parameters, body);
    }

    private Stmt varDeclaration() {
        Token name = consume(IDENTIFIER, "Expect variable name.");
        Expr initializer = null;
        if (match(EQUAL)) {
            initializer = expression();
        }
        consume(SEMICOLON, "Expect ';' after variable declaration.");
        return new Stmt.Var(name, initializer);
    }

    private Stmt statement() {
        if (match(PRINT)) return printStmt();
        if (match(LEFT_BRACE)) return new Stmt.Block(block());
        if (match(IF)) return ifStmt();
        if (match(WHILE)) return whileStmt();
        if (match(FOR)) return forStmt();
        if (match(RETURN)) return returnStmt();
        return exprStmt();
    }

    private Stmt returnStmt() {
        Token kw = previous();
        // We check if return value is absent, and doing that instead.
        Expr val = (check(SEMICOLON)) ? null : expression();
        consume(SEMICOLON, "Expect ';' after return value.");
        return new Stmt.ReturnStmt(kw, val);
    }

    private Stmt whileStmt() {
        consume(LEFT_PAREN, "Expect '(' after 'while'.");
        Expr condition = expression();
        consume(RIGHT_PAREN, "Expect ')' after while condition.");
        Stmt body = declaration();

        return new Stmt.WhileStmt(condition, body);
    }

    private Stmt forStmt() {
        consume(LEFT_PAREN, "Expect '(' after 'for'.");
        Stmt init = (match(SEMICOLON)) ? null : (match(VAR)) ? varDeclaration() : exprStmt();

        Expr cond = (!check(SEMICOLON)) ? expression() : new Expr.Literal(true);
        consume(SEMICOLON, "Expect ';' after terminal condition expression.");

        Expr increment = (!check(RIGHT_PAREN)) ? expression() : null;
        consume(RIGHT_PAREN, "Expect ')' after update expression.");

        Stmt body = statement();

        if (increment != null) {
            body = new Stmt.Block(
                    Arrays.asList(body, new Stmt.Expression(increment))
            );
        }
        // Now body is while stuff.
        body = new Stmt.WhileStmt(cond, body);
        if (init != null) {
            body = new Stmt.Block(List.of(init, body));
        }
        return body;
    }

    private Stmt ifStmt() {
        consume(LEFT_PAREN, "Expect '(' after 'if'.");
        Expr condition = expression();
        consume(RIGHT_PAREN, "Expect ')' after if condition.");

        Stmt thenBranch = statement();
        Stmt elseBranch = null;
        // Dangling else problem
        // This parser would eagerly find ELSE. Hence, ELSE is always paired with the closest IF.
        if (match(ELSE)) {
            elseBranch = statement();
        }
        return new Stmt.IfStmt(condition, thenBranch, elseBranch);
    }

    private List<Stmt> block() {
        List<Stmt> stmts = new ArrayList<>();
        while (!check(RIGHT_BRACE) && !isAtEnd()) {
            // This would avoid infinite loops.
            // Sometime, user forgets }, and without isAtEnd
            // make it stuck.
            stmts.add(declaration());
        }
        consume(RIGHT_BRACE, "Expect '}' after block.");
        return stmts;
    }

    private Stmt exprStmt() {
        Expr value = expression();
        consume(SEMICOLON, "Expect ';' after value.");
        return new Stmt.Expression(value);
    }

    private Stmt printStmt() {
        Expr value = expression();
        consume(SEMICOLON, "Expect ';' after value.");
        return new Stmt.Print(value);
    }

    private Expr expression() {
        return assignment();
    }

    /**
     * Assignment parsing. This has lower precedence over or
     * and and, and thus, expr = or() now.
     */
    private Expr assignment() {
        // Efficient assignment parser
        // single variable look ahead without backtracking

        // Left can be any kind of expression.
        Expr expr = or();

        if (match(EQUAL)) {
            // Get equal token
            Token equals = previous();
            // Assignment is right associative
            // hence, no WHILE, but rather recursively call assignment

            // Right can be assignment, so be recursive
            Expr value = assignment();

            // If it is a variable, for left, do it. Otherwise, report error.
            if (expr instanceof Expr.Variable var) {
                return new Expr.Assign(var.name, value);
            } else if (expr instanceof Expr.Get get) {
                return new Expr.Set(get.object, get.name, value);
            }
            error(equals, "Invalid l-value.");
        }

        return expr;
    }

    private Expr or() {
        Expr expr = and();

        while (match(OR)) {
            Token op = previous();
            Expr r = and();
            expr = new Expr.Logical(expr, op, r);
        }

        return expr;
    }

    private Expr and() {
        Expr expr = equality();
        while (match(AND)) {
            Token operator = previous();
            Expr right = equality();
            expr = new Expr.Logical(expr, operator, right);
        }
        return expr;
    }

    private Expr equality() {
        Expr expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }
        // If no equality expression are here, it effectively means,
        // return comparison, or anything of higher precedence
        return expr;
    }

    private Expr comparison() {
        Expr expr = term();

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr term() {
        Expr expr = factor();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr factor() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr unary() {
        if (match(BANG, MINUS, PLUS)) {
            // This parser look ahead to see if there are any !-+
            // make it a predict parser
            // But it just calling itself to build layers and layers of Unary Tree
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }
        return call();
    }

    // function call, (), is an expression.
    // It has higher precedence over unary, and one obviously, can use function call
    // to create function, and call that. Such makes this left-associative

    // call -> primary ("(" arguments? ")" )*; This is called currying style, partial function
    // and partial functions ...
    // arguments -> expression ( "," expression ) *

    private Expr call() {
        Expr expr = primary();

        while (true) {
            if (match(LEFT_PAREN)) {
                expr = finishCall(expr);
            } else if (match(DOT)) {
                Token name = consume(IDENTIFIER, "Expect property name after '.'.");
                expr = new Expr.Get(expr, name);
            } else {
                break;
            }
        }
        return expr;
    }

    private Expr finishCall(Expr callee) {
        List<Expr> arguments = new ArrayList<>();
        if (!check(RIGHT_PAREN)) {
            // If there are any arguments
            do {
                if (arguments.size() >= 255) {
                    // No throw, parser still know what it's doing.
                    error(peek(), "Can't have more than 255 arguments.");
                }
                // get an expression, then see if comma.
                arguments.add(expression());
            } while (match(COMMA));
        }

        Token paren = consume(RIGHT_PAREN, "Expect ')' after arguments.");
        return new Expr.Call(callee, paren, arguments);
    }


    private Expr primary() {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal());
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            /*
            - Detect and report error
            - Avoid crash itself or handing here
            - It should report as any error as it can in one time, and avoid cascaded errors, i.e., '
            recovery itself and not report gibberish.
            - Finally, it should be fast
             */
            // Error recovery to handle SyntaxErrors. Enter panic mode, and try to find another
            // token that aligned with syntax rule, i.e., **Synchronization**
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }
        if (match(IDENTIFIER)) {
            return new Expr.Variable(previous());
        }
        if (match(THIS)) {
            return new Expr.ThisKw(previous());
        }

        if(match(SUPER)){
            Token kw = previous();
            consume(DOT, "Expect '.' after 'super'.");
            Token method = consume(IDENTIFIER, "Expect superclass method name.");
            return new Expr.SuperKw(kw, method);
        }

        if (match(FUN)) {
            Token kw = previous();
            consume(LEFT_PAREN, "Expect '(' after anonymous function declaration.");
            List<Token> params = new ArrayList<>();
            if (!check(RIGHT_PAREN)) {
                do {
                    params.add(consume(IDENTIFIER, "Expect parameter after '('."));
                } while (match(COMMA));
            }
            consume(RIGHT_PAREN, "Expect ')' after function header.");
            consume(LEFT_BRACE, "Expect '{' after function header.");
            List<Stmt> body = block();
            return new Expr.Anonymous(kw, params, body);
        }
        throw error(peek(), "Expect expression.");
    }

    /**
     * Try to synchronize back to next possible stmt's boundry. Discard any token in between.
     */
    private void synchronize() {

        advance();

        while (!isAtEnd()) {
            if (previous().type() == SEMICOLON) return;
            switch (peek().type()) {
                case CLASS, FUN, VAR, FOR, IF, WHILE, PRINT, RETURN -> {
                    return;
                }
                default -> {
                }
            }
            advance();
        }
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw error(peek(), message);
    }

    private ParseError error(Token token, String messsage) {
        Lox.error(token, messsage);
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {
        static final long serialVersionUID = -2312134234234L;
    }

    /**
     * Checks to see if current token has any of the given types. If so, this
     * token is consumed and return true. Otherwise, return false and nothing happens.
     *
     * @param types expected
     * @return does any is expected.
     */
    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return false;
        }
        return tokens.get(current).type() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type() == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    /**
     * Return previous token. This function assume previous token exists.
     *
     * @return previous token.
     */
    private Token previous() {
        return tokens.get(current - 1);
    }
}
