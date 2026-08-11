package model.entity;

import model.entity.abstr.OrganizationEntity;
import model.vo.ContactInfo;
import model.vo.FullAddress;

import java.time.LocalDate;

public class Warehouse extends OrganizationEntity {

    private int capacity;

    public Warehouse(ContactInfo contactInfo, FullAddress fullAddress, int capacity) {
        super(contactInfo, fullAddress);
        setCapacity(capacity);
    }

    public Warehouse(Long id, boolean isDeleted, LocalDate registrationDate, ContactInfo contactInfo, FullAddress fullAddress, int capacity) {
        super(id, isDeleted, registrationDate, contactInfo, fullAddress);
        setCapacity(capacity);
    }


    private void setCapacity(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException(
                    "capacity can't be less than zero or equal to it"
            );

        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
