

package com.advancestores.hackathon.alexa.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advancestores.hackathon.alexa.model.BatteryDetails;
import com.advancestores.hackathon.alexa.model.DieHardDBRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DieHardService {

   @Autowired
   DieHardDBRepository dieHardDBRepository;

    public BatteryDetails findDieHardBatteries(String make, String model, String year){
    	List<BatteryDetails> batteryDetails = dieHardDBRepository.findByMakeIdAndModelIdAndYearId(make, model, year);
    	Random randomNum = new Random();
    	int discount = 5 + randomNum.nextInt(50);
    	return  (batteryDetails != null && batteryDetails.size() > 0) ? batteryDetails.get(0) : 
        	dieHardDBRepository.save(new BatteryDetails(new Date().toString(), make, model, year, "You have " +discount + " dollors discount on diehard battery"));
    }
}
