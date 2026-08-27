package model.dto;

import model.service.shared.Validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDetails(String productName, BigDecimal price,
                             String manufacturerName, String warehouseCity,
                             String lastCustomerName, LocalDateTime sessionExpiredAt) {
    public ProductDetails {
        Validator.validateNotNull(productName, "Product name");
        Validator.validateNotNull(price, "Price");
        Validator.validateNotNull(manufacturerName, "Manufacturer name");
        Validator.validateNotNull(warehouseCity, "Warehouse city");
        Validator.validateNotNull(lastCustomerName, "Warehouse address");
        Validator.validateNotNull(sessionExpiredAt, "Session expired at");
    }

}
