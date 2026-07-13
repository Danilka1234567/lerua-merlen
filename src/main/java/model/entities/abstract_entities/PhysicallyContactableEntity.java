package model.entities.abstract_entities;

import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public abstract class PhysicallyContactableEntity extends ContactableEntity {

    private Address address;

    public PhysicallyContactableEntity(Email email, PhoneNumber phoneNumber,
                                       String name, Address address) {
        super(email, phoneNumber, name);
        setAddress(address);
    }

    public PhysicallyContactableEntity(Long id, Email email, PhoneNumber phoneNumber,
                                       String name, Address address) {
        super(id, email, phoneNumber, name);
        setAddress(address);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
