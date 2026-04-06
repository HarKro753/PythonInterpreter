public class LangError extends RuntimeException {
    String text;
    int pos;

    LangError(String text, int pos, String message) {
        super(message);
        this.text = text;
        this.pos = pos;
    }

    @Override
    public String toString() {
        return getMessage() + "\n  " + text + "\n  " + " ".repeat(pos) + "^";
    }
}
