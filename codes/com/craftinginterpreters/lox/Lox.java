package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
// import java.util.Scanner; ← 【重要】ここを削除またはコメントアウト！
import java.util.Scanner;

public class Lox {
    // 【重要】まだInterpreterはないのでコメントアウト
    // private static final Interpreter interpreter = new Interpreter();

    static boolean hadError = false;
    // static boolean hadRuntimeError = false; // まだ使わない

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(65);
        // if (hadRuntimeError) System.exit(70); // まだ使わない
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.println("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        // ---------------------------------------------
        // 今はトークンを表示するだけでOKです。
        // ParserやInterpreterはまだないのでコメントアウトします。
        // ---------------------------------------------
        
        for (Token token : tokens) {
            System.out.println(token);
        }

        /* Parser parser = new Parser(tokens);
        Expr expression = parser.parse();
        if (hadError) return;
        interpreter.interpret(expression);
        */
    }

    static void error(int line, String message) {
        report(line, "", message);
    }
    static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() +
            "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }

    // まだTokenクラスのエラー処理を使う段階ではないかもしれませんが、
    // Token.javaがあるならエラーにはなりません。もしエラーになるならここもコメントアウト。
    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    /*
    static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() +
            "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }
    */

    private static void report(int line, String where, String message) {
        System.err.println(
            "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}