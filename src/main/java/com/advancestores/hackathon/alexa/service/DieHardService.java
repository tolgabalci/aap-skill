

package com.advancestores.hackathon.alexa.service;

import java.util.List;

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
    	log.info("BatteryDetails response ->" +batteryDetails);
        return  (batteryDetails != null && batteryDetails.size() > 0) ? batteryDetails.get(0) : null;
    }
}
