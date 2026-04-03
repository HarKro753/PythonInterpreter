# 📄 PythonInterpreter

> _A simple interpreter built from scratch in Python, learning how programming languages work under the hood._

[![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://python.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](LICENSE)

## 🌟 Highlights

- Hand-built lexer, parser, and evaluator — no external dependencies
- Interactive REPL mode (`calc>` prompt)
- Custom `.calc` file format support
- Following the full 18-part [LSBASI tutorial series](https://ruslanspivak.com/lsbasi-part1/)

## ℹ️ Overview

This project follows Ruslan Spivak's ["Let's Build A Simple Interpreter"](https://ruslanspivak.com/lsbasi-part1/) tutorial series to build a Pascal interpreter from scratch in Python. The goal is to understand how interpreters and compilers work — from tokenizing raw text, to parsing grammar rules, to evaluating results.

**Currently implemented (Parts 1–3):**
- Lexer that tokenizes input into `INTEGER`, `PLUS`, `MINUS`, and `EOF` tokens
- Parser that handles arbitrary-length addition and subtraction expressions
- Evaluator that computes the result
- Multi-digit integers and whitespace handling

## 🚀 Usage

### Interactive Mode

```sh
python3 src/main.py
```

```
calc> 21 + 12 + 23
56
calc> 100 - 50 - 25
25
```

### File Mode

Create a `.calc` file with one expression per line:

```
21 + 12 + 23
100 - 50 - 25
7 - 3 + 2 - 1 + 10
```

Run it:

```sh
python3 src/main.py examples/section3.calc
```

```
56
25
15
```

## ⬇️ Installation

Requires **Python 3.6+**. No dependencies.

```sh
git clone https://github.com/harrokrog/PythonInterpreter.git
cd PythonInterpreter
python3 src/main.py
```

## 🗺️ Roadmap

- [x] Part 1 — Single-digit addition
- [x] Part 2 — Multi-digit integers, subtraction, whitespace
- [x] Part 3 — Syntax diagrams, arbitrary-length +/- expressions
- [x] Part 4 — Context-free grammars, multiplication and division
- [x] Part 5 — Operator precedence and associativity
- [x] Part 6 — Parenthesized expressions
- [ ] Part 7 — Abstract Syntax Trees (ASTs)
- [ ] Part 8 — Unary operators
- [ ] Part 9 — Pascal compound statements, variables
- [ ] Part 10 — Complete Pascal programs
- [ ] Part 11 — Symbol table management
- [ ] Part 12 — Procedure declarations
- [ ] Part 13 — Semantic analysis
- [ ] Part 14 — Nested scopes, source-to-source compiler
- [ ] Part 15 — Improved error reporting
- [ ] Part 16 — Recognizing procedure calls
- [ ] Part 17 — Call stack and activation records
- [ ] Part 18 — Executing procedure calls

## 📖 Further Reading

- [Let's Build A Simple Interpreter — Ruslan Spivak](https://ruslanspivak.com/lsbasi-part1/)
- [banesullivan/README — README guide](https://github.com/banesullivan/README)

## 📄 License

Distributed under the MIT License. See [`LICENSE`](LICENSE) for details.
