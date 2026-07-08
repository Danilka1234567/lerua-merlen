package domain.physically_contactable_entities;

import domain.abstract_entities.PhysicallyContactableEntity;

public class Manufacturers extends PhysicallyContactableEntity {

    private String specialization;

    public Manufacturers(String email, String phoneNumber, String name, String address, String specialization) {
        super(email, phoneNumber, name, address);
        this.specialization = specialization;
    }

    public Manufacturers(Long id, String email, String phoneNumber, String name, String address, String specialization) {
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
