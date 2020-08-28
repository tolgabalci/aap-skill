package com.advancestores.hackathon.alexa.controller;

import com.advancestores.hackathon.alexa.model.BatteryDetails;

import com.advancestores.hackathon.alexa.model.DieHardDBRepository;
import com.advancestores.hackathon.alexa.service.DieHardService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public BatteryDetails getDieHardBatteriesByMakeModelYear(@PathVariable(name = "make") String make){
        log.info(make);
        List<BatteryDetails> batteries = dieHardService.findDieHardBatteries(make);
        BatteryDetails bd = new BatteryDetails();
        bd.setOffer("You have 122 dollors discount on diehard battery");
        return batteries.isEmpty() ? bd : batteries.get(0);

    }

    @PostMapping
    public BatteryDetails setDieHardBatteriesByMakeModelYear(@RequestBody BatteryDetails dieHardBattery){

       return dieHardDBRepository.save(dieHardBattery);
    }
}