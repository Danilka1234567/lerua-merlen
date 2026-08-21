package model.entity.abstr;

import model.vo.ContactInfo;
import model.vo.FullAddress;

import java.time.LocalDate;

public abstract class OrganizationEntity extends ContactableEntity {

    private FullAddress fullAddress;

    protected OrganizationEntity(ContactInfo contactInfo, FullAddress fullAddress) {
        super(contactInfo);
        setFullAddress(fullAddress);
    }

    protected OrganizationEntity(Long id, boolean isDeleted, LocalDate registrationDate,
                              ContactInfo contactInfo, FullAddress fullAddress) {
        super(id, isDeleted, registrationDate, contactInfo);
        setFullAddress(fullAddress);
    }

    private void setFullAddress(FullAddress fullAddress){
        if (fullAddress == null)
            throw new IllegalArgumentException(
                    "Full address can't be null"
            );
        this.fullAddress = fullAddress;
    }

    public FullAddress getFullAddress(){
        return fullAddress;
    }
}
