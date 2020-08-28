

package com.advancestores.hackathon.alexa.service;

import com.advancestores.hackathon.alexa.model.BatteryDetails;
import com.advancestores.hackathon.alexa.model.DieHardDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DieHardService {

   @Autowired
   DieHardDBRepository dieHardDBRepository;

    public BatteryDetails findDieHardBatteries(String queryId){
        return dieHardDBRepository.findByMakeId(queryId);
    }
}
