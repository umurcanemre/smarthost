package com.umurcan.smarthost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.umurcan.smarthost.bean.RoomAssignmentRequest;
import com.umurcan.smarthost.bean.RoomAssignmentResponse;
import com.umurcan.smarthost.service.RoomAssignmentService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class RoomAssignmentController {

	@Autowired
	private RoomAssignmentService assignmentService;

	@Operation(summary = "Allocate given customers to given room counts", description = "", tags = { "room" })
	@PostMapping("/api/room/assign")
	@ResponseBody
	public RoomAssignmentResponse RoomAssignmentResponse(@RequestBody RoomAssignmentRequest request) {
		validateRoomAssignmentRequest(request);
		return assignmentService.assignRoomsForCustomers(request);
	}

	private void validateRoomAssignmentRequest(RoomAssignmentRequest request) {
		if (request == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body can't be null");
		}
		if (CollectionUtils.isEmpty(request.getCustomerBudget())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer budgets can't be null or empty");
		}
		if (request.getEconomyRoomCount() < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room count can't be smaller than 0");
		}
		if (request.getPremiumRoomCount() < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room count can't be smaller than 0");
		}
	}
}
