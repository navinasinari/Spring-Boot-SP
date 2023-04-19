package com.sp.SPproject.api.controller;
import com.sp.SPproject.api.model.Phonebook;
import com.sp.SPproject.service.PhonebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/PhoneBook")
public class PhonebookController {
    @Autowired
    private PhonebookService phonebookService;

    @GetMapping("/list")
    public List<Phonebook> getPhoneBookList(){
        return this.phonebookService.getPhonebookList();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPhoneBook(@RequestBody Phonebook phonebook){
        return this.phonebookService.addPhonebook(phonebook);
    }

    @PutMapping("/deleteByName")
    public ResponseEntity<String> deleteByName(@RequestParam String name){
        return this.phonebookService.deleteByName(name);
    }

    @PutMapping("/deleteByNumber")
    public ResponseEntity<String> deleteByNumber(@RequestParam String phoneNumber){
        return this.phonebookService.deleteByNumber(phoneNumber);
    }
}
