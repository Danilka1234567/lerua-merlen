package model.exception;

import java.sql.SQLException;

public class UniqueViolationException extends RuntimeException {
    private final String fieldName;

    public UniqueViolationException(SQLException e) {
        fieldName = FieldNameParser.getFieldName(e);
    }

    @Override
    public String getMessage() {
        return switch (fieldName){
            case "email" -> "Entity with such email already exists in db!";
            case "phone_number" -> "Entity with such email already exists in db!";
            case "full_address" -> "Entity with such full address already exists in db!";
            default -> "Entity with unknown field which must be unique is already in db!";
        };
    }
}
