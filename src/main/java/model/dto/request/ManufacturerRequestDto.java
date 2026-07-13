package model.dto.request;

import model.entities.Manufacturer;
import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public class ManufacturerRequestDto{

    private Email email;
    private PhoneNumber phoneNumber;
    private String name;
    private Address address;
    private String specialization;

    public ManufacturerRequestDto(Email email, PhoneNumber phoneNumber, String name, Address address, String specialization) {
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setName(name);
        setAddress(address);
        setSpecialization(specialization);
    }

    public void setEmail(Email email) {

        if (email == null)
            throw new IllegalArgumentException(
                    "Can't assign email to manufacturer dto: email is null"
            );
        this.email = email;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber == null)
            throw new IllegalArgumentException(
                    "Can't assign phoneNumber to manufacturer dto: number is null"
            );
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException(
                    "Can't assign name to manufacturer: name can't be empty"
            );
        this.name = name;
    }

    public void setAddress(Address address) {
        if(address == null)
            throw new IllegalArgumentException(
                    "Can't assign address to manufacturer: address is null"
            );
        this.address = address;
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.isBlank())
            throw new IllegalArgumentException(
                    "Can't assign specialization to manufacturer: spec. is null or empty"
            );
        this.specialization = specialization;
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

    public String getSpecialization() {
        return specialization;
    }
}
