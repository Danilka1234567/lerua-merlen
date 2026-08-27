package model.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StreetAddress extends BaseVO<String>{

    private static final Pattern RU_STREET_ADDRESS = Pattern.compile(
            "^(?i)(ул\\.?|улица|просп\\.?|проспект|пер\\.?|переулок|пл\\.?|площадь|ш\\.?|шоссе|бульв\\.?|бульвар)"  +
                    "?\\s*([А-Яа-яЁё0-9\\s\\.\\-]+),\\s*(д\\.?|дом)?\\s*([0-9]+[а-яА-Я\\s]*(\\/[0-9]+)?)$");

    public StreetAddress(String value) {
        super(value);
    }

    @Override
    protected List<String> getViolations(String value) {
        List<String> violations = new ArrayList<>();
        if (! RU_STREET_ADDRESS.matcher(value).matches()){
            violations.add("invalid address format");
        }

        if (value.length() > 128)
            violations.add("address is too long. maximum length is 128");

        return violations;
    }
}
