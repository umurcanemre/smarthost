package com.umurcan.smarthost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.umurcan.smarthost.bean.RoomAllocationRequest;
import com.umurcan.smarthost.bean.RoomAllocationResponse;

@SpringBootTest
public class RoomServiceTests {
	@Autowired
	RoomService roomService;
	
	List<Integer> customerBudgets = Collections 
            .unmodifiableList(	List.of(23, 45, 155, 374, 22, 99, 100, 101, 115, 209));

	@Test
	public void roomServiceTest_3premium_3economyRooms () {
		var request = new RoomAllocationRequest(3,3,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 3, 167, 3, 738);
	}
	
	@Test
	public void roomServiceTest_7premium_5economyRooms () {
		var request = new RoomAllocationRequest(5,7,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 4, 189, 6, 1054);
	}
	
	@Test
	public void roomServiceTest_2premium_7economyRooms () {
		var request = new RoomAllocationRequest(7,2,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 4, 189, 2, 583);
	}
	
	@Test
	public void roomServiceTest_7premium_1economyRooms () {
		var request = new RoomAllocationRequest(1,7,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 1, 45, 7, 1153);
	}
	
	private void assertRoomAllocationResponse(RoomAllocationResponse response, int economyRoomOccupancy, 
			long economyRoomEarn, int premiumRoomOccupancy, long premiumRoomEarn) {
		
		assertNotNull(response);
		assertEquals(response.getEconomyRoomOccupancy(), economyRoomOccupancy);
		assertEquals(response.getTotalEconomyRoomEarn(), economyRoomEarn);
		assertEquals(response.getPremiumRoomOccupancy(), premiumRoomOccupancy);
		assertEquals(response.getTotalPremiumRoomEarn(), premiumRoomEarn);
	}
}
