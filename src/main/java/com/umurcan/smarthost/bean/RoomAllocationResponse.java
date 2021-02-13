package com.umurcan.smarthost.bean;

import lombok.Data;

@Data
public class RoomAllocationResponse {
	private long totalPremiumRoomEarn; //for the night
	private int premiumRoomOccupancy; 
	private long totalEconomyRoomEarn; //for the night
	private int economyRoomOccupancy; 
	
}
