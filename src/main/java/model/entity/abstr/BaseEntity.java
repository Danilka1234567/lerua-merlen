package model.entity.abstr;

import model.vo.Id;

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

    public Id getId() {
        return id;
    }
}
