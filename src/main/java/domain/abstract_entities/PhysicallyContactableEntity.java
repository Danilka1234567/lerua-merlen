package domain.abstract_entities;

import utils.Validator;

public class PhysicallyContactableEntity extends ContactableEntity {

    private String address;

    public PhysicallyContactableEntity(String email, String phoneNumber, String name, String address) {
        super(email, phoneNumber, name);
        setAddress(address);
    }

    public PhysicallyContactableEntity(Long id, String email, String phoneNumber, String name, String address) {
        super(id, email, phoneNumber, name);
        setAddress(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {

        if (!Validator.isValidAddress(address))
            throw new IllegalArgumentException(
                    "Invalid address"
            );

        this.address = address;
    }
}
