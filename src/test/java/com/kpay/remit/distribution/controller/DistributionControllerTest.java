package com.kpay.remit.distribution.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpay.remit.distribution.dto.DistributionSaveRequestDto;
import com.kpay.remit.distribution.dto.ReceiveSaveRequestDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class DistributionControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("뿌리기 요청 API: 뿌리기 정보 및 받기 기본 데이 저장 테스트")
	public void distributionSave() throws Exception {
		Long roomId = Long.valueOf(1);
		Long userId = Long.valueOf(1);
		String content = this.뿌리기요청(50000, 2);

		this.mockMvc.perform(
			post("/api/v1/distribution")
				.header("X-ROOM-ID", roomId)
				.header("X-USER-ID", userId)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token").isNotEmpty())
			.andDo(print());
	}

	private String 뿌리기요청(int amount, int personnel) throws JsonProcessingException {
		DistributionSaveRequestDto requestDto = new DistributionSaveRequestDto();
		requestDto.setAmount(amount);
		requestDto.setPersonnel(personnel);

		String content = objectMapper.writeValueAsString(requestDto);

		return content;
	}

	@Test
	@DisplayName("받기 API: 받기 기능 API 테스트")
	public void receiveSave() throws Exception {
		Long roomId = Long.valueOf(1);
		Long userId = Long.valueOf(3);

		String content = this.받기요청("UR5");

		this.mockMvc.perform(
			post("/api/v1/receive")
				.header("X-ROOM-ID", roomId)
				.header("X-USER-ID", userId)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.amount").isNotEmpty())
			.andDo(print());

	}

	private String 받기요청(String token) throws JsonProcessingException {
		ReceiveSaveRequestDto requestDto = new ReceiveSaveRequestDto();
		requestDto.setToken(token);

		String content = objectMapper.writeValueAsString(requestDto);

		return content;
	}
}
