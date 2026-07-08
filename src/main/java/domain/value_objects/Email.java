package domain.value_objects;

import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private final String email;

    public Email(String email){
        if (! EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException(
                    "Invalid email format"
            );
        this.email = email;
    }


    public String getValue(){
        return email;
    }

}
