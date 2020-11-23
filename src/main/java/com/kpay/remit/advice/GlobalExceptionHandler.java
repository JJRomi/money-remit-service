package com.kpay.remit.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kpay.remit.common.KpayResponse;
import com.kpay.remit.common.exception.DistributionException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(DistributionException.class)
	protected ResponseEntity<KpayResponse> handleDistributionMethodValidException(DistributionException e) {
		log.error("handleDistributionMethodValidException", e);

		final KpayResponse response = new KpayResponse();
		response.setErrorMessage(e.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
