package com.mock.yatra.controller;

import com.mock.yatra.model.QuestionPaperData;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mock.yatra.service.MockYatraPaperService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/mock/yatra/paper")
@Slf4j
@AllArgsConstructor
public class MockYatraPaperController {

	private MockYatraPaperService paperService;

    @PostMapping("/generate/{examType}")
    public ResponseEntity<String> generatePaper(@PathVariable String examType) {
        String version = paperService.generateAndSavePaper(examType);
        return ResponseEntity.ok("Generated version: " + version);
    }
    
    @GetMapping("/start/{examType}")
    public ResponseEntity<QuestionPaperData> startPaper(@AuthenticationPrincipal String userId,@PathVariable String examType) {
        log.info("User : {} Starting paper for examType {} ",userId, examType);
        QuestionPaperData questionPaperData = paperService.getQuestionPaper(examType,userId);
        return ResponseEntity.ok(questionPaperData);
    }

    @PostMapping("/submit/{examType}")
    public ResponseEntity<QuestionPaperData> submitPaper(@AuthenticationPrincipal String userId,@PathVariable String examType) {
        log.info("User : {} Submitting paper for examType {} ",userId, examType);
        QuestionPaperData questionPaperData = paperService.getQuestionPaper(examType,userId);
        return ResponseEntity.ok(questionPaperData);
    }
}
