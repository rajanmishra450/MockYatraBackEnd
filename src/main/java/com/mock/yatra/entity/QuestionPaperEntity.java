package com.mock.yatra.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question_paper")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionPaperEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "exam_type")
    private String examType;

    @Column(name = "version_id", unique = true)
    private String versionId;

    @Lob
    @Column(name = "sections")
    private String sections;

    @CreationTimestamp
    @Column(name = "created_timestamp")
    private LocalDateTime createdTimestamp;	
    
    @Column(name = "last_updated_timestamp")
    private LocalDateTime lastUpdatedTimestamp;	
	
}
