package model.entity.abstr;

public abstract class BaseEntity {

    private Long id;
    private final boolean isDeleted;

    public BaseEntity(){
        isDeleted = false;
    }

    public BaseEntity(Long id, boolean isDeleted){
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

    public void setId(Long id){
        if (id != null)
            throw new RuntimeException(
                    "You can't change id value! Settable only if null"
            );

        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
