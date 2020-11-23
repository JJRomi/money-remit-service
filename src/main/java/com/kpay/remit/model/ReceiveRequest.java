package com.kpay.remit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "money_receive_request")
@Getter
@Setter
public class ReceiveRequest extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long distributionId;

	@Column(nullable = false)
	private Long roomId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String token;

}
