package com.advancestores.hackathon.alexa.controller;

import com.advancestores.hackathon.alexa.model.BatteryDetails;

import com.advancestores.hackathon.alexa.model.DieHardDBRepository;
import com.advancestores.hackathon.alexa.service.DieHardService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diehard")
@Log4j2
public class DieHardBatteryController {

    @Autowired
    DieHardService dieHardService;
    @Autowired
    DieHardDBRepository dieHardDBRepository;

    @GetMapping("/{make}/{model}/{year}")
    public BatteryDetails getDieHardBatteriesByMakeModelYear(@PathVariable(name = "make") String make, @PathVariable(name = "model") String model, @PathVariable(name = "year") String year){
        log.info(make+model+year);
        return dieHardService.findDieHardBatteries(make+model+year);
    }

    @PostMapping
    public BatteryDetails setDieHardBatteriesByMakeModelYear(@RequestBody BatteryDetails dieHardBattery){

       return dieHardDBRepository.save(dieHardBattery);
    }
}