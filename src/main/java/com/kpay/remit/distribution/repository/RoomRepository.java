package com.kpay.remit.distribution.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.kpay.remit.model.QRoomUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class RoomRepository extends QuerydslRepositorySupport {

	public RoomRepository() {
		super(RoomRepository.class);
	}

	public Long countByUserInRoom(Long userId, Long roomId) {
		log.info("count by user in room");
		QRoomUser qRoomUser = QRoomUser.roomUser;
		return from(qRoomUser)
			.where(qRoomUser.userId.eq(userId))
			.where(qRoomUser.roomId.eq(roomId))
			.fetchCount();
	}
}
