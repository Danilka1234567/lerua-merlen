package model.entity;

import model.entity.abstr.OrganizationEntity;
import model.vo.ContactInfo;
import model.vo.FullAddress;

import java.time.LocalDate;

public class Warehouse extends OrganizationEntity {

    private int capacity;

    public static Warehouse createNew(ContactInfo contactInfo, FullAddress fullAddress, int capacity){
        return new Warehouse(contactInfo, fullAddress, capacity);
    }

    public static Warehouse loadFromDb(Long id, boolean isDeleted, LocalDate registrationDate,
                                       ContactInfo contactInfo, FullAddress fullAddress, int capacity){
        return new Warehouse(id, isDeleted, registrationDate, contactInfo, fullAddress, capacity);
    }

    private Warehouse(ContactInfo contactInfo, FullAddress fullAddress, int capacity) {
        super(contactInfo, fullAddress);
        setCapacity(capacity);
    }

    private Warehouse(Long id, boolean isDeleted, LocalDate registrationDate, ContactInfo contactInfo, FullAddress fullAddress, int capacity) {
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
