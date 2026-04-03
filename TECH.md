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

### Related Concepts

- Context-Free Grammars (CFG) — Chomsky hierarchy Type 2
- BNF (Backus-Naur Form) — notation for production rules
- Recursive Descent Parsing — one function per grammar rule, top-down
- Chomsky Hierarchy — our lexer is Type 3 (regular), our parser is Type 2 (context-free)
- Dragon Book (Aho, Sethi, Ullman) — stratified grammars for operator precedence
