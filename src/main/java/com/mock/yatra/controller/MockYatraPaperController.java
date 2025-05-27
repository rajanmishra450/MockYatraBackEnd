package com.mock.yatra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mock.yatra.entity.UserDetailsEntity;
import com.mock.yatra.service.MockYatraPaperService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/mock/yatra/papers")
@Slf4j
@AllArgsConstructor
public class MockYatraPaperController {
	
	private MockYatraPaperService paperService;

    @PostMapping("/generate/{examType}")
    public ResponseEntity<String> generatePaper(@PathVariable String examType) {
        String version = paperService.generateAndSavePaper(examType);
        return ResponseEntity.ok("Generated version: " + version);
    }
    
    @GetMapping("/start")
    public ResponseEntity<String> startPaper(@AuthenticationPrincipal String userId ) {
        
        return ResponseEntity.ok("User Loggin Is " +userId );
    }
}
