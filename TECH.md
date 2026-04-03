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

## Part 8 — The Visitor Pattern and Dynamic Dispatch

`visit_UnaryOp()` looks like it's never called — there's no explicit call to it anywhere. The magic is in the `visit()` method:

```python
def visit(self, node):
    method_name = 'visit_' + type(node).__name__
    visitor = getattr(self, method_name, self.generic_visit)
    return visitor(node)
```

It builds the method name **dynamically** from the node's class name. When `visit()` receives a `UnaryOp` node:

1. `type(node).__name__` → `"UnaryOp"`
2. `'visit_' + "UnaryOp"` → `"visit_UnaryOp"`
3. `getattr(self, "visit_UnaryOp")` → finds the method
4. Calls it

### Example: `-3`

```
Parser builds:  UnaryOp(MINUS, Num(3))

Interpreter:
├─ interpret() calls self.visit(tree)
├─ tree is a UnaryOp → visit() builds "visit_UnaryOp" → calls it
├─ visit_UnaryOp sees MINUS, calls self.visit(node.expr)
│   ├─ node.expr is a Num(3) → visit() builds "visit_Num" → calls it
│   └─ visit_Num returns 3
└─ visit_UnaryOp returns -3
```

This is the **visitor pattern** — instead of using `if/elif` to check node types, it uses Python's `getattr()` to dispatch automatically. The benefit: when we add a new node type, we just add a `visit_NewType` method and it works. No need to touch `visit()` itself.

This relates to **dynamic dispatch** and the **open/closed principle** — the system is open for extension (new node types) but closed for modification (the `visit()` method never changes).

## Part 9 — From Calculator to Programming Language

This is the biggest leap in the series. We went from a stateless expression evaluator to an imperative language with variables, assignment, and compound statements. The interpreter now has **memory**.

### The Symbol Table (GLOBAL_SCOPE)

The `GLOBAL_SCOPE` dictionary is our first **symbol table** — the data structure every compiler and interpreter uses to track what names mean. Right now it maps variable names to integer values. In later parts it'll track types, scopes, and procedures.

In formal terms, this is an **environment** in the operational semantics sense — a mapping from identifiers to values: `Env : Name → Value`.

### From Expressions to Statements — State Transitions

Up to Part 8, our language was purely **functional**: input goes in, a value comes out, no side effects. Now we have **statements** that modify state.

Each statement is a **state transition** — it reads from the store, computes, and writes back. This is the core of **denotational semantics**, where a program's meaning is defined as a function from states to states:

```
Statement : State → State
```

For example, `x := 2 + 3` transforms `{} → {x: 5}`. Then `y := x + 1` transforms `{x: 5} → {x: 5, y: 6}`. The program is a composition of these state transitions.

### Sequential Composition and Structured Programming

`BEGIN...END` blocks give us **sequential composition** — executing statements in order. In formal semantics this is written as `S1; S2` and means "execute S1, then in the resulting state, execute S2."

This is one of the fundamental constructs in **Hoare logic**, where we reason about programs using preconditions and postconditions:

```
{P} S1 {Q},  {Q} S2 {R}
─────────────────────────
    {P} S1; S2 {R}
```

Nested `BEGIN...END` blocks also give us **block structure** — a key concept from **structured programming** (Dijkstra, 1968).

### Reserved Words vs Identifiers

The `_id()` method in the lexer reads a word, then checks if it's a keyword (`BEGIN`, `END`) or a variable name. This is a classic lexer problem — the lexer must resolve this ambiguity before the parser ever sees the token. This is why most languages forbid using keywords as variable names.

This connects to the concept of **maximal munch** in lexical analysis — the lexer always consumes the longest possible match. When it reads `B`, `E`, `G`, `I`, `N`, it doesn't stop at `B` — it keeps going until the word ends, then looks it up.

### Predictive Parsing and LL(1)

The `statement()` method checks the current token to decide which rule to apply:

```
BEGIN → compound_statement
ID    → assignment_statement
else  → empty
```

This is **LL(1) parsing** — we decide which production to use by looking at just **one token** ahead. The "LL" means Left-to-right scan, Leftmost derivation. The "1" means one token of lookahead.

The `peek()` method in the lexer is related — it gives us one character of lookahead to distinguish `:` from `:=` (a two-character token). This is lookahead at the **lexical level**, while `statement()` uses lookahead at the **syntactic level**.

### Walkthrough: A Complete Program

```pascal
BEGIN
    x := 2;
    y := x + 3
END.
```

```
State: {}
├─ visit_Compound → visits each child statement
│   ├─ visit_Assign(x := 2)
│   │   ├─ visit_Num(2) → returns 2
│   │   └─ GLOBAL_SCOPE["x"] = 2           State: {x: 2}
│   ├─ visit_Assign(y := x + 3)
│   │   ├─ visit_BinOp(x + 3)
│   │   │   ├─ visit_Var(x) → looks up "x" → returns 2
│   │   │   └─ visit_Num(3) → returns 3
│   │   │   └─ returns 2 + 3 = 5
│   │   └─ GLOBAL_SCOPE["y"] = 5           State: {x: 2, y: 5}
└─ returns {x: 2, y: 5}
```

## Part 13 — Static Analysis: Catching Errors Before Execution

This part introduces the most important new theoretical concept since ASTs: **static semantic analysis**. The interpreter now has a phase that checks the program for errors _without running it_.

### The Multi-Pass Architecture

Our pipeline is now:

```
Source → Lexer → Parser → AST → Semantic Analyzer → Interpreter
                                 ↑                    ↑
                           Pass 1: check          Pass 2: execute
```

The AST is traversed **twice** by two different visitors. The semantic analyzer walks the tree first, checking for errors. Only if it passes does the interpreter walk the same tree to execute it. This is the same architecture used by real compilers — GCC, Clang, javac all have separate analysis passes before code generation.

### Static vs Dynamic Analysis

This is a fundamental distinction in computer science:

- **Static analysis** — examining the program _without executing it_. Our `SemanticAnalyzer` does this. It reads declarations, builds a symbol table, and checks that every variable used is declared. All of this happens at "compile time" (before execution).
- **Dynamic analysis** — checking things _during execution_. Our `Interpreter` does this. Division by zero, for example, can only be caught at runtime.

In type theory, this maps to:

- **Static type systems** (Java, C, Rust) — types checked before execution
- **Dynamic type systems** (Python, JavaScript) — types checked during execution

Our Pascal interpreter is moving toward a static approach — you must declare variables before using them.

### What the Semantic Analyzer Checks

Two specific semantic rules are enforced:

**1. Undeclared identifiers** — using a variable that was never declared in a `VAR` block:

```pascal
PROGRAM Test;
VAR x : INTEGER;
BEGIN
    x := y + 1    { Error: Symbol(identifier) not found 'y' }
END.
```

The analyzer walks the AST, and when it visits a `Var` node, it looks up the name in the symbol table. If it's not there, the program is rejected before it ever runs.

**2. Duplicate declarations** — declaring the same variable twice:

```pascal
PROGRAM Test;
VAR
    x : INTEGER;
    x : REAL;       { Error: Duplicate identifier 'x' found }
BEGIN
    x := 1
END.
```

When `visit_VarDecl` tries to insert a symbol, it first checks if the name already exists.

### Decidability and the Halting Problem

Static analysis is fundamentally limited by **Rice's theorem** — you cannot statically determine all properties of a program's runtime behavior. For example, you can check "is this variable declared?" statically, but you cannot check "will this program ever divide by zero?" in all cases (that would require solving the **halting problem**).

This is why languages split their checks: easy things (declarations, types) are checked statically, hard things (bounds, null pointers, termination) are checked dynamically or not at all.

### The Symbol Table as a Formal Environment

The `SymbolTable` is now a proper data structure with `insert()` and `lookup()` operations. In formal terms, it implements an **environment** — a partial function from names to their properties:

```
Γ : Name → Symbol
```

This is the same Γ (gamma) notation used in **type judgments** in formal type theory:

```
Γ ⊢ x : INTEGER
```

This reads: "in environment Γ, the identifier x has type INTEGER." Our `lookup('x')` does exactly this — it checks what type (if any) is associated with `x` in the current environment.

### Limitation: No Nested Scopes Yet

Right now there's one flat symbol table. This means a variable `a` declared inside a procedure collides with `a` declared in the main program — even though Pascal allows this (they're in different scopes). Part 14 fixes this with **scoped symbol tables** that chain together, implementing **lexical scoping** — one of the most important concepts in programming language theory.

## Parts 14–18 — Nested Scopes, Procedure Calls, and the Call Stack

These five parts complete the interpreter. We now have nested scopes, proper error reporting with line/column numbers, and full procedure call execution with a call stack.

### Part 14: Lexical Scoping and the Scope Chain

The flat `SymbolTable` is replaced by `ScopedSymbolTable` — each scope has a name, level, and a pointer to its **enclosing scope**. When looking up a name, we first check the current scope, then walk up the chain:

```
Procedure scope (level 2)  →  Global scope (level 1)  →  None
      a, b (params)                x, y (globals)
```

This is **lexical scoping** (also called static scoping) — the scope of a name is determined by where it appears in the source code, not by the runtime call sequence. This was introduced by **Algol 60** and is used by almost every modern language.

In the **lambda calculus**, this corresponds to the concept of **free and bound variables**. A variable is **bound** if it's defined in the current scope (a parameter or local), and **free** if it must be resolved from an enclosing scope. The `lookup()` method with `current_scope_only=False` implements exactly this: search locally first, then look outward for free variables.

The `current_scope_only=True` flag is used for duplicate detection — you can have `a` in the global scope AND `a` in a procedure scope, because they're different bindings. But two `a`s in the same scope is an error.

### Part 15: Error Reporting and Source Location Tracking

Tokens now carry `lineno` and `column` fields, set by the lexer as it scans. Errors include three custom exception classes:

```
LexerError    — invalid character (e.g., '@')
ParserError   — unexpected token (e.g., missing semicolon)
SemanticError — identifier not found, duplicate id
```

Each error reports the exact source position:

```
SemanticError: Identifier not found -> Token(ID, 'y', position=4:10)
```

This connects to the concept of **error recovery** in compiler theory. Our interpreter uses the simplest strategy — **panic mode** — it stops at the first error. Real compilers try to recover and report multiple errors in one pass.

### Part 16: Procedure Calls — New Grammar Rules

The parser now distinguishes between assignments (`x := 5`) and procedure calls (`Alpha(3, 7)`) by peeking at the character after the identifier. If it's `(`, it's a call. This is a form of **LL(2) lookahead** — we need to see two tokens ahead (the ID and the LPAREN) to decide which rule to apply.

New AST nodes:

- `Param` — a formal parameter in a procedure declaration (`a : INTEGER`)
- `ProcedureCall` — a call site with actual parameters (`Alpha(3, 7)`)

The `ProcedureCall` node has a `proc_symbol` field that gets filled in by the semantic analyzer — this is a form of **name resolution** where the analyzer links the call to its declaration.

### Part 17: The Call Stack and Activation Records

The `GLOBAL_SCOPE` dictionary is replaced by a proper **call stack** with **activation records** (also called **stack frames**). This is how real computers manage function calls.

```
┌─────────────────────────┐
│ AR: Alpha (level 2)     │  ← top (current)
│   a = 3                 │
│   b = 7                 │
│   z = ?                 │
├─────────────────────────┤
│ AR: PROGRAM (level 1)   │
│   x = 10               │
│   y = ?                 │
└─────────────────────────┘
```

Each procedure call **pushes** a new activation record. When the procedure returns, it **pops** the record. This is a **LIFO (Last In, First Out) stack** — the same data structure used by CPU hardware for function calls.

In theoretical CS, the call stack is what gives us **pushdown automata** — automata with a stack. This is the computational model that corresponds to **context-free languages** in the Chomsky hierarchy. The call stack is literally the "push-down" part.

### Part 18: Executing Procedure Calls — The Complete Algorithm

The five-step execution of a procedure call:

```
1. Create a new ActivationRecord for the procedure
2. Evaluate actual parameters, store in AR under formal parameter names
3. Push AR onto the call stack
4. Execute the procedure body (visit the block AST)
5. Pop AR from the call stack
```

This maps directly to how real CPUs execute function calls:

```
1. Allocate stack frame
2. Pass arguments (registers or stack)
3. Jump to function code
4. Execute function body
5. Restore stack frame, return
```

### Formal Semantics: Big-Step Operational Semantics

The procedure call mechanism implements what formal semantics calls a **big-step rule** for procedure invocation:

```
Γ ⊢ proc(a₁, a₂, ...) ⇓ result

  where:
    - look up proc in Γ to get formal params (p₁, p₂, ...) and body B
    - evaluate each aᵢ to get values vᵢ
    - extend Γ with {p₁ → v₁, p₂ → v₂, ...}
    - evaluate B in the extended environment
```

Our code does exactly this: `node.proc_symbol` gives us the formal params and body, we evaluate actual params, create an extended environment (the AR), and execute the body.

### The Complete Pipeline

The final interpreter architecture:

```
Source Code
    ↓
  Lexer          — characters → tokens (with line:column)
    ↓
  Parser         — tokens → AST (with Param, ProcedureCall nodes)
    ↓
  Semantic       — AST → checked AST (scoped symbol tables,
  Analyzer         name resolution, proc_symbol linking)
    ↓
  Interpreter    — AST → execution (call stack with activation records)
```

This four-stage pipeline mirrors the architecture of real compilers and interpreters. The only stage a real compiler would add is **code generation** — translating the AST to machine code or bytecode instead of walking it directly.
