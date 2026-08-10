package model.vo;

import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.regex.Pattern;

public class Password extends BaseVO{

    public Password(String value) {
        super(value);
    }

    @Override
    protected List<String> getViolations(String value) {

        List<String> violations = new ArrayList<>();

        if (value == null || value.length() < 8)
            violations.add("Password is too short. Minimum length is 8");

        boolean hasSpecialSymbols = false;
        boolean hasCapitalLetter = false;
        boolean hasSmallLetter = false;
        boolean hasNumbers = false;

        String capitalLetterRegex = "[A-ZА-ЯЁ]";
        String smallLetterRegex = "[a-zа-яё]";
        String numsRegex = "[0-9]";

        for (int i = 0; i < value.length(); i++){

            char symbol = value.charAt(i);

            if (symbol == ' '){
                violations.add("Spacebar is unsupported symbol");
            } else if (Character.isUpperCase(symbol)){
                hasCapitalLetter = true;
            } else if (Character.isLowerCase(symbol)) {
                hasSmallLetter = true;
            } else if (Character.isDigit(symbol)) {
                hasNumbers = true;
            } else {
                hasSpecialSymbols = true;
            }

            if (hasSpecialSymbols && hasCapitalLetter && hasSmallLetter && hasNumbers)
                break;

        }

        if (! hasSpecialSymbols)
            violations.add("Password must include at least one special symbol");
        if (! hasCapitalLetter)
            violations.add("Password must include at least one capital letter");
        if (! hasSmallLetter)
            violations.add("Password must include at least one small letter");
        if (! hasNumbers)
            violations.add("Password must include at least one number");

        return violations;
    }
}
