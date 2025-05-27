package com.mock.yatra.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mock.yatra.entity.QuestionPaperEntity;
import com.mock.yatra.mapper.QuestionPaperEntityMapper;
import com.mock.yatra.repository.QuestionPaperRepository;
import com.mock.yatra.util.PaperVersionUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MockYatraPaperService {

	private final QuestionPaperRepository paperRepository;
	private final GPTService gptService;

	public String generateAndSavePaper(String examType) {
		
		log.info("received request to generate paper for exam type : {}",examType);

		List<QuestionPaperEntity> questionPapers = paperRepository.findByExamType(examType);

		List<String> existingVersions = fetchExistingversionsFromPaper(questionPapers);
		
		log.info("versions that are already present for exam : {} , {}",examType,existingVersions);
		
		String newVersion = PaperVersionUtil.generateNextVersion(examType, existingVersions);

		String prompt = GPTPromptBuilderService.buildPrompt(examType, existingVersions);
		
		String json = gptService.generatePaper(prompt);

		QuestionPaperEntity questionPaper = QuestionPaperEntityMapper.mapFromGPTResponse(json, examType, newVersion);

		paperRepository.save(questionPaper);

		log.info("Paper generated and saved sucessfully for exam type : {} having version : {}", examType,newVersion);
		
		return newVersion;
	}
	
	private List<String> fetchExistingversionsFromPaper(List<QuestionPaperEntity> questionPapers) {
	
		return questionPapers.stream().map(QuestionPaperEntity::getVersionId).collect(Collectors.toList());

	}
}
