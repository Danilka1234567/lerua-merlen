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
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }


    public void setEmail(Email email) {
        this.email = email;
    }


    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
