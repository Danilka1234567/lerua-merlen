package domain;

import utils.Validator;

public abstract class ContactableEntity extends BaseEntity {

    private String email;
    private String phoneNumber;

    public ContactableEntity(String email, String phoneNumber){
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public ContactableEntity(Long id, String email, String phoneNumber){
        this(email, phoneNumber);
        setId(id);
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
