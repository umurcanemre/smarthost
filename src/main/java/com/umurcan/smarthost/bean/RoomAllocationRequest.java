package com.umurcan.smarthost.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomAllocationRequest {

	private int economyRoomCount;
	private int premiumRoomCount;
	private List<Integer> customerBudget;
}
