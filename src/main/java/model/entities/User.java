package model.entities;

import model.entities.abstract_entities.ContactableEntity;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public class User extends ContactableEntity {

    private String surname;


    public User(Email email, PhoneNumber phoneNumber, String name, String surname) {
        super(email, phoneNumber, name);
        setSurname(surname);
    }

    public User(Long id, Email email, PhoneNumber phoneNumber, String name, String surname) {
        super(id, email, phoneNumber, name);
        setSurname(surname);
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
