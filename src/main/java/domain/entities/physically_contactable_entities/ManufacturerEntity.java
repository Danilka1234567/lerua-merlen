package domain.entities.physically_contactable_entities;

import domain.entities.abstract_entities.PhysicallyContactableEntity;
import domain.value_objects.Address;
import domain.value_objects.Email;
import domain.value_objects.PhoneNumber;

public class ManufacturerEntity extends PhysicallyContactableEntity {

    private String specialization;

    public ManufacturerEntity(Email email, PhoneNumber phoneNumber, String name, Address address, String specialization) {
        super(email, phoneNumber, name, address);
        setSpecialization(specialization);
    }

    public ManufacturerEntity(Long id, Email email, PhoneNumber phoneNumber, String name, Address address, String specialization) {
        super(id, email, phoneNumber, name, address);
        setSpecialization(specialization);
    }


    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {

        if (specialization == null || specialization.isBlank())
            throw new IllegalArgumentException(
                    "specialization can't be empty!"
            );

        this.specialization = specialization;
    }

}
