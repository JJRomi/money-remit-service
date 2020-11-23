package com.kpay.remit.distribution.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpay.remit.model.Receive;

public interface IReceiveRepository extends JpaRepository<Receive, Long> {
}
