package com.advancestores.hackathon.alexa.service;

import com.advancestores.hackathon.alexa.model.CouponDetails;
import com.advancestores.hackathon.alexa.model.MemberCoupons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class SpeedPerkService {

    @Autowired
    RestTemplate restTemplate;

    public void getCoupons(String id){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://apiqa.test.advanceauto.cloud/api/v2/experience/loyalty/coupons")
                .queryParam("accountNumber", "7770131000143681");
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-correlation-id", "ididi");
        headers.set("client_id", "6f36fbeadad84b18b09b0cf7f974f74");
        headers.set("client_secret", "Ea37a2ED33BC4eFAb3f349A9993e6FaB");
        headers.set("Host", "experience-client-loyalty-api-v2.test.advanceauto.cloud");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
    }

    public CouponDetails getCoupons(){
        return new CouponDetails(45,9,"9194538872","test207@example.com");
    }


}
