#!/bin/sh
mkdir -p out
javac -d out Lexer.java Parser.java && java -cp out Parser
