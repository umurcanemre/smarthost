package com.umurcan.smarthost.service;

import com.umurcan.smarthost.bean.RoomAssignmentRequest;
import com.umurcan.smarthost.bean.RoomAssignmentResponse;

public interface RoomAssignmentService {
	RoomAssignmentResponse assignRoomsForCustomers(RoomAssignmentRequest request);
}
