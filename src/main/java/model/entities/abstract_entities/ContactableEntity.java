package model.entities.abstract_entities;


import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public abstract class ContactableEntity extends BaseEntity {

    private Email email;
    private PhoneNumber phoneNumber;
    private String name;

    public ContactableEntity(Email email, PhoneNumber phoneNumber, String name){
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setName(name);
    }

    public ContactableEntity(Long id, Email email, PhoneNumber phoneNumber, String name){
        this(email, phoneNumber, name);
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (name == null || name.isBlank() || name.length() < 2)
            throw new IllegalArgumentException(
                    "Name of contactable entity must contain at least 2 valid characters!"
            );

        this.name = name;
    }

    public Email getEmail() {
        return email;
    }


    public void setEmail(Email email) {

        if (email == null)
            throw new IllegalArgumentException(
                    "Email can't be null"
            );

        this.email = email;
    }


    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(PhoneNumber phoneNumber) {

        if (phoneNumber == null)
            throw new IllegalArgumentException(
                    "Phone number can't be null"
            );

        this.phoneNumber = phoneNumber;
    }
}
