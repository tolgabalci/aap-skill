package com.advancestores.hackathon.alexa.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advancestores.hackathon.alexa.service.SpeedPerkService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/speedperks")
@Log4j2
public class Speedperks {

    @Autowired
    SpeedPerkService speedPerkService;

    @GetMapping("/coupons/{accountNumber}")
    public ResponseEntity<String> getSpeedPerkDetails(@PathVariable String accountNumber) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    	log.info("Get Coupons for accountNumber ->"+accountNumber);
        return speedPerkService.getCoupons(accountNumber);
    }
    
    @GetMapping("/members/{phone}")
    public ResponseEntity<String> getMembersByPhone(@PathVariable String phone){
    	log.info("Get members by phone number ->" + phone);
    	return speedPerkService.getMembersByPhone(phone);
    }
}
