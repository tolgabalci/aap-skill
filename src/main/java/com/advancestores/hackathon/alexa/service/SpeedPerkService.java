package com.advancestores.hackathon.alexa.service;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.advancestores.hackathon.alexa.model.AapDBRepository;
import com.advancestores.hackathon.alexa.model.AlexaUser;
import com.advancestores.hackathon.alexa.model.CouponDetails;
import com.advancestores.hackathon.alexa.model.SpeedPerkDetails;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SpeedPerkService {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    AapDBRepository aapDBRepository;
    
    @Autowired
    private Environment env;

    public ResponseEntity<String> getCoupons(String accountNumber)  {
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

	public ResponseEntity<String> getMembers(String phone, String accountNumber) {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		if (phone != null)
			queryParams.add("phone", phone);
		if (accountNumber != null)
			queryParams.add("accountNumber", accountNumber);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("speedperks.baseurl")+"/members")
                .queryParams(queryParams);
       
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHttpEntity(), String.class);
        log.info("Members Response by phone ->" + response);
        return response;
	}
	
	public ResponseEntity<String> getMemberByAccount(String accountNumber) {
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("accountNumber", accountNumber);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				env.getProperty("speedperks.baseurl")+"/members/{accountNumber}");
       
        ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.GET, getHttpEntity(), String.class);
        log.info("Members Points Summary Response by accountNumber ->" + response);
        return response;
	}
	
	

	public SpeedPerkDetails getSpeedPerksByUser(String alexaUserId) {
		SpeedPerkDetails details = null;
		final AlexaUser user = aapDBRepository.findByAlexaUserId(alexaUserId);
        if (user != null) {
            log.info("found user with phone number: " + user.getSpeedPerksPhone());            
        
		//Retrieve the phone number from DB by userId 
		String phone = user.getSpeedPerksPhone();
		ResponseEntity<String> response = getMembers(phone, null);
		JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();
		details = new SpeedPerkDetails();
		JsonArray arr = jsonObject.getAsJsonArray("SearchMemberResult");
		JsonObject searchMember = arr.get(0).getAsJsonObject();
		details.setName(searchMember.get("firstName").getAsString() + " " + searchMember.get("lastName").getAsString());

		String accounNumber = searchMember.get("accountNumber").getAsString();
		ResponseEntity<String> responseByAccount = getMemberByAccount(accounNumber); 
		JsonObject jsonObject1 = new JsonParser().parse(responseByAccount.getBody()).getAsJsonObject().getAsJsonObject("MemberFullProfile");
		JsonObject tier = jsonObject1.getAsJsonObject("tier");
		details.setCurrentLevel(tier.get("name").getAsString());
		JsonObject pointSummary = jsonObject1.getAsJsonObject("pointSummary");
		JsonArray points = pointSummary.getAsJsonArray("points");
		if(points!= null && points.size() > 0 ) {
			details.setCurrentPoints(points.get(0).getAsJsonObject().get("pointsAvailable").getAsBigDecimal()); 
		}
		
		JsonArray customAttributes = jsonObject1.getAsJsonArray("customAttributes");
		if(customAttributes != null && customAttributes.size() > 0) {
			for(int i = 0; i< customAttributes.size(); i++ ) {
				if(customAttributes.get(0).getAsJsonObject().get("key").getAsString().equalsIgnoreCase("pointsToNextReward")) {
					details.setPointsToNextReward(customAttributes.get(0).getAsJsonObject().get("param").getAsString());
					break;
				}
			}
		}
		
		
		ResponseEntity<String> couponResponse = getCoupons(accounNumber);
		JsonObject couponObject = new JsonParser().parse(couponResponse.getBody()).getAsJsonObject();
		JsonArray memberCoupons = couponObject.getAsJsonArray("MemberCoupons");
		details.setTotalCouponsAvailable(memberCoupons.size());
		BigDecimal totalCouponValue = BigDecimal.ZERO;
		if(memberCoupons != null && memberCoupons.size()>0) {
			for(int i = 0; i<memberCoupons.size(); i++) {
				totalCouponValue = memberCoupons.get(i).getAsJsonObject().get("couponValue").getAsBigDecimal();
			}
			details.setTotalCouponValue(totalCouponValue);
		}
		
		log.info(details);
        }
		return details;
	}
}
