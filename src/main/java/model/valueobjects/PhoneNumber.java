package model.valueobjects;

import java.util.regex.Pattern;

public class PhoneNumber {

    private static final Pattern RUS_PHONE_NUMBER_PATTERN = Pattern.compile(
            "^(?:\\+7|8)[\\s\\-]?\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$"
    );

    private final String number;

    public PhoneNumber(String number){

        if (! RUS_PHONE_NUMBER_PATTERN.matcher(number).matches())
            throw new IllegalArgumentException(
                    "Invalid phone number!"
            );
        this.number = getNormalizedPhoneNumber(number);
    }


    private static String getNormalizedPhoneNumber(String number){
        String digits = number.replaceAll("[^\\d+]", "");

        if (digits.startsWith("8")){
            digits = "+7" + digits.substring(1);
        }

        return digits;
    }


    public String getValue(){
        return number;
    }
}
