package model.dto.request;

import model.entities.Warehouse;
import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public class WarehouseRequestDto{

    private Email email;
    private PhoneNumber phoneNumber;
    private String name;
    private Address address;
    private int capacity;

    public WarehouseRequestDto(Email email, PhoneNumber phoneNumber, String name, Address address, int capacity) {
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setName(name);
        setAddress(address);
        setCapacity(capacity);
    }

    public void setEmail(Email email) {

        if (email == null)
            throw new IllegalArgumentException(
                    "Can't assign email to warehouse dto: email is null"
            );

        this.email = email;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {

        if (phoneNumber == null)
            throw new IllegalArgumentException(
                    "Can't assign phoneNumber to warehouse dto: phoneNumber is null"
            );
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setAddress(Address address) {
        if (address == null)
            throw new IllegalArgumentException(
                    "Can't assign address to warehouse dto: address is null"
            );
        this.address = address;
    }

    public void setCapacity(int capacity) {

        if (capacity < 0)
            throw new IllegalArgumentException(
                    "Can't assign capacity to warehouse dto: capacity must be bigger than 0"
            );
        this.capacity = capacity;
    }

    public Email getEmail() {
        return email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }
}
