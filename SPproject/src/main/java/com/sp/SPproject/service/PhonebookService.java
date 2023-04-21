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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class PhonebookService {

    @Autowired
    PhonebookRepository phonebookRepository;
    @Audit(action = "Added a phonebook")
    public ResponseEntity<?> addPhonebook(@AuditField(field = "phoneBook") Phonebook phonebook){
        Map<String, String> resp = new HashMap<>();

        String patternNumber =
                "^((\\+[1-9]{1,2}\\s?)|(\\d{1,3}\\s?))?(\\d)?[\\s.-]?\\(?[1-9][0-9]?[0-9]\\)?[\\s.-]?\\d{3}[\\s.-]\\d{4}$"
                +"|^\\d{5}[\\s.]\\d{5}$"
                +"|^\\d{3}-\\d{4}$"
                +"|^\\d{5}$"
                +"|^\\+?\\d{2}\\s\\d{2}\\s\\d{2}\\s\\d{2}\\s?(\\d{2})?\\s?$";
        Pattern match = Pattern.compile(patternNumber);

        String patternName = "^(\\s?[a-zA-Z]+[-,']?\\s?[a-zA-Z].?){1,3}$";
        Pattern matchName = Pattern.compile(patternName);
        if(match.matcher(phonebook.getPhoneNumber()).matches() && matchName.matcher(phonebook.getName()).matches()) {
            try {
                Phonebook _phonebook = phonebookRepository.save(phonebook);
            } catch (Exception e) {
                return new ResponseEntity<>("Message: Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.put("Status", "400");
            resp.put("Message", "Invalid Input");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        resp.put("Status", "200");
        resp.put("Message", "Success");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Audit(action = "Fetched phonebook list")
    public List<Phonebook> getPhonebookList(){
        List<Phonebook> phonebookList = new ArrayList<>();
        phonebookRepository.findAll().forEach(phonebookList::add);
        return phonebookList;
    }

    @Audit(action = "Deleted by name")
    public ResponseEntity<?> deleteByName(@AuditField(field = "name") String name){
        Map<String, String> resp = new HashMap<>();

        try{
            String patternName = "^(\\s?[a-zA-Z]+[-,']?\\s?[a-zA-Z].?){1,3}";
            Pattern matchName = Pattern.compile(patternName);

            if(matchName.matcher(name).matches()) {
                Phonebook ph = phonebookRepository.findByName(name);
                if(ph!=null){
                    phonebookRepository.delete(ph);
                    resp.put("Status", "200");
                    resp.put("Message", "Success");
                    return new ResponseEntity<>(resp, HttpStatus.OK);
                }
                resp.put("Status", "404");
                resp.put("Message", "Not Found");
                return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
            }
            resp.put("Status", "400");
            resp.put("Message", "Invalid Input");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Audit(action = "Deleted by phone number")
    public ResponseEntity<?> deleteByNumber(@AuditField(field = "phoneNumber") String phoneNumber){
        try{
            Map<String, String> resp = new HashMap<>();

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
                    resp.put("Status", "200");
                    resp.put("Message", "Success");
                    return new ResponseEntity<>(resp, HttpStatus.OK);
                }
                resp.put("Status", "404");
                resp.put("Message", "Not Found");
                return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
            }
            resp.put("Status", "400");
            resp.put("Message", "Invalid Input");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}