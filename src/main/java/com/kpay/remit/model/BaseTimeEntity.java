package com.kpay.remit.model;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@JsonFormat(pattern = "yyyy-MM-dd")
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
