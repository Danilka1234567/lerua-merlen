package domain.abstract_entities;

import domain.value_objects.Address;
import domain.value_objects.Email;
import domain.value_objects.PhoneNumber;

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
        if (address == null)
            throw new IllegalArgumentException(
                    "Adress can't be null"
            );
        this.address = address;
    }
}
