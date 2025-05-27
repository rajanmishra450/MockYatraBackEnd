package com.mock.yatra.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.mock.yatra.entity.QuestionPaperEntity;

@Component
public class QuestionPaperEntityMapper {
	
	public static QuestionPaperEntity mapFromGPTResponse(String json,String examType,String version) {
		
		return QuestionPaperEntity.builder()
				.title("Mock Test - " + examType)
				.sections(json)
				.examType(examType)
				.versionId(version)
				.createdTimestamp(LocalDateTime.now())
				.lastUpdatedTimestamp(LocalDateTime.now())
				.build();
	}
}
