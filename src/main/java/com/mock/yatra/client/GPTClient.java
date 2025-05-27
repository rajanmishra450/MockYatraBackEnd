package com.mock.yatra.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mock.yatra.exception.BusinessException;
import com.mock.yatra.model.GPTRequest;
import com.mock.yatra.model.GPTResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GPTClient {

	@Value("${openai.api.key}")
	private String apiKey;

	@Value("${openai.api.url}")
	private String apiUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	public String generatePaper(GPTRequest request) {
		log.info("Received request to generate paper request : {}", request);

		HttpEntity<GPTRequest> httpRequest = new HttpEntity<>(request, getHeaders());

		try {

			ResponseEntity<GPTResponse> response = restTemplate.exchange(apiUrl, HttpMethod.POST, httpRequest,
					GPTResponse.class);

			log.info("Received response from GPT : {}", response);

			if (response.getStatusCode().is2xxSuccessful()) {
				List<GPTResponse.Choice> choices = response.getBody().getChoices();
				if (choices != null && !choices.isEmpty()) {
					return choices.get(0).getMessage().getContent();
				}
			}
		} catch (Exception ex) {
			log.error("Gettting Error while calling GPT {}", ex.toString());
		}

		throw new BusinessException("No response from OpenAI", HttpStatus.CONFLICT);
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(apiKey);
		return headers;
	}
}
