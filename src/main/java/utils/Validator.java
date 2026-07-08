package utils;

import java.util.regex.Pattern;

public class Validator {

    private static final Pattern RUS_PHONE_NUMBER_PATTERN = Pattern.compile(
            "^(?:\\+7|8)[\\s\\-]?\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$"
    );

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern RUSSIAN_ADDRESS_PATTERN = Pattern.compile(
            "^(?=.*[А-Яа-яЁё])(?=.*\\d)[А-Яа-яЁё0-9\\s\\.,\\-\\/]+(?:\\s*,\\s*[А-Яа-яЁё0-9\\s\\.,\\-\\/]+)*$"
    );

    public static boolean isValidNumber(String phoneNumber){
        if (phoneNumber == null)
            return false;

        return RUS_PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }


    public static String getNormalizedPhoneNumber(String number){

        if (! isValidNumber(number))
            throw new IllegalArgumentException(
                    "Invalid phone number!"
            );

        String digits = number.replaceAll("[^\\d+]", "");

        if (digits.startsWith("8")){
            digits = "+7" + digits.substring(1);
        }

        return digits;
    }


    public static boolean isValidEmail(String email){
        if (email == null)
            return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidAddress(String address){
        if (address == null)
            return false;

        return RUSSIAN_ADDRESS_PATTERN.matcher(address).matches();
    }

}
