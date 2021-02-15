package com.umurcan.smarthost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.umurcan.smarthost.bean.RoomAssignmentRequest;
import com.umurcan.smarthost.bean.RoomAssignmentResponse;

@SpringBootTest
public class RoomAssignmentServiceTests {
	@Autowired
	RoomAssignmentService roomService;
	
	List<Integer> customerBudgets = new ArrayList<>();
	
	@BeforeEach
	public void init() {
		customerBudgets.clear();
		customerBudgets.addAll(List.of(23, 45, 155, 374, 22, 99, 100, 101, 115, 209));
	}

	@Test
	void roomServiceTest_3economyRooms_3premium () {
		var request = new RoomAssignmentRequest(3,3,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 3, 167, 3, 738);
	}
	
	@Test
	void roomServiceTest_5economyRooms_7premium () {
		var request = new RoomAssignmentRequest(5,7,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 4, 189, 6, 1054);
	}
	
	@Test
	void roomServiceTest_7economyRooms_2premium () {
		var request = new RoomAssignmentRequest(7,2,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 4, 189, 2, 583);
	}
	
	@Test
	void roomServiceTest_1economyRooms_7premium () {
		var request = new RoomAssignmentRequest(1,7,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 1, 45, 7, 1153);
	}
	
	@Test
	void roomServiceTest_1economyRooms_0premium () {
		var request = new RoomAssignmentRequest(1,0,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 1, 99, 0, 0);
	}
	
	@Test
	void roomServiceTest_0economyRooms_1premium () {
		var request = new RoomAssignmentRequest(0,1,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 0, 0, 1, 374);
	}
	
	@Test
	void roomServiceTest_10economyRooms_10premium () {
		var request = new RoomAssignmentRequest(10,10,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 4, 189, 6, 1054);
	}
	
	@Test
	void roomServiceTest_1economyRooms_9premium () {
		var request = new RoomAssignmentRequest(1,9,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 1, 22, 9, 1221);
	}
	
	@Test
	void roomServiceTest_10premium () {
		var request = new RoomAssignmentRequest(0,10,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 0, 0, 10, 1243);
	}
	
	@Test
	void roomServiceTest_10economy () {
		var request = new RoomAssignmentRequest(10,0,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 4, 189, 0, 0);
	}
	
	@Test
	void roomServiceTest_noRoom () {
		var request = new RoomAssignmentRequest(0,0,customerBudgets);
		
		var response = roomService.assignRoomsForCustomers(request);
		
		assertRoomAllocationResponse(response, 0, 0, 0, 0);
	}
	
	private void assertRoomAllocationResponse(RoomAssignmentResponse response, int economyRoomOccupancy, 
			long economyRoomEarn, int premiumRoomOccupancy, long premiumRoomEarn) {
		
		assertNotNull(response);
		assertEquals(economyRoomOccupancy,response.getEconomyRoomOccupancy());
		assertEquals(economyRoomEarn     ,response.getTotalEconomyRoomEarn());
		assertEquals(premiumRoomOccupancy,response.getPremiumRoomOccupancy());
		assertEquals(premiumRoomEarn     ,response.getTotalPremiumRoomEarn());
	}
}
