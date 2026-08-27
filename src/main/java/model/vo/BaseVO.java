package model.vo;

import java.util.List;
import java.util.Objects;

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
                    "validation error:" + String.join("\n", violations)
            );
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass())
            return false;
        if (obj == this)
            return true;
        return ((BaseVO<?>) obj).getValue().equals(value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    protected abstract List<String> getViolations(T value);

    public T getValue(){
        return value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().toLowerCase() + ":" + value;
    }
}
