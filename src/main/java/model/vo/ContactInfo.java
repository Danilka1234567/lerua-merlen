package model.vo;

public class ContactInfo {

    private final PhoneNumber phoneNumber;
    private final Email email;

    public ContactInfo(PhoneNumber phoneNumber, Email email){

        if (email == null)
            throw new IllegalArgumentException(
                    "email can't be null"
            );

        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public Email getEmail() {
        return email;
    }
}
