package model.entities.abstract_entities;

public abstract class BaseEntity {

    private Long id;


    public void setId(Long id){

        if (this.id != null)
            throw new RuntimeException(
                    "Field id can't be changed!"
            );

        this.id = id;
    }


    public Long getId(){
        return id;
    }

}
