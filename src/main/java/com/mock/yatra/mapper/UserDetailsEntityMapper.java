package com.mock.yatra.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.mock.yatra.entity.UserDetailsEntity;
import com.mock.yatra.model.UserDetails;

@Component
public class UserDetailsEntityMapper {
	
	private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	
	public static UserDetailsEntity mapFromUserDetails(UserDetails userDetails) {
		
		return UserDetailsEntity.builder()
				.name(userDetails.getName())
				.dob(userDetails.getDOB())
				.emailId(userDetails.getEmailId())
				.password(bCryptPasswordEncoder.encode(userDetails.getPassword()))
				.userId(userDetails.getEmailId())
				.build();
	}
}
