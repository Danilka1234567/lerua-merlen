package domain.contactable_entities;

import domain.abstract_entities.ContactableEntity;

public class UserEntity extends ContactableEntity {

    private String surname;


    public UserEntity(String email, String phoneNumber, String name, String surname) {
        super(email, phoneNumber, name);
        setSurname(surname);
    }

    public UserEntity(Long id, String email, String phoneNumber, String name, String surname) {
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
