package com.umurcan.smarthost.service;

import com.umurcan.smarthost.bean.RoomAllocationRequest;
import com.umurcan.smarthost.bean.RoomAllocationResponse;

public interface RoomService {
	RoomAllocationResponse assignRoomsForCustomers(RoomAllocationRequest request);
}
