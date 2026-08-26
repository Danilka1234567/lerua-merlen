package model.vo;

import java.util.ArrayList;
import java.util.List;

public class Password extends BaseVO<String>{

    public Password(String value) {
        super(value);
    }

    @Override
    protected List<String> getViolations(String value) {

        List<String> violations = new ArrayList<>();

        if (value == null){
            violations.add("password can't be null.");
            return violations;
        }

        if (value.length() < 8)
            violations.add("password is too short. minimum length is 8");


        if (value.length() > 64)
            violations.add("password is too long. maximum length is 64");

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || this.getClass() != obj.getClass())
            return false;

        return getValue().equals(((Password) obj).getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
