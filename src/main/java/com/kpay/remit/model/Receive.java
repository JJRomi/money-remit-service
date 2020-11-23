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
@Table(name = "money_receive")
@Getter
@Setter
public class Receive extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long distributionId;

	@Column
	private Long roomId;

	@Column
	private Long userId;

	@Column(nullable = false)
	private int amount;

	@Column(nullable = false)
	private String token;

}
