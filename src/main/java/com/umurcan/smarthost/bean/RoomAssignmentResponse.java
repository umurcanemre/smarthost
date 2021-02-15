package com.umurcan.smarthost.bean;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomAssignmentResponse {
	private long totalPremiumRoomEarn; //for the night
	private int premiumRoomOccupancy; 
	private long totalEconomyRoomEarn; //for the night
	private int economyRoomOccupancy; 
	
}
