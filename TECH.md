# Technical Insights

Notes and learnings from each section of the interpreter build.

## Part 4 — Operator Precedence Through Grammar Structure

There's no special "precedence table" — precedence is encoded entirely through **which function calls which**.

```
expr()   → handles + and -   (lowest precedence)
  ↓
term()   → handles * and /   (higher precedence)
  ↓
factor() → returns a number  (highest precedence)
```

Each function only loops on its own operators. When it needs a value, it calls the level below. Since `term()` resolves `*` and `/` before returning a value to `expr()`, multiplication and division always happen first.

### Example: `2 + 3 * 4`

```
expr() starts
├─ calls term() → calls factor() → 2
│   term() sees +, not * or /, so returns 2
├─ expr() sees +, eats it
├─ calls term() → calls factor() → 3
│   term() sees *, eats it, calls factor() → 4
│   term() computes 3 * 4 = 12, returns 12
├─ expr() computes 2 + 12 = 14
└─ returns 14
```

The deeper a function is in the call hierarchy, the higher its precedence. Simple `while` loops and `if/else` statements are all it takes to encode the "multiply before addition" rule.

### Related Concepts in Theoretical Computer Science

#### Context-Free Grammars (CFG)

Our function hierarchy is a direct implementation of a CFG. The grammar rules we coded are called **production rules**:

```
expr   → term ((PLUS | MINUS) term)*
term   → factor ((MUL | DIV) factor)*
factor → INTEGER
```

This is the same notation (BNF — Backus-Naur Form) used to formally define programming languages.

#### Recursive Descent Parsing

Our approach — one function per grammar rule, calling each other top-down — is called a **recursive descent parser**. It's one of the simplest ways to implement a parser and maps 1:1 to the grammar. This falls under **top-down parsing** in the Chomsky hierarchy.

#### The Chomsky Hierarchy

Our grammar sits at **Type 2 (context-free)** in Chomsky's hierarchy:

```
Type 0 — Unrestricted (Turing machines)
Type 1 — Context-sensitive
Type 2 — Context-free ← our parser is here
Type 3 — Regular       ← our lexer is here (finite automaton)
```

The lexer (tokenizer) operates at Type 3 — it's essentially a finite automaton recognizing patterns like digits and operators. The parser operates at Type 2 — it needs a stack (the call stack!) to handle nested structures, which is exactly why parentheses in Part 6 will work through recursion.

#### Operator Precedence via Stratified Grammars

In formal terms, precedence is encoded by **stratifying the grammar into levels**. Each level of production rules corresponds to a precedence level. This is a standard technique in compiler theory — straight out of the "Dragon Book" (Aho, Sethi, Ullman).

## Part 7 — Abstract Syntax Trees and the Separation of Syntax from Semantics

The interpreter is now split into a **parser** (builds a tree) and an **interpreter** (walks the tree). The pipeline matches the classic compiler architecture:

```
Frontend (Lexer + Parser) → IR (AST) → Backend (Interpreter)
```

### Abstract Syntax Trees (ASTs)

In formal language theory, a **parse tree** (or derivation tree) represents how a string is derived from a grammar. An AST is a simplified version — it drops the grammar noise (parentheses, keywords) and keeps only the meaningful structure. For `(2 + 3) * 4`:

```
    *
   / \
  +   4
 / \
2   3
```

The parentheses are gone — the tree structure itself encodes the precedence.

### Intermediate Representations (IR)

This is a core concept from **compiler theory**. The AST is our first **intermediate representation** — a data structure that sits between the source code and the result. Real compilers often have multiple IRs, each transforming the program further.

### Tree Traversal and Tree Automata

The way the interpreter walks the tree (`visit_BinOp` calls `visit` on left and right children) is a **post-order tree traversal** — children are evaluated before the parent. This connects to **tree automata** in theoretical CS, which formalize how to process tree-structured data.

### Separation of Syntax and Semantics

This is a fundamental distinction in formal language theory. The **parser** handles syntax (is the expression well-formed?), while the **interpreter** handles semantics (what does it mean?). By separating them, we could attach different semantics to the same syntax — a type checker, a compiler, a pretty-printer — all just different visitors walking the same tree.
