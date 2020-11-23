package com.kpay.remit.distribution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;

import com.kpay.remit.distribution.repository.DistributeRepository;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private DistributeRepository distributeRepository;

	@Override
	public String createToken(Long roomId) {
		String result;
		String token = RandomString.make(3);
		if (checkByTokenInRoom(token, roomId)) {
			result = token;
		} else {
			result = createToken(roomId);
		}
		return result;
	}

	@Override
	public boolean checkByTokenInRoom(String token, Long roomId) {
		Long countTokenInRoom = distributeRepository.countByTokenInRoom(token, roomId);
		if(countTokenInRoom == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkByTokenTime(String token, Long roomId) {
		return false;
	}

	@Override
	public boolean checkByTokenUser(String token, Long roomId) {
		return false;
	}

	@Override
	public boolean checkByTokenUserAtDatetime(String token, Long roomId, Long userId) {
		return false;
	}
}
