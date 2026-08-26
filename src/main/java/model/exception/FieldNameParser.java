package model.exception;

import java.sql.SQLException;

public class FieldNameParser {

    public static String getFieldName(SQLException e){
        String message = e.getMessage();
        if (message.contains("email"))
            return "email";
        if(message.contains("phone_number"))
            return "phone_number";
        if (message.contains("unique_address"))
            return "full_address";
        if (message.contains("manufacturer_id"))
            return "manufacturer_id";
        if (message.contains("warehouse_id"))
            return "warehouse_id";
        if (message.contains("user_id"))
            return "user_id";
        if (message.contains("product_id"))
            return "product_id";
        return "";
    }

}
