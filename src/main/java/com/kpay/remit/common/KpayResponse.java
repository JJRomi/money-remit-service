package com.kpay.remit.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KpayResponse<T> {
	private String errorCode;
	private String errorMessage;
	private T Data;
}
