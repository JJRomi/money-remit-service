package com.kpay.remit.distribution.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpay.remit.distribution.dto.DistributionSaveRequestDto;
import com.kpay.remit.distribution.dto.DistributionSaveResponseDto;
import com.kpay.remit.distribution.repository.IDistributionRepository;
import com.kpay.remit.distribution.repository.IReceiveRepository;
import com.kpay.remit.model.Distribution;
import com.kpay.remit.model.Receive;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DistributionService {
	@Autowired
	private IDistributionRepository IDistributionRepository;

	@Autowired
	private IReceiveRepository IReceiveRepository;

	@Autowired
	private RoomService roomService;

	@Autowired
	private TokenServiceImpl tokenServiceImpl;

	public boolean checkDistribution(Long userId, Long roomId) {
		if(!this.roomService.findByUserInRoom(userId, roomId)) {
			return false;
		}
		return true;
	}

	public DistributionSaveResponseDto distributionSave(Long userId, Long roomId, DistributionSaveRequestDto requestDto) {
		if(!this.checkDistribution(userId, roomId)) {
			return null;
		}
		String token = tokenServiceImpl.createToken(roomId);
		Distribution distribution = distributionSave(userId, roomId, requestDto, token);
		divideMoney(distribution);
		DistributionSaveResponseDto responseDto = new DistributionSaveResponseDto();
		responseDto.setToken(distribution.getToken());

		return responseDto;
	}

	public Distribution distributionSave(Long userId, Long roomId, DistributionSaveRequestDto requestDto, String token) {
		Distribution distribution = new Distribution();
		distribution.setUserId(userId);
		distribution.setRoomId(roomId);
		distribution.setAmount(requestDto.getAmount());
		distribution.setPersonnel(requestDto.getPersonnel());
		distribution.setToken(token);

		return IDistributionRepository.save(distribution);
	}


	public void divideMoney(Distribution distribution) {
		int amount = distribution.getAmount();
		int personnel = distribution.getPersonnel();
		int amountByOne = amount/personnel;
		int remain = amount%personnel;

		List<Receive> receiveList = new ArrayList<>();

		for(int i = 0; i < personnel; i++) {
			if(i == personnel-1) {
				amountByOne += remain;
			}
			Receive receive = new Receive();
			receive.setDistributionId(distribution.getId());
			receive.setToken(distribution.getToken());
			receive.setRoomId(distribution.getRoomId());

			receive.setAmount(amountByOne);
			receiveList.add(receive);
		}
		IReceiveRepository.saveAll(receiveList);
	}

}
