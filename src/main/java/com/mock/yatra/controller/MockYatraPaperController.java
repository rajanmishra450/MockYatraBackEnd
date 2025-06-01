package com.mock.yatra.controller;

import com.mock.yatra.model.QuestionPaperData;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{examType}")
    public ResponseEntity<QuestionPaperData> getExamPaper(@AuthenticationPrincipal String userId,@PathVariable String examType) {
        log.info("User : {} fetching paper for examType {} ",userId, examType);
        QuestionPaperData questionPaperData = paperService.getQuestionPaper(examType,userId);
        return ResponseEntity.ok(questionPaperData);
    }
    
    @PostMapping("/start/{questionPaperId}")
    public ResponseEntity<Void> startPaper(@AuthenticationPrincipal String userId,@PathVariable Long questionPaperId) {
        log.info("User : {} Starting paper for questionPaperId {} ",userId, questionPaperId);
        paperService.startPaper(questionPaperId,userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitPaper(@AuthenticationPrincipal String userId,
                                                         @RequestBody QuestionPaperData questionPaperData) {
        log.info("User : {} Submitting paper for questionPaperId {} ",userId, questionPaperData.getQuestionPaper().getId());
        paperService.submitPaper(questionPaperData,userId);
        return ResponseEntity.ok().build();
    }
}
