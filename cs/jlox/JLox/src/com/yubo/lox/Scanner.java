package com.yubo.lox;

import java.util.ArrayList;

import static java.util.Map.entry;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import static com.yubo.lox.TokenType.*;

public class Scanner {
    private int start = 0;
    private int current = 0;
    private int line = 1;

    private final String src;
    private final List<Token> tokens = new ArrayList<>();
    private static final Map<String, TokenType> keywords = Map.ofEntries(
            entry("and", AND),
            entry("class", CLASS),
            entry("else", ELSE),
            entry("false", FALSE),
            entry("for", FOR),
            entry("fun", FUN),
            entry("if", IF),
            entry("nil", NIL),
            entry("or", OR),
            entry("print", PRINT),
            entry("return", RETURN),
            entry("super", SUPER),
            entry("this", THIS),
            entry("true", TRUE),
            entry("var", VAR),
            entry("while", WHILE)
    );

    public Scanner(String src) {
        this.src = src;
    }

    public List<Token> scanTokens() {
        while (!end()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean end() {
        return current >= src.length();
    }

    private void scanToken() {
        char c = src.charAt(current++);
        switch (c) {
            case '(' -> addToken(LEFT_PAREN, "(");
            case ')' -> addToken(RIGHT_PAREN, ")");
            case '{' -> addToken(LEFT_BRACE);
            case '}' -> addToken(RIGHT_BRACE);
            case ',' -> addToken(COMMA);
            case '.' -> addToken(DOT);
            case '-' -> addToken(MINUS);
            case '+' -> addToken(PLUS);
            case ';' -> addToken(SEMICOLON);
            case '*' -> addToken(STAR);
            case '!' -> addToken(match('=') ? BANG_EQUAL : BANG);
            case '=' -> addToken(match('=') ? EQUAL_EQUAL : EQUAL);
            case '<' -> addToken(match('=') ? LESS_EQUAL : LESS);
            case '>' -> addToken(match('=') ? GREATER_EQUAL : GREATER);
            case '/' -> {
                // This need special care because comment
                if (match('/')) {
                    // All the comments are ignored in our implementation.
                    while (peek() != '\n' && !end()) {
                        // This charAt(current + 1) is called lookahead. One character look ahead.
                        current++;
                    }
                } else if (match('*')) {
                    // Multiple comment
                    var stack = new Stack<>();
                    stack.push("/*");
                    while (!stack.isEmpty() && !end()) {
                        if (peek() == '*' && current < src.length() - 1 && src.charAt(current + 1) == '/') {
                            stack.pop();
                            current++;
                        } else if (peek() == '/' && current < src.length() - 1 && src.charAt(current + 1) == '*') {
                            stack.push("/*");
                        } else if (peek() == '\n') {
                            line++;
                        }
                        current++;
                    }
                    if (!stack.isEmpty()) {
                        Lox.error(line, "Unterminated comment.");
                    }
                } else {
                    addToken(SLASH);
                }
            }
            // white spaces, expect new line, are ignored.
            case ' ', '\r', '\t' -> {
            }
            case '"' -> stringLiteral();
            case '\n' -> line++;
            case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> numberLiteral();
            // Keyword
            // Maximal munch. If two lexical grammar rule can both match a chunk of code that
            // the scanner is looking at, whichever one matches the most characters wins
            case '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' -> {
                identifierOrKeyword();
            }

            default -> {
                // Notice even so, the current character is still consumed. Thus, we won't stick here
                Lox.error(line, "Unexpected character %c.".formatted(c));
            }
        }
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private boolean isNumeric(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isIdentifierPart(char c) {
        return isAlpha(c) || c == '_' || isNumeric(c);
    }

    private char peek() {
        if (current >= src.length()) {
            return '\0';
        } else {
            return src.charAt(current);
        }
    }

    /**
     * This method attempt to match the next character. If success, it advanced the current offset.
     * and return true. Otherwise, it does not and return false.
     *
     * @param expected what character do you expect?
     * @return whether successfully matched the expected character or not.
     */
    private boolean match(char expected) {
        if (end() || src.charAt(current) != expected) {
            return false;
        }
        current++;
        return true;
    }

    private void stringLiteral() {
        while (peek() != '"' && !end()) {
            if (peek() == '\n') line++;
            current++;
        }
        if (end()) {
            Lox.error(line, "Unterminated string.");
            return;
        }
        current++;
        String value = src.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private void numberLiteral() {
        while (Character.isDigit(peek())) {
            current++;
        }
        if (peek() == '.' && current < src.length() - 1 && Character.isDigit(src.charAt(current + 1))) {
            current++;
            while (Character.isDigit(peek())) {
                current++;
            }
        }
        addToken(NUMBER, Double.parseDouble(src.substring(start, current)));
    }

    private void identifierOrKeyword() {
        while (isIdentifierPart(peek())) {
            current++;
        }
        String text = src.substring(start, current);
        TokenType type = keywords.getOrDefault(text, IDENTIFIER);
        addToken(type, (type == IDENTIFIER) ? text : null);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = src.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
