package com.advancestores.hackathon.alexa.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

import com.advancestores.hackathon.alexa.service.SpeedPerkService;

@Log4j2
@RestController
public class Speedperks {

    @Autowired
    SpeedPerkService speedPerkService;

    @GetMapping("/speedperks/{id}")
    public ResponseEntity<String> getSpeedPerkDetails(@PathVariable String id)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    
        log.info("getSpeedPerkDetails called for id " + id);

        ResponseEntity<String> response= speedPerkService.getCoupons(id);
        return response;
    }
}
