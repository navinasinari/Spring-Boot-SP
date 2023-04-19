package com.sp.SPproject.repository;

import com.sp.SPproject.api.model.Phonebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PhonebookRepository extends JpaRepository<Phonebook, String> {
    Phonebook findByPhoneNumber(String phonenumber);
    Phonebook findByName(String name);
}
