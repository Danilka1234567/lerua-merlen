package model.vo;

import java.util.List;

public abstract class BaseVO {

    private final String value;

    public BaseVO(String value){

        if (value == null)
            throw new NullPointerException(
                    "VO value can't be null"
            );

        List<String> violations = getViolations(value);
        if (! violations.isEmpty()){
            throw new IllegalArgumentException(
                    "VO validation error:" + String.join("\n", violations)
            );
        }

        this.value = value;
    }

    protected abstract List<String> getViolations(String value);

    public String getValue(){
        return value;
    }
}
