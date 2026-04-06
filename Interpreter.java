import java.util.List;
import java.util.Scanner;

public class Interpreter {

    double visit(Parser.Node node) {
        if (node instanceof Parser.NumberNode n) {
            return Double.parseDouble(n.token.value);

        } else if (node instanceof Parser.BinOpNode n) {
            double left = visit(n.left);
            double right = visit(n.right);

            if (n.op.type == Lexer.TokenType.Plus) return left + right;
            if (n.op.type == Lexer.TokenType.Minus) return left - right;
            if (n.op.type == Lexer.TokenType.Mul) return left * right;
            if (n.op.type == Lexer.TokenType.Div) return left / right;

        } else if (node instanceof Parser.UnaryOpNode n) {
            double value = visit(n.node);

            if (n.op.type == Lexer.TokenType.Minus) return -value;
            return value;
        }

        throw new RuntimeException("Unbekannter Node: " + node.getClass().getSimpleName());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Interpreter interpreter = new Interpreter();
        while (true) {
            System.out.print("calc> ");
            if (!scanner.hasNextLine()) break;
            String text = scanner.nextLine();
            if (text.trim().isEmpty()) continue;
            try {
                List<Lexer.Token> tokens = Lexer.lexer(text);
                Parser parser = new Parser(tokens, text);
                Parser.Node ast = parser.expr();
                if (parser.current().type != Lexer.TokenType.EOF) {
                    throw new LangError(text, parser.current().pos, "Unerwartetes Token nach Ausdruck: " + parser.current());
                }
                double result = interpreter.visit(ast);
                if (result == (int) result) {
                    System.out.println((int) result);
                } else {
                    System.out.println(result);
                }
            } catch (LangError e) {
                System.out.println("Error: " + e);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
