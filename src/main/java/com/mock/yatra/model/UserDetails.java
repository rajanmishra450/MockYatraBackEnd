package com.mock.yatra.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@ToString
public class UserDetails {
	
	private String userId;
	private String name;
    private String password;
    private String token;  
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonProperty(value = "dateOfBirth")
    private LocalDate DOB;
    @JsonProperty(value = "email")
    private String emailId;
}
