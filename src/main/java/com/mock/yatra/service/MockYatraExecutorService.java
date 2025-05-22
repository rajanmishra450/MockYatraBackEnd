package com.mock.yatra.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mock.yatra.entity.UserDetailsEntity;
import com.mock.yatra.exception.BusinessException;
import com.mock.yatra.mapper.UserDetailsEntityMapper;
import com.mock.yatra.model.UserDetails;
import com.mock.yatra.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MockYatraExecutorService {

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserDetails performLogin(UserDetails request) {
	
		UserDetailsEntity user = userRepository.findByUserId(request.getUserId())
				.orElseThrow(() -> new BusinessException("User not found",HttpStatus.UNAUTHORIZED));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new BusinessException("Invalid credentials",HttpStatus.UNAUTHORIZED);
		}

		String token = jwtService.generateToken(user.getUserId());
		return UserDetails.builder().token(token).build();
	}

	public void performSignUp(UserDetails request) {

		if (userRepository.findByUserId(request.getUserId()).isPresent()) {
			throw new BusinessException("User already exists",HttpStatus.BAD_REQUEST);
		}
		userRepository.save(UserDetailsEntityMapper.mapFromUserDetails(request));
	}

}
