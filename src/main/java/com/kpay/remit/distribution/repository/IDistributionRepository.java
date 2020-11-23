package com.kpay.remit.distribution.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpay.remit.model.Distribution;

public interface IDistributionRepository extends JpaRepository<Distribution, Long> {

}
