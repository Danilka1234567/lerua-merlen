package model.service.shared;

public class Validator {

    public static void validateNotNull(Object o, String fieldName){
        if (o == null)
            throw new IllegalArgumentException(
                    "%s can't be null".formatted(fieldName)
            );
    }

}
