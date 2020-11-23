package com.kpay.remit.distribution.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpay.remit.common.exception.DistributionException;
import com.kpay.remit.distribution.dto.DistributionListResponseDto;
import com.kpay.remit.distribution.dto.DistributionSaveRequestDto;
import com.kpay.remit.distribution.dto.DistributionSaveResponseDto;
import com.kpay.remit.distribution.dto.ReceiveSaveRequestDto;
import com.kpay.remit.distribution.dto.ReceiveSaveResponseDto;
import com.kpay.remit.distribution.repository.DistributeRepository;
import com.kpay.remit.distribution.repository.IDistributionRepository;
import com.kpay.remit.distribution.repository.IReceiveRepository;
import com.kpay.remit.distribution.repository.IReceiveRequestRepository;
import com.kpay.remit.distribution.repository.ReceiveRepository;
import com.kpay.remit.distribution.repository.ReceiveRequestRepository;
import com.kpay.remit.model.Distribution;
import com.kpay.remit.model.Receive;
import com.kpay.remit.model.ReceiveRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DistributionService {
	@Autowired
	private IDistributionRepository IDistributionRepository;

	@Autowired
	DistributeRepository distributeRepository;

	@Autowired
	private IReceiveRepository IReceiveRepository;

	@Autowired
	ReceiveRepository receiveRepository;

	@Autowired
	ReceiveRequestRepository receiveRequestRepository;

	@Autowired
	private IReceiveRequestRepository IReceiveRequestRepository;

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

	public ReceiveSaveResponseDto receiveSave(Long userId, Long roomId, ReceiveSaveRequestDto requestDto) {
		String token = requestDto.getToken();
		Long distributionId = this.getDistributionId(token, roomId);
		this.receiveRequestSave(distributionId, roomId, userId, requestDto);
		if(tokenServiceImpl.checkByTokenInRoom(token, roomId)) {
			throw new DistributionException("유효하지 않은 토큰입니다.", "T001");
		}
		if(!this.roomService.findByUserInRoom(userId, roomId)) {
			throw new DistributionException("해당 방 참여자만 받을 수 있습니다.", "R001");
		}
		if(tokenServiceImpl.checkByTokenUser(token, roomId, userId)) {
			throw new DistributionException("자신이 뿌린 머니는 받을 수 없습니다.", "M001");
		}
		if(findAlreadyReceiveByTokenInRoom(token, distributionId, userId) > 0) {
			throw new DistributionException("이미 받으셨습니다.", "M002");
		}
		List<Receive> receiveList = this.receiveRepository.findRemainAmountByToken(token, distributionId);
		int idx = (int)(Math.random()*receiveList.size());
		Receive receive = receiveList.get(idx);
		receive.setUserId(userId);
		int amount = receiveUpdate(receive);
		ReceiveSaveResponseDto responseDto = new ReceiveSaveResponseDto();
		responseDto.setAmount(amount);

		return responseDto;
	}

	public Long findAlreadyReceiveByTokenInRoom(String token, Long distributionId, Long userId) {
		return receiveRepository.findAlreadyReceiveByTokenInRoom(token, distributionId, userId);
	}

	public Long getDistributionId(String token, Long roomId) {
		Long distributionId = distributeRepository.findByTokenInRoom(token, roomId).longValue();
		return distributionId;
	}

	public ReceiveRequest receiveRequestSave(Long distributionId, Long roomId, Long userId, ReceiveSaveRequestDto requestDto) {
		ReceiveRequest receiveRequest = new ReceiveRequest();
		receiveRequest.setDistributionId(distributionId);
		receiveRequest.setRoomId(roomId);
		receiveRequest.setUserId(userId);
		receiveRequest.setToken(requestDto.getToken());

		return IReceiveRequestRepository.save(receiveRequest);
	}

	public int receiveUpdate(Receive receive) {
		IReceiveRepository.save(receive);

		return receive.getAmount();
	}

	public DistributionListResponseDto distributionList(Long userId, Long roomId, String token) {
		if(!tokenServiceImpl.checkByTokenUserAtDatetime(token, roomId, userId)) {
			throw new DistributionException("유효하지 않은 토큰입니다.", "T001");
		}
		DistributionListResponseDto responseDto = this.getDistributionList(token, roomId);

		return responseDto;
	}

	public DistributionListResponseDto getDistributionList(String token, Long roomId) {
		Distribution distribution = this.distributeRepository.findDistributionByTokenInRoom(token, roomId);
		if(distribution.getId() == null){
			return null;
		}

		if(distribution.getCreatedAt() == null) {
			return null;
		}

		List<Receive> receiveList = this.receiveRepository.findReceiveListByDistribution(token, distribution.getId());
		DistributionListResponseDto responseDto = new DistributionListResponseDto();
		responseDto.setDistributionDateTime(distribution.getCreatedAt());
		responseDto.setAmount(distribution.getAmount());
		responseDto.setReceiveList(receiveList);

		return responseDto;
	}
}
