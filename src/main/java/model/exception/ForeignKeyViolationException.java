package model.exception;

import java.sql.SQLException;


public class ForeignKeyViolationException extends RuntimeException {

    private final String fieldName;

    public ForeignKeyViolationException(SQLException e) {
        fieldName = FieldNameParser.getFieldName(e);
    }

    @Override
    public String getMessage() {
        return switch (fieldName){
            case "warehouse_id" -> "Where are no warehouse with such id in db";
            case "manufacturer_id" -> "Where are no manufacturer with such id in db";
            case "user_id" -> "Where are no user with such id in db";
            case "product_id" -> "Where are no product with such id in db";
            default -> "Where are no unknown entity with such id in db";
        };
    }
}
