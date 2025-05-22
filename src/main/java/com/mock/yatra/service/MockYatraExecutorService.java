package com.mock.yatra.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mock.yatra.entity.UserDetailsEntity;
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
		// TODO create business exception
		UserDetailsEntity user = userRepository.findByUserId(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		String token = jwtService.generateToken(user.getUserId());
		return UserDetails.builder().token(token).build();
	}

	public void performSignUp(UserDetails request) {

		if (userRepository.findByUserId(request.getUserId()).isPresent()) {
			// TODO need to check
		}
		userRepository.save(UserDetailsEntityMapper.mapFromUserDetails(request));
	}

}
