package model.exception;

public class UniqueViolationException extends RuntimeException {
    public UniqueViolationException(String message) {
        super(message);
    }

    public UniqueViolationException() {
    }
}
