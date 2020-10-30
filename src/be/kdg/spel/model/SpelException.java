package be.kdg.spel.model;

public class SpelException extends RuntimeException {
    public SpelException(String message) {
        super(message);
    }

    public SpelException(Throwable cause) {
        super(cause);
    }
}
