package com.mock.yatra.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class UserDetails {
	
	private String userId;
	private String name;
    private String password;
    private String token;  
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate DOB;
    private String emailId;
}
