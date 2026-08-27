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
            case "warehouse_id" -> "В базе данных нет скалада с таким айди!";
            case "manufacturer_id" -> "В базе данных нет производителя с таким айди!";
            case "user_id" -> "В базе данных нет пользователя с таким айди!";
            case "product_id" -> "В базе данных нет товара с таким айди!";
            default -> "Неизвестная ошибка 'иностранного' ключа";
        };
    }
}
