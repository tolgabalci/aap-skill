package com.advancestores.hackathon.alexa.service;

import com.advancestores.hackathon.alexa.model.Battery;
import com.advancestores.hackathon.alexa.model.DieHardBattery;
import com.advancestores.hackathon.alexa.model.DieHardDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DieHardService {

    @Autowired
    DieHardDBRepository dieHardDBRepository;

    public DieHardBattery findDieHardBatteries(String queryId){
        return dieHardDBRepository.findByBatteryId(queryId);
    }
}
