package model.dto.request;

import model.entities.User;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public class UserRequestDto{

    private Email email;
    private PhoneNumber phoneNumber;
    private String name;
    private String surname;

    public UserRequestDto(Email email, PhoneNumber phoneNumber, String name, String surname) {
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setName(name);
        setSurname(surname);
    }

    public void setEmail(Email email) {
        if (email == null)
            throw new IllegalArgumentException(
                    "Can't assign email to user dto: email is null"
            );

        this.email = email;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {

        if (phoneNumber == null)
            throw new IllegalArgumentException(
                    "Can't assign phone number to user dto: phone number is null"
            );

        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {

        if (name == null || name.isBlank() || name.length() < 3)
            throw new IllegalArgumentException(
                    "Can't assign name to user dto: name must contain at least 3 letters"
            );
        this.name = name;
    }

    public void setSurname(String surname) {

        if (surname == null || surname.isBlank() || surname.length() < 3)
            throw new IllegalArgumentException(
                    "Can't assign surname to user dto: surname must contatin at lest 3 letters"
            );
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }
}
