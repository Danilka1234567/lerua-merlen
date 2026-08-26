package model.exception;

public class ForeignKeyViolationException extends RuntimeException {
    public ForeignKeyViolationException(String message) {
        super(message);
    }

    public ForeignKeyViolationException() {
    }
}
