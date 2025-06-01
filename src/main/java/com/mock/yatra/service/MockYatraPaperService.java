package com.mock.yatra.service;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mock.yatra.exception.BusinessException;
import com.mock.yatra.model.QuestionPaperData;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
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
    private final ObjectMapper objectMapper;

    public String generateAndSavePaper(String examType) {

        log.info("received request to generate paper for exam type : {}", examType);

        //TODO its not recommended we are fetching all question paper to generate version
        List<QuestionPaperEntity> questionPapers = paperRepository.findByExamType(examType);

        List<String> existingVersions = fetchExistingVersionsFromPaper(questionPapers);

        log.info("versions that are already present for exam : {} , {}", examType, existingVersions);

        String newVersion = PaperVersionUtil.generateNextVersion(examType, existingVersions);

        String prompt = GPTPromptBuilderService.buildPrompt(examType, existingVersions);

        String json = gptService.generatePaper(prompt);

        QuestionPaperEntity questionPaper = QuestionPaperEntityMapper.mapFromGPTResponse(json, examType, newVersion);

        paperRepository.save(questionPaper);

        log.info("Paper generated and saved successfully for exam type : {} having version : {}", examType, newVersion);

        return newVersion;
    }

    private List<String> fetchExistingVersionsFromPaper(List<QuestionPaperEntity> questionPapers) {

        return questionPapers.stream().map(QuestionPaperEntity::getVersionId).collect(Collectors.toList());

    }

	public QuestionPaperData getQuestionPaper(String examType,String userid) {
		QuestionPaperEntity questionPaperEntity = getUnattemptedPaper(userid,examType);
		// mapper will convert into Json that will return to UI and
        return getQuestionPaperData(questionPaperEntity);
	}

    private QuestionPaperData getQuestionPaperData(QuestionPaperEntity questionPaperEntity)  {
        try {
            return objectMapper.readValue(questionPaperEntity.getSections(), QuestionPaperData.class);
        }
        catch (Exception e) {
            log.error("exception occurred while parsing question paper data : {}", e.getMessage());
            throw new BusinessException("exception occurred while parsing question paper data",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private QuestionPaperEntity getUnattemptedPaper(String userId, String examType) {
        Page<QuestionPaperEntity> page = paperRepository.findOneUnattemptedPaper(
                userId, examType, PageRequest.of(0, 1)
        );
        if(page.isEmpty()) {
            log.error("Question paper not found for exam type : {} , userid : {}", examType, userId);
            //fetch any question paper so that user will not face any issue
            return getAnyOnePaper(examType);
        }
        return page.getContent().get(0);
    }

    private QuestionPaperEntity getAnyOnePaper(String examType) {
        Page<QuestionPaperEntity> page = paperRepository.findAnyOnePaper(examType,PageRequest.of(0, 1)
        );
        if (page.isEmpty()) {
            log.error("Question paper not found for exam type : {} ", examType);
            throw new BusinessException("Question paper not found for exam type " + examType , HttpStatus.NOT_FOUND);
        }
        return page.getContent().get(0);
    }
}