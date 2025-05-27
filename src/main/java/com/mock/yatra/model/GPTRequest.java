package com.mock.yatra.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GPTRequest {
	private String model;
    private List<GPTMessage> messages;
}