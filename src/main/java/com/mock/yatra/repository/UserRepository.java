package com.mock.yatra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mock.yatra.entity.UserDetailsEntity;

@Repository
public interface UserRepository extends JpaRepository<UserDetailsEntity, String> {
	
    Optional<UserDetailsEntity> findByUserId(String userId);
}

