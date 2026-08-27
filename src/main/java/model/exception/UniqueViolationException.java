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
            case "email" -> "Сущность с таким email уже существует в db!";
            case "phone_number" -> "Сущность с таким номером телефоном уже существует в db!";
            case "full_address" -> "Сущность с таким адрессом уже существует в db!";
            default -> "Сущность с неизвестным уникальным полем уже существует в db!";
        };
    }
}
