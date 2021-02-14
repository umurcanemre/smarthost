package com.umurcan.smarthost.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.umurcan.smarthost.bean.RoomAllocationRequest;
import com.umurcan.smarthost.bean.RoomAllocationResponse;
import com.umurcan.smarthost.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Override
	public RoomAllocationResponse assignRoomsForCustomers(RoomAllocationRequest request) {
		//Sorting the customer budgets by desc order, so in a given moment all collections holding
		//customer budgets are by descending order.
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
					allocateToEconomyRoom(request, economyRooms, i);
				}
			}
		}

		return RoomAllocationResponse.builder().economyRoomOccupancy(economyRooms.size())
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
	private boolean allocateToPremiumRoomCondition(RoomAllocationRequest request, int customerPtr) {
		return request.getCustomerBudget().get(customerPtr) >= 100 || request.getEconomyRoomCount() == 0;
	}

	private void allocateToPremiumRoom(RoomAllocationRequest request, List<Integer> premiumRooms, int customerPtr) {
		if (premiumRoomsRoomVacant(request, premiumRooms)) {
			premiumRooms.add(request.getCustomerBudget().get(customerPtr));
		}
	}

	private void allocateToEconomyRoom(RoomAllocationRequest request, Queue<Integer> economyRooms, int customerPtr) {
		if (economyRoomVacant(request, economyRooms)) {
			economyRooms.add(request.getCustomerBudget().get(customerPtr));
		}
	}

	private boolean economyRoomVacant(RoomAllocationRequest request, Queue<Integer> economyRooms) {
		return economyRooms.size() < request.getEconomyRoomCount();
	}

	private boolean premiumRoomsRoomVacant(RoomAllocationRequest request, List<Integer> premiumRooms) {
		return premiumRooms.size() < request.getPremiumRoomCount();
	}

	private void pushHighestPayingEcoCustomerToPremiumRoom(List<Integer> premiumRooms, Queue<Integer> economyRooms) {
		// remove head of (one with greatest value) economy guests, push it to the end of premium rooms
		premiumRooms.add(economyRooms.remove());
	}
}
