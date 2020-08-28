

package com.advancestores.hackathon.alexa.service;

import com.advancestores.hackathon.alexa.model.BatteryDetails;
import com.advancestores.hackathon.alexa.model.DieHardDBRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class DieHardService {

   @Autowired
   DieHardDBRepository dieHardDBRepository;

    public List<BatteryDetails> findDieHardBatteries(String queryId){
        log.info(queryId);
        return dieHardDBRepository.findByMakeId(queryId);
    }
}
