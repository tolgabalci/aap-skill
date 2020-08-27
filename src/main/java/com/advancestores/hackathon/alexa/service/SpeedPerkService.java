package com.advancestores.hackathon.alexa.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.advancestores.hackathon.alexa.model.CouponDetails;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SpeedPerkService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> getCoupons(String id) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://apiqa.test.advanceauto.cloud/api/v2/experience/loyalty/coupons")
                .queryParam("accountNumber", id);
       
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-correlation-id", "some-uuid");
        headers.set("client_id", "6f36fbeadad84b18b09b0cf7f974f741");
        headers.set("client_secret", "Ea37a2ED33BC4eFAb3f349A9993e6FaB");
        headers.set("Host", "experience-client-loyalty-api-v2.test.advanceauto.cloud");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        log.info("response ->" +response);
        return response;
    }
    
    public CouponDetails getCoupons(){
        return new CouponDetails(45,9,"9194538872","test207@example.com");
    }


}
