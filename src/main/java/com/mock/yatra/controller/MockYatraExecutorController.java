package com.mock.yatra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mock.yatra.model.UserDetails;
import com.mock.yatra.service.MockYatraExecutorService;

@RestController
@RequestMapping("/mock/yatra")
public class MockYatraExecutorController {

	private final MockYatraExecutorService mockYatraExecutorService;
    
    public MockYatraExecutorController(MockYatraExecutorService mockYatraExecutorService) {
        this.mockYatraExecutorService = mockYatraExecutorService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDetails> login(@RequestBody UserDetails request) {
    	UserDetails logInResponse = mockYatraExecutorService.performLogin(request);
        return ResponseEntity.ok(logInResponse);
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDetails request) {
    	mockYatraExecutorService.performSignUp(request);
        return ResponseEntity.ok("User registered successfully");
    }
}
