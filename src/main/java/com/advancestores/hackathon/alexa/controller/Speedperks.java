package com.advancestores.hackathon.alexa.controller;

import com.advancestores.hackathon.alexa.model.CouponDetails;
import com.advancestores.hackathon.alexa.service.SpeedPerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Speedperks {

    @Autowired
    SpeedPerkService speedPerkService;

    @GetMapping("/speedperks/{id}")
    public ResponseEntity<CouponDetails> getSpeedPerkDetails(){
        var couponDetails = speedPerkService.getCoupons();
        return new ResponseEntity<CouponDetails>(couponDetails, HttpStatus.OK);
    }
}
