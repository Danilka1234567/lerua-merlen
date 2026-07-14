package infrastructure.exceptions.dto;

public class NotNullException extends RuntimeException {
    public NotNullException(String fieldName) {
        super("Field %s can't be null".formatted(fieldName));
    }
}
