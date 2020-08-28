package com.advancestores.hackathon.alexa.controller;

import com.advancestores.hackathon.alexa.model.BatteryDetails;

import com.advancestores.hackathon.alexa.model.DieHardDBRepository;
import com.advancestores.hackathon.alexa.service.DieHardService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/diehard")
@Log4j2
public class DieHardBatteryController {

    @Autowired
    DieHardService dieHardService;
    @Autowired
    DieHardDBRepository dieHardDBRepository;

    @GetMapping("/{make}")
    public ResponseEntity<BatteryDetails> getDieHardBatteriesByMakeModelYear(@PathVariable(name = "make") String make,
    		@RequestParam String model, @RequestParam String year){
      
    	log.info("Vehicle make ->" + make + ", Model ->" + model + " and Year ->"+ year);
    	BatteryDetails batteryDetails = dieHardService.findDieHardBatteries(make, model, year);
    	
        return new ResponseEntity<BatteryDetails>(batteryDetails, HttpStatus.OK);

    }

    @PostMapping
    public BatteryDetails setDieHardBatteriesByMakeModelYear(@RequestBody BatteryDetails dieHardBattery){

       return dieHardDBRepository.save(dieHardBattery);
    }
}