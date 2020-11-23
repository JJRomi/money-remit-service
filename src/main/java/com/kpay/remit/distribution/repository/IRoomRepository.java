package com.kpay.remit.distribution.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpay.remit.model.RoomUser;

public interface IRoomRepository extends JpaRepository<RoomUser, Long> {

}
