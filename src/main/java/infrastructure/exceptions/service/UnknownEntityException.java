package infrastructure.exceptions.service;

public class UnknownEntityException extends RuntimeException {
    public UnknownEntityException(String message) {
        super(message);
    }
}
