package model.vo;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Email extends BaseVO{

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?=[^@]{1,64}@)(?=.{1,255}$)[A-Za-z0-9_%+-]+(\\.[A-Za-z0-9_%+-]+)*@" +
                    "[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?" +
                    "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?)*" +
                    "\\.[A-Za-z]{2,63}$"
    );

    public Email(String value) {
        super(value);
    }

    @Override
    protected List<String> getViolations(String value) {

        List<String> violations = new ArrayList<>();
        if (! EMAIL_PATTERN.matcher(value).matches()){
            violations.add("not an email");
        }

        if (value.length() > 255)
            violations.add("email is too long. maximum length is 255 symbols");

        return violations;
    }
}
