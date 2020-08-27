package com.advancestores.hackathon.alexa.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CouponDetails {

    Integer couponTotals;
    Integer coupons;
    String phoneNumber;
    String emailId;

}
