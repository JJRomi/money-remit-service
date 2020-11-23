package com.kpay.remit.distribution.service;

public interface TokenService {
	public String createToken(Long roomId);

	public boolean checkByTokenInRoom(String token, Long roomId);

	public boolean checkByTokenTime(String token, Long roomId);

	public boolean checkByTokenUser(String token, Long roomId);

	public boolean checkByTokenUserAtDatetime(String token, Long roomId, Long userId);
}
