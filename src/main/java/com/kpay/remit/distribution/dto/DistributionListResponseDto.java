package com.kpay.remit.distribution.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DistributionListResponseDto {
	private LocalDateTime distributionDateTime;
	private int distributionAmount;
	private int receiveAmount;
	private List<ReceiveListDto> receiveList;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public LocalDateTime getDistributionDateTime() {
		return distributionDateTime;
	}

}
