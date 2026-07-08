package domain.physically_contactable_entities;

import domain.abstract_entities.PhysicallyContactableEntity;
import domain.value_objects.Address;
import domain.value_objects.Email;
import domain.value_objects.PhoneNumber;

public class Manufacturers extends PhysicallyContactableEntity {

    private String specialization;

    public Manufacturers(Email email, PhoneNumber phoneNumber, String name, Address address, String specialization) {
        super(email, phoneNumber, name, address);
        this.specialization = specialization;
    }

    public Manufacturers(Long id, Email email, PhoneNumber phoneNumber, String name, Address address, String specialization) {
        super(id, email, phoneNumber, name, address);
        this.specialization = specialization;
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
