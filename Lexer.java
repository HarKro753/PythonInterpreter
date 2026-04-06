import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lexer {

    enum TokenType {
        Plus, Minus, Mul, Div, Int, Float
    }

    static class Token {
        TokenType type;
        String value;

        Token(TokenType type) {
            this.type = type;
            this.value = null;
        }

        Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return value != null ? type + ":" + value : type.toString();
        }
    }

    static List<Token> lexer(String text) throws Exception {
        List<Token> tokens = new ArrayList<>();
        int i = 0;

        while (i < text.length()) {
            char ch = text.charAt(i);
            if (ch == ' ' || ch == '\n' || ch == '\t') {
                i++;
            } else if (ch == '+') {
                tokens.add(new Token(TokenType.Plus));
                i++;
            } else if (ch == '-') {
                tokens.add(new Token(TokenType.Minus));
                i++;
            } else if (ch == '*') {
                tokens.add(new Token(TokenType.Mul));
                i++;
            } else if (ch == '/') {
                tokens.add(new Token(TokenType.Div));
                i++;
            } else if (Character.isDigit(ch) || ch == '.') {
                StringBuilder num = new StringBuilder();
                int dots = 0;
                while (i < text.length() && (Character.isDigit(text.charAt(i)) || text.charAt(i) == '.')) {
                    if (text.charAt(i) == '.') {
                        dots++;
                        if (dots > 1) {
                            throw new Exception("Invalide Zahl: " + num.toString() + text.charAt(i));
                        }
                    }
                    num.append(text.charAt(i));
                    i++;
                }
                if (dots == 1) {
                    tokens.add(new Token(TokenType.Float, String.valueOf(Double.parseDouble(num.toString()))));
                } else {
                    tokens.add(new Token(TokenType.Int, String.valueOf(Integer.parseInt(num.toString()))));
                }
            } else {
                throw new Exception("Unbekanntes Zeichen: " + ch);
            }
        }
        return tokens;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("lexer> ");
            if (!scanner.hasNextLine()) break;
            String text = scanner.nextLine();
            if (text.trim().isEmpty()) continue;
            try {
                List<Token> result = lexer(text);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
