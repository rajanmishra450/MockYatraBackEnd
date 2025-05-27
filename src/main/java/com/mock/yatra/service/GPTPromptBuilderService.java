package com.mock.yatra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GPTPromptBuilderService {
	
    /**
     * Build a ChatGPT prompt to generate a unique paper for the given exam type.
     * 
     * @param examType The type of exam (e.g., SSC CGL)
     * @param previousVersions List of version IDs or brief summaries of previous papers
     * @return Formatted prompt string
     */
    public static String buildPrompt(String examType, List<String> previousVersions) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a test paper generator for competitive exams like ")
              .append(examType)
              .append(".\n");

        if (!previousVersions.isEmpty()) {
            prompt.append("Avoid reusing topics, questions, or structure from these previous versions:\n");
            for (String version : previousVersions) {
                prompt.append("- ").append(version).append("\n");
            }
        }

        prompt.append("""
        Now generate a new question paper with the following structure:
        - Format: JSON array
        - Each object is a section (e.g., Reasoning, Quantitative Aptitude)
        - Each section contains 5 questions
        - Each question has: id, question, options (A-D), correctAnswer

        Return only valid JSON in this format:
        [
          {
            "section": "Reasoning",
            "questions": [
              {
                "id": "Q1",
                "question": "What comes next in the series: 2, 4, 8, ?",
                "options": { "A": "10", "B": "12", "C": "16", "D": "14" },
                "correctAnswer": "C"
              }
            ]
          }
        ]

        Ensure this paper is fresh and does not overlap with earlier papers.
        """);

        return prompt.toString();
    }

}
