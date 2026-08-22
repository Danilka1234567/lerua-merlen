package model.entity.abstr;

import model.vo.ContactInfo;
import model.vo.Id;

import java.time.LocalDate;

public abstract class ContactableEntity extends ExtendedEntity {

    private ContactInfo contactInfo;

    protected ContactableEntity(ContactInfo contactInfo){
        super();
        setContactInfo(contactInfo);
    }

    protected ContactableEntity(Id id, boolean isDeleted, LocalDate registrationDate, ContactInfo contactInfo) {
        super(id, isDeleted, registrationDate);
        setContactInfo(contactInfo);
    }

    private void setContactInfo(ContactInfo contactInfo){
        if (contactInfo == null)
            throw new IllegalArgumentException(
                    "Contact info can't be null"
            );
        this.contactInfo = contactInfo;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }
}
