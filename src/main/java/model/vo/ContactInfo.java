package model.vo;

public class ContactInfo {

    private final PhoneNumber phoneNumber;
    private final Email email;

    public ContactInfo(PhoneNumber phoneNumber, Email email){

        if (phoneNumber == null)
            throw new NullPointerException(
                    "Phone number can't be null"
            );

        if (email == null)
            throw new NullPointerException(
                    "Email can't be null"
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
