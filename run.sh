#!/bin/sh
mkdir -p out
javac -d out LangError.java Lexer.java Parser.java Interpreter.java && java -cp out Interpreter
