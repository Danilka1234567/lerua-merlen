package domain.entities.physically_contactable_entities;

import domain.entities.abstract_entities.PhysicallyContactableEntity;
import domain.value_objects.Address;
import domain.value_objects.Email;
import domain.value_objects.PhoneNumber;

public class WarehouseEntity extends PhysicallyContactableEntity {

    private int capacity;
    private final static int MINIMAL_CAPACITY = 100;

    public WarehouseEntity(Email email, PhoneNumber phoneNumber, String name, Address address,
                           int capacity) {
        super(email, phoneNumber, name, address);
        setCapacity(capacity);
    }

    public WarehouseEntity(Long id, Email email, PhoneNumber phoneNumber, String name,
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
