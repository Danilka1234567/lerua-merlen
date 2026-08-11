package model.entity;

import model.entity.abstr.ContactableEntity;
import model.enums.UserRole;
import model.vo.ContactInfo;
import model.vo.Password;

import java.time.LocalDate;

public class User extends ContactableEntity {

    private String name;
    private Password password;
    private UserRole role;

    public User(ContactInfo contactInfo, String name, Password password, UserRole role) {
        super(contactInfo);
        setName(name);
        setPassword(password);
        setRole(role);
    }

    public User(Long id, boolean isDeleted, LocalDate registrationDate, ContactInfo contactInfo, String name, Password password, UserRole role) {
        super(id, isDeleted, registrationDate, contactInfo);
        setName(name);
        setPassword(password);
        setRole(role);
    }


    private void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException(
                    "name can't be null"
            );

        if (name.length() > 64)
            throw new IllegalArgumentException(
                "name can't be more than 64 symbols in length"
            );

        this.name = name;
    }

    private void setPassword(Password password) {

        if (password == null)
            throw new IllegalArgumentException(
                    "password can't be null"
            );

        this.password = password;
    }

    private void setRole(UserRole role) {
        this.role = role;
    }


    public String getName() {
        return name;
    }

    public Password getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}
