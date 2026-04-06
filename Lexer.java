import java.util.ArrayList;
import java.util.List;

public class Lexer {

    enum TokenType {
        Plus, Minus, Mul, Div, Int, Float, LParen, RParen, EOF
    }

    static class Token {
        TokenType type;
        String value;
        int pos;

        Token(TokenType type, int pos) {
            this.type = type;
            this.value = null;
            this.pos = pos;
        }

        Token(TokenType type, String value, int pos) {
            this.type = type;
            this.value = value;
            this.pos = pos;
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
                tokens.add(new Token(TokenType.Plus, i));
                i++;
            } else if (ch == '-') {
                tokens.add(new Token(TokenType.Minus, i));
                i++;
            } else if (ch == '*') {
                tokens.add(new Token(TokenType.Mul, i));
                i++;
            } else if (ch == '/') {
                tokens.add(new Token(TokenType.Div, i));
                i++;
            } else if (ch == '(') {
                tokens.add(new Token(TokenType.LParen, i));
                i++;
            } else if (ch == ')') {
                tokens.add(new Token(TokenType.RParen, i));
                i++;
            } else if (Character.isDigit(ch) || ch == '.') {
                int start = i;
                StringBuilder num = new StringBuilder();
                int dots = 0;
                while (i < text.length() && (Character.isDigit(text.charAt(i)) || text.charAt(i) == '.')) {
                    if (text.charAt(i) == '.') {
                        dots++;
                        if (dots > 1) {
                            throw new LangError(text, i, "Invalide Zahl");
                        }
                    }
                    num.append(text.charAt(i));
                    i++;
                }
                if (dots == 1) {
                    tokens.add(new Token(TokenType.Float, String.valueOf(Double.parseDouble(num.toString())), start));
                } else {
                    tokens.add(new Token(TokenType.Int, String.valueOf(Integer.parseInt(num.toString())), start));
                }
            } else {
                throw new LangError(text, i, "Unbekanntes Zeichen: '" + ch + "'");
            }
        }
        tokens.add(new Token(TokenType.EOF, i));
        return tokens;
    }

}
