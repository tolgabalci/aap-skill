package com.advancestores.hackathon.alexa.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SpeedPerkDetails {
	
	private String name;
	private BigDecimal currentPoints;
	private String pointsToNextReward;
	private int totalCouponsAvailable;
	private BigDecimal totalCouponValue;
	private String currentLevel;
	
	
}
