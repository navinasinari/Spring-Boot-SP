package com.sp.SPproject.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PHONEBOOK")
public class Phonebook {
    @Column(name = "NAME")
    private String name = null;
    @Column(name = "PHONENUMBER")
    @Id
    private String phoneNumber = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
