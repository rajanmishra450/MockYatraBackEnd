package com.mock.yatra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mock.yatra.entity.QuestionPaperEntity;

@Repository
public interface QuestionPaperRepository extends JpaRepository<QuestionPaperEntity, String> {

	List<QuestionPaperEntity> findByExamType(String examType);
}
