package com.advancestores.hackathon.alexa.controller;

import com.advancestores.hackathon.alexa.model.CouponDetails;
import com.advancestores.hackathon.alexa.service.SpeedPerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class Speedperks {

    @Autowired
    SpeedPerkService speedPerkService;

    @GetMapping("/speedperks/{id}")
    public ResponseEntity<CouponDetails> getSpeedPerkDetails() {
        // System.out.println("getSpeedPerkDetails called");
        log.info("getSpeedPerkDetails called");

        CouponDetails couponDetails = speedPerkService.getCoupons();
        return new ResponseEntity<CouponDetails>(couponDetails, HttpStatus.OK);
    }
}
