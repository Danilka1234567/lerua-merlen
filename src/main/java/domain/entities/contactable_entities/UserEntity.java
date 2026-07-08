package domain.entities.contactable_entities;

import domain.entities.abstract_entities.ContactableEntity;
import domain.value_objects.Email;
import domain.value_objects.PhoneNumber;

public class UserEntity extends ContactableEntity {

    private String surname;


    public UserEntity(Email email, PhoneNumber phoneNumber, String name, String surname) {
        super(email, phoneNumber, name);
        setSurname(surname);
    }

    public UserEntity(Long id, Email email, PhoneNumber phoneNumber, String name, String surname) {
        super(id, email, phoneNumber, name);
        setSurname(surname);
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {

        if (surname == null || surname.isBlank() || surname.length() < 2)
            throw new IllegalArgumentException(
                    "User's surname must contain at least three characters"
            );

        this.surname = surname;
    }
}
