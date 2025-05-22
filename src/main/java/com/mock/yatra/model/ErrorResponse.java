package com.mock.yatra.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
	
	 private LocalDateTime timestamp;
	 private int status;
     private String error;
	 private String message;
	 private String path;
}