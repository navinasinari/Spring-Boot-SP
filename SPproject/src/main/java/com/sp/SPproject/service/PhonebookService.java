package com.sp.SPproject.service;
import com.sp.SPproject.repository.PhonebookRepository;
import org.audit4j.core.annotation.Audit;
import org.audit4j.core.annotation.AuditField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sp.SPproject.api.model.Phonebook;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PhonebookService {

    @Autowired
    PhonebookRepository phonebookRepository;
    @Audit(action = "Added a phonebook")
    public ResponseEntity<String> addPhonebook(@AuditField(field = "phoneBook") Phonebook phonebook){
        String patternNumber =
                "^((\\+[1-9]{1,2}\\s?)|(\\d{1,3}\\s?))?(\\d)?[\\s.-]?\\(?[1-9][0-9]?[0-9]\\)?[\\s.-]?\\d{3}[\\s.-]\\d{4}$"
                +"|^\\d{5}[\\s.]\\d{5}$"
                +"|^\\d{3}-\\d{4}$"
                +"|^\\d{5}$"
                +"|^\\+?\\d{2}\\s\\d{2}\\s\\d{2}\\s\\d{2}\\s?(\\d{2})?\\s?$";
        Pattern match = Pattern.compile(patternNumber);

        String patternName = "^(\\s?[a-zA-Z]+[-,']?\\s?[a-zA-Z].?){1,3}";
        Pattern matchName = Pattern.compile(patternName);
        if(match.matcher(phonebook.getPhoneNumber()).matches() && matchName.matcher(phonebook.getName()).matches()) {
            try {
                Phonebook _phonebook = phonebookRepository.save(phonebook);
            } catch (Exception e) {
                return new ResponseEntity<>("Message: Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Message: Invalid Input", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Message: Success", HttpStatus.OK);
    }

    @Audit(action = "Fetched phonebook list")
    public List<Phonebook> getPhonebookList(){
        List<Phonebook> phonebookList = new ArrayList<Phonebook>();
        phonebookRepository.findAll().forEach(phonebookList::add);
        return phonebookList;
    }

    @Audit(action = "Deleted by name")
    public ResponseEntity<String> deleteByName(@AuditField(field = "name") String name){
        try{
            String patternName = "^(\\s?[a-zA-Z]+[-,']?\\s?[a-zA-Z].?){1,3}";
            Pattern matchName = Pattern.compile(patternName);

            if(matchName.matcher(name).matches()) {
                Phonebook ph = phonebookRepository.findByName(name);
                if(ph!=null){
                    phonebookRepository.delete(ph);
                    return new ResponseEntity<>("Message: Success", HttpStatus.OK);
                }
                return new ResponseEntity<>("Message: Not Found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Message: Invalid Input", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Audit(action = "Deleted by phone number")
    public ResponseEntity<String> deleteByNumber(@AuditField(field = "phoneNumber") String phoneNumber){
        try{
            String patternNumber =
                    "^((\\+[1-9]{1,2}\\s?)|(\\d{1,3}\\s?))?(\\d)?\\s?\\(?[1-9][0-9]?[0-9]\\)?[\\s.-]?\\d{3}[\\s.-]\\d{4}$"
                    +"|^\\d{5}[\\s.]\\d{5}$"
                    +"|^\\d{3}-\\d{4}$"
                    +"|^\\d{5}$"
                    +"|^\\+?\\d{2}\\s\\d{2}\\s\\d{2}\\s\\d{2}\\s?(\\d{2})?\\s?$";
            Pattern match = Pattern.compile(patternNumber);

            if(match.matcher(phoneNumber).matches()) {
                Phonebook ph = phonebookRepository.findByPhoneNumber(phoneNumber);
                if(ph!=null){
                    phonebookRepository.delete(ph);
                    return new ResponseEntity<>("Message: Success", HttpStatus.OK);
                }
                return new ResponseEntity<>("Message: Not Found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Message: Invalid Input", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}