package com.mock.yatra.repository;

import com.mock.yatra.entity.TestAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface TestAttemptRepository extends JpaRepository<TestAttemptEntity, Long> {


    @Query("update TestAttemptEntity t set t.answer = :answer ,t.score = :score, t.submittedTimestamp = :submittedTimeStamp" +
            " where t.userId = :userId and t.questionPaperId = :questionPaperId")
    @Modifying
    @Transactional
    void updateTestAttemptByUserIdAndQuestionPaperId(String answer,Integer score, LocalDateTime submittedTimeStamp, String userId, Long questionPaperId);
}
