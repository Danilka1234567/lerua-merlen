package model.entity;

import model.entity.abstr.OrganizationEntity;
import model.vo.ContactInfo;
import model.vo.FullAddress;

import java.time.LocalDate;

public class Manufacturer extends OrganizationEntity {

    private String name;
    private String specialization;

    public static Manufacturer createNew(ContactInfo contactInfo, FullAddress fullAddress, String name, String specialization) {
        return new Manufacturer(contactInfo, fullAddress, name, specialization);
    }

    private Manufacturer(ContactInfo contactInfo, FullAddress fullAddress, String name, String specialization){
        super(contactInfo, fullAddress);
        setName(name);
        setSpecialization(specialization);
    }

    public static Manufacturer loadFromDb(Long id, boolean isDeleted, LocalDate registrationDate,
                                          ContactInfo contactInfo, FullAddress fullAddress,
                                          String name, String specialization){
        return new Manufacturer(id, isDeleted, registrationDate, contactInfo, fullAddress, name, specialization);
    }

    private Manufacturer(Long id, boolean isDeleted, LocalDate registrationDate,
                        ContactInfo contactInfo, FullAddress fullAddress, String name, String specialization) {
        super(id, isDeleted, registrationDate, contactInfo, fullAddress);
        setName(name);
        setSpecialization(specialization);
    }


    private void setName(String name) {

        if (name == null)
            throw new IllegalArgumentException(
                    "name can't be null"
            );

        if (name.length() > 64)
            throw new IllegalArgumentException(
                    "name is too big. maximum length is 64"
            );

        this.name = name;
    }

    private void setSpecialization(String specialization) {
        if (specialization == null)
            throw new IllegalArgumentException(
                    "specialization can't be null"
            );

        if (specialization.length() > 255)
            throw new IllegalArgumentException(
                    "specialization is too big. maximum length is 255"
            );

        this.specialization = specialization;
    }


    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }
}
