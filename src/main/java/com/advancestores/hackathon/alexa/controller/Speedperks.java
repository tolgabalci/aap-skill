package com.advancestores.hackathon.alexa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Speedperks {

    @GetMapping("/speedperks")
    public ResponseEntity<String> getSpeedPerkDetails(){
        return new ResponseEntity<String>("todo speedperks", HttpStatus.OK);        
    }
}
