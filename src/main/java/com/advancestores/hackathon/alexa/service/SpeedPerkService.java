package com.advancestores.hackathon.alexa.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    
    @Autowired
    private Environment env;

    public ResponseEntity<String> getCoupons(String accountNumber) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("speedperks.baseurl")+"/coupons")
                .queryParam("accountNumber", accountNumber);
       
        HttpEntity entity = getHttpEntity();
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHttpEntity(), String.class);
        log.info("Coupons Response by accountNumer ->" +response);
        return response;
    }
    
    public CouponDetails getCoupons(){
        return new CouponDetails(45,9,"9194538872","test207@example.com");
    }


    private HttpEntity getHttpEntity() {
    	HttpHeaders headers = new HttpHeaders();
        headers.set("x-correlation-id", "some-uuid");
        headers.set("client_id", "6f36fbeadad84b18b09b0cf7f974f741");
        headers.set("client_secret", "Ea37a2ED33BC4eFAb3f349A9993e6FaB");
        headers.set("Host", "experience-client-loyalty-api-v2.test.advanceauto.cloud");
        return new HttpEntity(headers);
    }

	public ResponseEntity<String> getMembersByPhone(String phone) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("speedperks.baseurl")+"/members")
                .queryParam("phone", phone);
       
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHttpEntity(), String.class);
        log.info("members Response by phone ->" + response);
        return response;
	}
}
