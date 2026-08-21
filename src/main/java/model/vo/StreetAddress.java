package model.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StreetAddress extends BaseVO<String>{

    private static final Pattern RU_STREET_ADDRESS = Pattern.compile(
            "^(?:ул(?:ица)?|пр(?:оспект)?|пер(?:еулок)?|пл(?:ощадь)?|" +
                    "бул(?:ьвар)?|наб(?:ережная)?|шоссе|проезд|аллея|тупик)\\.?\\s+" +
                    "[А-Яа-яЁёA-Za-z0-9\\s.\\-'\"()]+" +
                    ",\\s*" +
                    "(?:г\\.?\\s*)?[А-Яа-яЁёA-Za-z\\s.\\-]+" +
                    "(?:,\\s*(?:д\\.?\\s*)?\\d+[А-Яа-я]?" +
                    "(?:\\s*[/\\\\]\\s*" +
                    "(?:к(?:орп(?:ус)?)?|стр(?:оение)?|лит(?:ера)?)" +
                    "[\\s.\\-]*\\d+[А-Яа-я]?)?)?" +
                    "(?:,\\s*(?:кв(?:артира)?|оф(?:ис)?|пом(?:ещение)?)\\.?\\s*\\d+[А-Яа-я]?)?$"
    );

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
