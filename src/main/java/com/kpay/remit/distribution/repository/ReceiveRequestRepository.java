package com.kpay.remit.distribution.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ReceiveRequestRepository extends QuerydslRepositorySupport {
	public ReceiveRequestRepository() {
		super(ReceiveRequestRepository.class);
	}
}
