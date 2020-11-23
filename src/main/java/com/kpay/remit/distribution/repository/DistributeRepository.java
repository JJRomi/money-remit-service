package com.kpay.remit.distribution.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.kpay.remit.model.Distribution;
import com.kpay.remit.model.QDistribution;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DistributeRepository extends QuerydslRepositorySupport {

	public DistributeRepository() {
		super(DistributeRepository.class);
	}


	public Long countByTokenInRoom(String token, Long roomId) {
		log.info("find By Token In room");

		QDistribution qDistribution = QDistribution.distribution;

		// TODO:: add current time diff (10 min)
		return from(qDistribution)
			.where(qDistribution.token.eq(token))
			.where(qDistribution.roomId.eq(roomId))
			.fetchCount();
	}

	public Long findByTokenInRoom(String token, Long roomId) {
		log.info("find by Token in room");

		QDistribution qDistribution = QDistribution.distribution;

		return from(qDistribution)
			.where(qDistribution.token.eq(token))
			.where(qDistribution.roomId.eq(roomId))
			.select(qDistribution.id)
			.fetchOne();
	}

	public Long findUserIdByTokenUser(String token, Long roomId) {
		log.info("find user id by token user");

		QDistribution qDistribution = QDistribution.distribution;

		return from(qDistribution)
			.where(qDistribution.token.eq(token))
			.where(qDistribution.roomId.eq(roomId))
			.select(qDistribution.userId)
			.fetchOne();

	}

	public Distribution findDistributionByTokenInRoom(String token, Long roomId) {
		log.info("find distribution info by token in room");

		QDistribution qDistribution = QDistribution.distribution;

		return from(qDistribution)
			.where(qDistribution.token.eq(token))
			.where(qDistribution.roomId.eq(roomId))
			.fetchOne();
	}
}
