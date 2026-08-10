package model.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PhoneNumber extends BaseVO{

    private static final Pattern RU_PHONE_RAW = Pattern.compile(
            "^(?:\\+7|7|8)?[\\s.\\-]*(?:\\([3489]\\d{2}\\)|[3489]\\d{2})[\\s.\\-]*\\d{3}[\\s.\\-]*\\d{2}[\\s.\\-]*\\d{2}$"
    );


    public PhoneNumber(String value) {
        super(value);
    }

    @Override
    protected List<String> getViolations(String value) {
        List<String> violations = new ArrayList<>();
        if (! RU_PHONE_RAW.matcher(value).matches())
            violations.add("invalid phone number format");
        return violations;
    }

    private static String normalizeValue(String value){
        return value.replaceAll("\\D", "");
    }

    @Override
    public String getValue() {
        return normalizeValue(super.getValue());
    }
}
