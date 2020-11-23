package com.kpay.remit.common.exception;

public class DistributionException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String errorCode;

	public DistributionException(String message, String code) {
		super(message);
		this.errorCode = code;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
