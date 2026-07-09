package model.valueobjects;

import java.util.regex.Pattern;

public class Address {

    private static final Pattern RUSSIAN_ADDRESS_PATTERN = Pattern.compile(
            "^(?=.*[А-Яа-яЁё])(?=.*\\d)[А-Яа-яЁё0-9\\s\\.,\\-\\/]+(?:\\s*,\\s*[А-Яа-яЁё0-9\\s\\.,\\-\\/]+)*$"
    );

    private final String address;

    public Address(String address){

        if (! RUSSIAN_ADDRESS_PATTERN.matcher(address).matches()){
            throw new IllegalArgumentException(
                    "Invalid address"
            );
        }

        this.address = address;
    }

    public String getValue(){
        return address;
    }

}
