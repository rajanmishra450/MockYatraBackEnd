package com.mock.yatra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TEST_ATTEMPT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestAttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_attempt_seq_gen")
    @SequenceGenerator(name = "test_attempt_seq_gen", sequenceName = "test_attempt_seq", allocationSize = 1)
    private Long id;

    private String userId;

    @Lob
    private String answer;

    private Integer score;

    @Column(name = "STARTED_TIMESTAMP")
    private LocalDateTime startTimestamp;

    @Column(name = "SUBMITTED_TIMESTAMP")
    private LocalDateTime submittedTimestamp;

    private Long questionPaperId;
}
