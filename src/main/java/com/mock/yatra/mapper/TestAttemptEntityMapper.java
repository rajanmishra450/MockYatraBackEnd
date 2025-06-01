package com.mock.yatra.mapper;

import com.mock.yatra.entity.TestAttemptEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestAttemptEntityMapper {

    public static TestAttemptEntity mapToTestAttemptEntity(Long questionPaperId,String userId) {

        return TestAttemptEntity.builder()
                .questionPaperId(questionPaperId)
                .userId(userId)
                .startTimestamp(LocalDateTime.now())
                .build();
    }
}
