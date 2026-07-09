package model.entities;

import model.entities.abstract_entities.PhysicallyContactableEntity;
import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public class Warehouse extends PhysicallyContactableEntity {

    private int capacity;
    private final static int MINIMAL_CAPACITY = 100;

    public Warehouse(Email email, PhoneNumber phoneNumber, String name, Address address,
                     int capacity) {
        super(email, phoneNumber, name, address);
        setCapacity(capacity);
    }

    public Warehouse(Long id, Email email, PhoneNumber phoneNumber, String name,
                     Address address, int capacity) {
        super(id, email, phoneNumber, name, address);
        setCapacity(capacity);
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {

        if (capacity < MINIMAL_CAPACITY)
            throw new IllegalArgumentException(
                    "To work with as warehouse must be able to contain at least %d goods".formatted(
                            MINIMAL_CAPACITY
                    )
            );

        this.capacity = capacity;
    }
}
