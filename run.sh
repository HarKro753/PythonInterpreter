#!/bin/sh
mkdir -p out
javac -d out LangError.java Lexer.java Parser.java && java -cp out Parser
