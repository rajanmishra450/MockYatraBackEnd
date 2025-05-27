package com.mock.yatra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mock.yatra.model.UserDetails;
import com.mock.yatra.service.MockYatraAuthService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/mock/yatra/auth")
@Slf4j
public class MockYatraAuthController {

	private final MockYatraAuthService mockYatraAuthService;

	public MockYatraAuthController(MockYatraAuthService mockYatraAuthService) {
		this.mockYatraAuthService = mockYatraAuthService;
	}

	@PostMapping("/login")
	public ResponseEntity<UserDetails> login(@RequestBody UserDetails request) {
		log.info("Received Log In Request for userId : {}", request.getUserId());
		UserDetails logInResponse = mockYatraAuthService.performLogin(request);
		return ResponseEntity.ok(logInResponse);
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDetails request) {
		log.info("Received Register Request for userId : {}", request.getEmailId());
		mockYatraAuthService.performSignUp(request);
		return ResponseEntity.ok("User registered successfully");
	}

	@PostMapping("/forgot/password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email) {
		log.info("Received Forget password request for userId : {}", email);
		mockYatraAuthService.performForgetPasswordOperation(email);
		return ResponseEntity.ok("Password Reset link sent to your email");
	}

	@PostMapping("/reset/password")
	public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
		log.info("Received reset password request for token : {}", token);
		mockYatraAuthService.performResetPassword(token, newPassword);
		return ResponseEntity.ok("Password reset successfully");
	}
}
