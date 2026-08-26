package model.entity.abstr;

import model.vo.Id;

import java.util.Objects;
import java.util.Optional;

public abstract class BaseEntity{

    private Id id;
    private final boolean isDeleted;

    protected BaseEntity(){
        isDeleted = false;
    }

    protected BaseEntity(Id id, boolean isDeleted){
        if (id == null)
            throw new IllegalArgumentException(
                    "Id can't be null"
            );

        this.id = id;
        this.isDeleted = isDeleted;
    }

    public boolean isDeleted(){
        return isDeleted;
    }

    public void setId(Id id){
        if (this.id != null)
            throw new RuntimeException(
                    "You can't change id value! Settable only if null"
            );

        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass())
            return false;

        if (obj == this) return true;
        return Objects.equals(id, ((BaseEntity) obj).getId());
    }

    public Id getId() {
        return id;
    }
}
