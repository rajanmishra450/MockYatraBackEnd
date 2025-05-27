package com.mock.yatra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mock.yatra.client.GPTClient;
import com.mock.yatra.model.GPTMessage;
import com.mock.yatra.model.GPTRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GPTService {

	@Value("${openai.model}")
	@Autowired
	private String model;

	@Autowired
	private GPTClient gptClient;

	public String generatePaper(String prompt) {
		return gptClient.generatePaper(prepareGPTRequest(prompt));
	}

	private GPTRequest prepareGPTRequest(String prompt) {
		return GPTRequest.builder()
				.messages(
						List.of(GPTMessage.builder().role("system").content("You are an exam paper generator.").build(),
								GPTMessage.builder().role("user").content(prompt).build()))
				.model(model).build();
	}
}
