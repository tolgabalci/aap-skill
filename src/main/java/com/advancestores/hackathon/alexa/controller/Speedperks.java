package com.advancestores.hackathon.alexa.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advancestores.hackathon.alexa.model.AapDBRepository;
import com.advancestores.hackathon.alexa.model.AlexaUser;
import com.advancestores.hackathon.alexa.model.SpeedPerkDetails;
import com.advancestores.hackathon.alexa.service.SpeedPerkService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/speedperks")
@Log4j2
public class Speedperks {

    @Autowired
    AapDBRepository aapDBRepository;

    @Autowired
    SpeedPerkService speedPerkService;

    @GetMapping("/coupons/{accountNumber}")
    public ResponseEntity<String> getSpeedPerkDetails(@PathVariable final String accountNumber)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        log.info("Get Coupons for accountNumber ->" + accountNumber);
        return speedPerkService.getCoupons(accountNumber);
    }
    
    @GetMapping("/members")
    public ResponseEntity<String> getMembers(@RequestParam(name="phone") String phone, 
    		@RequestParam(name="accountNumber") String accountNumber){
    	log.info("Get members by phone number ->" + phone);
    	return speedPerkService.getMembers(phone, accountNumber);
    }
    
    @GetMapping("/GetSpeedPerks")
    public ResponseEntity<SpeedPerkDetails> getSpeedPerksByUser(@RequestParam(name="userId") String userId){
    	log.info("Get Speed Perks By User Id->" + userId);
    	SpeedPerkDetails details = speedPerkService.getSpeedPerksByUser(userId);
    	ResponseEntity<SpeedPerkDetails> response = details != null 
    			? new ResponseEntity<SpeedPerkDetails>(details, HttpStatus.OK)
    			: new ResponseEntity<SpeedPerkDetails>(details, HttpStatus.NOT_FOUND);
    	return response;
    }
 
    @GetMapping(value = "foo/{alexaUserId}")
    public ResponseEntity<AlexaUser> getFooByAlexaUserId(@PathVariable final String alexaUserId) {
        //final AlexaUser alexaUser = new AlexaUser();
        // alexaUser.setAlexaUserId(alexaUserId);
        // alexaUser.setSpeedPerksMemberId("SomeId12345"); 
        // alexaUser.setSpeedPerksPhone("540-555-1212");
        // aapDBRepository.save(alexaUser);

        final AlexaUser user = aapDBRepository.findByAlexaUserId(alexaUserId);
        if (user != null) {
            log.info("found user with phone number: " + user.getSpeedPerksPhone());            
            return new ResponseEntity<AlexaUser>(user, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<AlexaUser>(user, HttpStatus.NOT_FOUND);
        }        
    }

}
