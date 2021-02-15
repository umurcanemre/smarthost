package com.umurcan.smarthost.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.umurcan.smarthost.bean.RoomAssignmentRequest;
import com.umurcan.smarthost.bean.RoomAssignmentResponse;
import com.umurcan.smarthost.service.RoomAssignmentService;

@Service
public class RoomAssignmentServiceImpl implements RoomAssignmentService {
	
	@Value("${app.value.roomThreshold}")
	private int roomValueThreshold;
	
	@Override
	public RoomAssignmentResponse assignRoomsForCustomers(RoomAssignmentRequest request) {
		//Sorting the customer budgets by desc order, so in a given moment all collections holding
		//customer budgets are in descending order.
		Collections.sort(request.getCustomerBudget(), Collections.reverseOrder());

		Queue<Integer> economyRooms = new LinkedList<>();
		List<Integer> premiumRooms = new ArrayList<>(request.getPremiumRoomCount());

		for (int i = 0; i < request.getCustomerBudget().size(); i++) {
			if (allocateToPremiumRoomCondition(request, i)) {
				allocateToPremiumRoom(request, premiumRooms, i);
			} else {
				boolean premiumRoomVacant = premiumRoomsRoomVacant(request, premiumRooms);
				boolean economyRoomVacant = economyRoomVacant(request, economyRooms);

				if (economyRoomVacant) {
					allocateToEconomyRoom(request, economyRooms, i);
				} else if (premiumRoomVacant) {
					pushHighestPayingEcoCustomerToPremiumRoom(premiumRooms, economyRooms);
					//after pushing 1 customer from eco to premium, now there is room for 1 customer in eco
					allocateToEconomyRoom(request, economyRooms, i);
				}
			}
		}

		return RoomAssignmentResponse.builder().economyRoomOccupancy(economyRooms.size())
				.totalEconomyRoomEarn(economyRooms.stream().collect(Collectors.summingLong(Integer::longValue)))
				.premiumRoomOccupancy(premiumRooms.size())
				.totalPremiumRoomEarn(premiumRooms.stream().collect(Collectors.summingLong(Integer::longValue)))
				.build();
	}

	/**
	 * Either customer is paying above premium room threshold value or there is no
	 * economy room at hotel at all
	 * 
	 * @param request
	 * @param customerPtr
	 * @return whether customer should be allocated to a premium room or not
	 */
	private boolean allocateToPremiumRoomCondition(RoomAssignmentRequest request, int customerPtr) {
		return request.getCustomerBudget().get(customerPtr) >= roomValueThreshold || request.getEconomyRoomCount() == 0;
	}

	private void allocateToPremiumRoom(RoomAssignmentRequest request, List<Integer> premiumRooms, int customerPtr) {
		if (premiumRoomsRoomVacant(request, premiumRooms)) {
			premiumRooms.add(request.getCustomerBudget().get(customerPtr));
		}
	}

	private void allocateToEconomyRoom(RoomAssignmentRequest request, Queue<Integer> economyRooms, int customerPtr) {
		if (economyRoomVacant(request, economyRooms)) {
			economyRooms.add(request.getCustomerBudget().get(customerPtr));
		}
	}

	private boolean economyRoomVacant(RoomAssignmentRequest request, Queue<Integer> economyRooms) {
		return economyRooms.size() < request.getEconomyRoomCount();
	}

	private boolean premiumRoomsRoomVacant(RoomAssignmentRequest request, List<Integer> premiumRooms) {
		return premiumRooms.size() < request.getPremiumRoomCount();
	}

	private void pushHighestPayingEcoCustomerToPremiumRoom(List<Integer> premiumRooms, Queue<Integer> economyRooms) {
		// remove head of (one with greatest value) economy guests, push it to the end of premium rooms
		premiumRooms.add(economyRooms.remove());
	}
}
