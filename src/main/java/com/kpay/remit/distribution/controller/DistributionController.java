package com.kpay.remit.distribution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kpay.remit.distribution.dto.DistributionListRequestDto;
import com.kpay.remit.distribution.dto.DistributionListResponseDto;
import com.kpay.remit.distribution.dto.DistributionSaveRequestDto;
import com.kpay.remit.distribution.dto.DistributionSaveResponseDto;
import com.kpay.remit.distribution.dto.ReceiveSaveRequestDto;
import com.kpay.remit.distribution.dto.ReceiveSaveResponseDto;
import com.kpay.remit.distribution.service.DistributionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DistributionController {

	@Autowired
	private DistributionService distributionService;

	@PostMapping(value = "/distribution", produces = "application/json")
	public DistributionSaveResponseDto distributionSave(@RequestHeader("X-USER-ID") Long userId,
		@RequestHeader("X-ROOM-ID") Long roomId,
		@RequestBody DistributionSaveRequestDto requestDto) {
		return distributionService.distributionSave(userId, roomId, requestDto);
	}

	@PostMapping(value = "/receive", produces = "application/json")
	public ResponseEntity<ReceiveSaveResponseDto> receiveSave(@RequestHeader("X-USER-ID") Long userId,
		@RequestHeader("X-ROOM-ID") Long roomId,
		@RequestBody ReceiveSaveRequestDto requestDto) {
		ReceiveSaveResponseDto responseDto = distributionService.receiveSave(userId, roomId, requestDto);
		return new ResponseEntity<ReceiveSaveResponseDto>(responseDto, HttpStatus.OK);
	}

	@GetMapping(value = "/distribution")
	public DistributionListResponseDto distributionList(@RequestHeader("X-USER-ID") Long userId,
		@RequestHeader("X-ROOM-ID") Long roomId,
		@RequestHeader("token") String token) {
		return distributionService.distributionList(userId, roomId, token);
	}
}

