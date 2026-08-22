package model.entity.abstr;

import model.vo.Id;

import java.time.LocalDate;

public abstract class ExtendedEntity extends BaseEntity {

    private final LocalDate registrationDate;

    protected ExtendedEntity() {
        super();
        registrationDate = LocalDate.now();
    }

    protected ExtendedEntity(Id id, boolean isDeleted, LocalDate registrationDate){
        super(id, isDeleted);

        if(registrationDate == null)
            throw new IllegalArgumentException(
                    "Can't set registration date value as null"
            );
        this.registrationDate = registrationDate;
    }


    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
}
