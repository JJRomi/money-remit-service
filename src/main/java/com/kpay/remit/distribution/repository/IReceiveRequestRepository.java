package com.kpay.remit.distribution.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpay.remit.model.ReceiveRequest;

public interface IReceiveRequestRepository extends JpaRepository<ReceiveRequest, Long> {
}
