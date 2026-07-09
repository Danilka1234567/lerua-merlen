package model.entities;

import model.entities.abstract_entities.PhysicallyContactableEntity;
import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public class Manufacturer extends PhysicallyContactableEntity {

    private String specialization;

    public Manufacturer(Email email, PhoneNumber phoneNumber, String name, Address address, String specialization) {
        super(email, phoneNumber, name, address);
        setSpecialization(specialization);
    }

    public Manufacturer(Long id, Email email, PhoneNumber phoneNumber, String name, Address address, String specialization) {
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
