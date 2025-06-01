package com.mock.yatra.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mock.yatra.entity.QuestionPaperEntity;

@Repository
public interface QuestionPaperRepository extends JpaRepository<QuestionPaperEntity, Long> {

    List<QuestionPaperEntity> findByExamType(String examType);

    @Query("""
                SELECT qp FROM QuestionPaperEntity qp
                LEFT JOIN TestAttemptEntity ta ON ta.questionPaperId = qp.id AND ta.userId = :userId
                WHERE ta.id IS NULL AND qp.examType = :examType
                ORDER BY qp.createdTimestamp DESC
            """)
    Page<QuestionPaperEntity> findOneUnattemptedPaper(
            @Param("userId") String userId,
            @Param("examType") String examType,
            Pageable pageable
    );

    @Query("""
                SELECT qp FROM QuestionPaperEntity qp
                WHERE qp.examType = :examType
                ORDER BY qp.createdTimestamp DESC
            """)
    Page<QuestionPaperEntity> findAnyOnePaper(@Param("examType") String examType, Pageable pageable);
}