package com.mock.yatra.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsEntity {

	@Id
	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "dob")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dob;

	@Column(name = "password", nullable = false)
	private String password;
}
