package com.mock.yatra.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "password_reset_token")
public class PasswordResetTokenEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reset_token_seq_gen")
	@SequenceGenerator(name = "reset_token_seq_gen", sequenceName = "password_reset_token_seq", allocationSize = 1)
	private Long id;

	private String token;

	@Column(name = "expiry_date")
	private LocalDateTime expiryDate;

	@OneToOne
	@JoinColumn(name = "USER_ID")
	private UserDetailsEntity user;
}
