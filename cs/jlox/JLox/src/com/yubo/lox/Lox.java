package com.yubo.lox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.yubo.lox.TokenType.EOF;

public class Lox {
    private static boolean hadError = false;
    private static boolean hadRuntimeError = false;

    private static final Interpreter interpreter = new Interpreter();

    private Lox() {
    }

    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runPrompt() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("> ");
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                run(line);
                hadError = false; // If user make a mistake, don't quit
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(63);
        }

    }

    private static void runFile(String path) {
        var str = new StringBuilder();
        var line = "";
        try (BufferedReader input = new BufferedReader(new FileReader(path))) {
            while ((line = input.readLine()) != null) {
                str.append(line);
                str.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(63);
        }
        run(str.toString());
        if (hadError) System.exit(65);
        if (hadRuntimeError) System.exit(70);
    }

    private static void run(String src) {
        Scanner scanner = new Scanner(src);
        Parser parser = new Parser(scanner.scanTokens());
        List<Stmt> stmts = parser.parse();

        if (hadError) return;

        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(stmts);

        if (hadError) return;
        interpreter.interpret(stmts);
    }


    public static void error(int line, String message) {
        report(line, "", message);
    }

    static void error(Token token, String message) {
        if (token.type() == EOF) {
            Lox.report(token.line(), " at end", message);
        } else {
            Lox.report(token.line(), " at '" + token.lexeme() + "'", message);
        }
    }

    public static void report(int line, String where, String message) {
        hadError = true;
        System.err.printf("[line %d] Error %s: %s\n", line, where, message);
    }

    public static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\b [line" + error.token.line() + "]");
        hadRuntimeError = true;
    }
}
