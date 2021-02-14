package com.umurcan.smarthost.bean;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomAllocationResponse {
	private long totalPremiumRoomEarn; //for the night
	private int premiumRoomOccupancy; 
	private long totalEconomyRoomEarn; //for the night
	private int economyRoomOccupancy; 
	
}
