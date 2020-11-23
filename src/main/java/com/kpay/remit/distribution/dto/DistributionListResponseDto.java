package com.kpay.remit.distribution.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kpay.remit.model.Receive;

import lombok.Data;

@Data
public class DistributionListResponseDto {
	private LocalDateTime distributionDate;
	private int amount;
	private List<Receive> receiveList;

	@JsonFormat(pattern = "yyyy-MM-dd")
	public LocalDateTime getDistributionDate() {
		return distributionDate;
	}

}
