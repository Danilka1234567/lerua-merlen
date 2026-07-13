package model.entities;

import model.entities.abstract_entities.PhysicallyContactableEntity;
import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public class Warehouse extends PhysicallyContactableEntity {

    private int capacity;

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
        this.capacity = capacity;
    }
}
