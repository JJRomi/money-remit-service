package com.kpay.remit.distribution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpay.remit.distribution.repository.RoomRepository;
import com.kpay.remit.distribution.repository.IRoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomService {

	@Autowired
	private IRoomRepository IRoomRepository;

	@Autowired
	RoomRepository roomRepository;

	public boolean findByUserInRoom(Long userId, Long roomId) {
		Long countByUserInRoom = this.roomRepository.countByUserInRoom(userId, roomId);
		if(countByUserInRoom == 0) {
			return false;
		}
		return true;
	}
}
