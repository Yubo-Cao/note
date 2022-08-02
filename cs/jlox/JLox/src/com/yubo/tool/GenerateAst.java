package com.yubo.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    private static PrintWriter writer;
    private static int indent = 0;

    public static void main(String[] args) throws IOException {
        String dir = "C:\\Users\\Cao20\\note\\cs\\jlox\\JLox\\src\\com\\yubo\\lox";
        defineAst(dir, "Expr", Arrays.asList(
                "Assign   : Token name, Expr value",
                "Binary   : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal  : Object value",
                "Unary    : Token operator, Expr right",
                "Variable : Token name",
                "Logical  : Expr left, Token operator, Expr right",
                "Set      : Expr object, Token name, Expr val",
                "ThisKw   : Token kw",
                // assignment -> ( call "." )? IDENTIFIER "=" assignment | logic_or;
                "Anonymous: Token kw, List<Token> params, List<Stmt> body",
                // call -> primary ( "(" args? ")" | "." IDENTIFIER )*;
                "Call     : Expr callee, Token paren, List<Expr> arguments",
                // paren is used to report error caused by function call
                "Get      : Expr object, Token name",
                "SuperKw   : Token kw, Token method"
        ));
        defineAst(dir, "Stmt", Arrays.asList(
                "Block      : List<Stmt> stmts",
                "Expression : Expr expression",
                "IfStmt     : Expr condition, Stmt thenBranch, Stmt elseBranch",
                "Print      : Expr expression",
                "Var        : Token name, Expr initializer",
                "WhileStmt  : Expr condition, Stmt body", // "while" "(" expr ")" stmt;
                //  "ForStmt    : Stmt init, Expr cond, Expr update, Stmt body" // "for" "(" expr? ";" expr? ";" expr? ")" stmt;
                // Notice for is just a syntactic sugar, which make some common code patterns more pleasant to write
                // Therefore, it desugared to while, rather than new node.
                "Function   : Token name, List<Token> params, List<Stmt> body",
                // declaration -> funDecl | varDecl | statement
                // funDecl -> "fun" function;
                // function -> IDENTIFIER "(" ( IDENTIFIER ( "," IDENTIFIER)*)? ")" block;
                "ReturnStmt : Token kw, Expr val",
                // returnStmt -> "return" expr? ";";
                "ClassStmt      : Token name, Expr.Variable superclass, List<Stmt.Function> methods"
                // class -> "class" IDENTIFIER "{" function* "}"
        ));
    }

    private static void defineAst(String dir, String base, List<String> types) throws IOException {
        String path = dir + "/" + base + ".java";

        try (var writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            GenerateAst.writer = writer;
            print("package com.yubo.lox;");
            space();
            print("import java.util.List;");
            space();
            print("public abstract class " + base + " {");
            defineVisitor(types);
            space();
            print("public abstract <R> R accept(Visitor<R> visitor);");
            space();
            types.stream()
                    .map(type -> type.split(":"))
                    .forEach(type -> {
                        String cls = type[0].trim();
                        String fields = type[1].trim();
                        defineType(base, cls, fields);
                        space();
                    });
            print("}");
        }
    }

    private static void defineType(String baseName, String className, String fields) {
        print("public static class " + className + " extends " + baseName + " {");
        Arrays.stream(fields.split(", ")).forEach(field -> print("public final " + field.trim() + ";"));
        space();
        print("public " + className + "(" + fields + ") {");
        Arrays.stream(fields.split(", ")).forEach(field -> print("this." + field.split(" ")[1] + " = " + field.split(" ")[1] + ";"));
        print("}");
        space();
        print("@Override");
        print("public <R> R accept(Visitor<R> visitor) {");
        print("return visitor.visit(this);");
        print("}");
        print("}");
    }

    private static void defineVisitor(List<String> types) {
        print("public interface Visitor<R> {");
        types.stream()
                .map(type -> type.split(":")[0].trim())
                .forEach(cls -> print("R visit(" + cls + " " + cls.toLowerCase() + ");"));
        print("}");
    }

    private static void space() {
        print("");
    }

    private static void print(String str) {
        print(str, "\n");
    }

    private static void print(String str, String end) {
        if (str.contains("{")) {
            writer.print("\t".repeat(indent) + str + end);
            indent++;
        } else if (str.contains("}")) {
            indent--;
            writer.print("\t".repeat(indent) + str + end);
        } else {
            writer.print("\t".repeat(indent) + str + end);
        }
    }
}

