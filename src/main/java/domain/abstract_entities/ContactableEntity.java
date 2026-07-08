package domain.abstract_entities;

import utils.Validator;

public abstract class ContactableEntity extends BaseEntity {

    private String email;
    private String phoneNumber;
    private String name;

    public ContactableEntity(String email, String phoneNumber, String name){
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setName(name);
    }

    public ContactableEntity(Long id, String email, String phoneNumber, String name){
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

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {

        if (! Validator.isValidEmail(email))
            throw new IllegalArgumentException(
                    "Invalid email!"
            );

        this.email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {

        if (! Validator.isValidNumber(phoneNumber))
            throw new IllegalArgumentException(
                    "Invalid phone number!"
            );

        this.phoneNumber = Validator.getNormalizedPhoneNumber(phoneNumber);
    }
}
