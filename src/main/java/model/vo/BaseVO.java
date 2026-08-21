package model.vo;

import java.util.List;

public abstract class BaseVO<T> {

    private final T value;

    public BaseVO(T value){

        if (value == null)
            throw new IllegalArgumentException(
                    "vo value can't be null"
            );

        List<String> violations = getViolations(value);
        if (! violations.isEmpty()){
            throw new IllegalArgumentException(
                    "vo validation error:" + String.join("\n", violations)
            );
        }

        this.value = value;
    }

    protected abstract List<String> getViolations(T value);

    public T getValue(){
        return value;
    }
}
