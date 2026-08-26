package model.service.shared;

import model.repository.common.ContactableRepository;
import model.vo.ContactInfo;

public class Validator {

    public static void validateNotNull(Object o, String fieldName){
        if (o == null)
            throw new IllegalArgumentException(
                    "%s can't be null".formatted(fieldName)
            );
    }

}
