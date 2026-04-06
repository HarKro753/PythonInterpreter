import java.util.List;
import java.util.Scanner;

public class Parser {

    interface Node {}

    static class NumberNode implements Node {
        Lexer.Token token;

        NumberNode(Lexer.Token token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return token.toString();
        }
    }

    static class BinOpNode implements Node {
        Node left;
        Lexer.Token op;
        Node right;

        BinOpNode(Node left, Lexer.Token op, Node right) {
            this.left = left;
            this.op = op;
            this.right = right;
        }

        @Override
        public String toString() {
            return "(" + left + ", " + op + ", " + right + ")";
        }
    }

    static class UnaryOpNode implements Node {
        Lexer.Token op;
        Node node;

        UnaryOpNode(Lexer.Token op, Node node) {
            this.op = op;
            this.node = node;
        }

        @Override
        public String toString() {
            return "(" + op + ", " + node + ")";
        }
    }

    // Grammar:
    //   expr   : term ((PLUS|MINUS) term)*
    //   term   : factor ((MUL|DIV) factor)*
    //   factor : INT|FLOAT | (PLUS|MINUS) factor | LPAREN expr RPAREN

    List<Lexer.Token> tokens;
    String text;
    int pos;

    Parser(List<Lexer.Token> tokens, String text) {
        this.tokens = tokens;
        this.text = text;
        this.pos = 0;
    }

    Lexer.Token current() {
        if (pos >= tokens.size()) {
            throw new LangError(text, text.length(), "Unerwartetes Ende der Eingabe");
        }
        return tokens.get(pos);
    }

    void advance() {
        pos++;
    }

    Node factor() throws Exception {
        Lexer.Token tok = current();

        if (tok.type == Lexer.TokenType.Plus || tok.type == Lexer.TokenType.Minus) {
            advance();
            return new UnaryOpNode(tok, factor());
        } else if (tok.type == Lexer.TokenType.Int || tok.type == Lexer.TokenType.Float) {
            advance();
            return new NumberNode(tok);
        } else if (tok.type == Lexer.TokenType.LParen) {
            advance();
            Node result = expr();
            if (current().type != Lexer.TokenType.RParen) {
                throw new LangError(text, current().pos, "Erwarte ')'");
            }
            advance();
            return result;
        }

        throw new LangError(text, tok.pos, "Unerwartetes Token: " + tok);
    }

    Node term() throws Exception {
        Node left = factor();
        while (current().type == Lexer.TokenType.Mul || current().type == Lexer.TokenType.Div) {
            Lexer.Token op = current();
            advance();
            Node right = factor();
            left = new BinOpNode(left, op, right);
        }
        return left;
    }

    Node expr() throws Exception {
        Node left = term();
        while (current().type == Lexer.TokenType.Plus || current().type == Lexer.TokenType.Minus) {
            Lexer.Token op = current();
            advance();
            Node right = term();
            left = new BinOpNode(left, op, right);
        }
        return left;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("parser> ");
            if (!scanner.hasNextLine()) break;
            String text = scanner.nextLine();
            if (text.trim().isEmpty()) continue;
            try {
                List<Lexer.Token> tokens = Lexer.lexer(text);
                Parser parser = new Parser(tokens, text);
                Node ast = parser.expr();
                if (parser.current().type != Lexer.TokenType.EOF) {
                    throw new LangError(text, parser.current().pos, "Unerwartetes Token nach Ausdruck: " + parser.current());
                }
                System.out.println(ast);
            } catch (LangError e) {
                System.out.println("Error: " + e);
            }
        }
        scanner.close();
    }
}
