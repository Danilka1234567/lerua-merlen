package infrastructure.exceptions.repository;

public class ForeignKeyException extends RepositoryException {
    public ForeignKeyException(String message) {
        super(message);
    }

    public ForeignKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
