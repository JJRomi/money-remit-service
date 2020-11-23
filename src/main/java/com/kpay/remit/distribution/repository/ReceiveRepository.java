package com.kpay.remit.distribution.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.kpay.remit.distribution.dto.ReceiveListDto;
import com.kpay.remit.model.QReceive;
import com.kpay.remit.model.Receive;
import com.querydsl.core.types.Projections;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ReceiveRepository extends QuerydslRepositorySupport {

	public ReceiveRepository() {
		super(ReceiveRepository.class);
	}

	public Long findAlreadyReceiveByTokenInRoom(String token, Long distributionId, Long userId) {
		log.info("already receive user find by token in room");
		QReceive qReceive = QReceive.receive;

		return from(qReceive)
			.where(qReceive.token.eq(token))
			.where(qReceive.distributionId.eq(distributionId))
			.where(qReceive.userId.eq(userId))
			.fetchCount();
	}

	public List<Receive> findRemainAmountByToken(String token, Long distributionId) {
		log.info("find amount by token");
		QReceive qReceive = QReceive.receive;

		return from(qReceive)
			.where(qReceive.token.eq(token))
			.where(qReceive.distributionId.eq(distributionId))
			.where(qReceive.userId.isNull())
			.fetch();
	}

	public List<ReceiveListDto> findReceiveListByDistribution(String token, Long distributionId) {
		log.info("find receive list by distribution id");
		QReceive qReceive = QReceive.receive;

		return from(qReceive)
			.where(qReceive.token.eq(token))
			.where(qReceive.distributionId.eq(distributionId))
			.where(qReceive.userId.isNotNull())
			.select(Projections.fields(ReceiveListDto.class, qReceive.amount, qReceive.userId))
			.fetch();
	}

}
