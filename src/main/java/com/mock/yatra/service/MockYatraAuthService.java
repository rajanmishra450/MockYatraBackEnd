package com.mock.yatra.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mock.yatra.entity.PasswordResetTokenEntity;
import com.mock.yatra.entity.UserDetailsEntity;
import com.mock.yatra.exception.BusinessException;
import com.mock.yatra.mapper.UserDetailsEntityMapper;
import com.mock.yatra.model.UserDetails;
import com.mock.yatra.repository.PasswordResetTokenRepository;
import com.mock.yatra.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MockYatraAuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender mailSender;

    public UserDetails performLogin(UserDetails request) {

        UserDetailsEntity user = userRepository.findByUserId(request.getUserId()).orElseThrow(
                () -> new BusinessException("User not found", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtService.generateToken(user.getUserId());
        return UserDetails.builder().token(token).build();
    }

    public void performSignUp(UserDetails request) {

        if (userRepository.findByUserId(request.getEmailId()).isPresent()) {
            throw new BusinessException("User already exists", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(UserDetailsEntityMapper.mapFromUserDetails(request));
    }

    public String performForgetPasswordOperation(String emailId) {

        UserDetailsEntity user = userRepository.findByUserId(emailId).orElseThrow(
                () -> new BusinessException("User not found", HttpStatus.NOT_FOUND));

        PasswordResetTokenEntity passwordResetTokenEntity = preparePasswordResetToken(user);
        passwordResetTokenRepository.save(passwordResetTokenEntity);

        sendPasswordResetMail(emailId, passwordResetTokenEntity.getToken());

        return passwordResetTokenEntity.getToken();
    }

    private PasswordResetTokenEntity preparePasswordResetToken(UserDetailsEntity user) {
        String token = UUID.randomUUID().toString();
        return PasswordResetTokenEntity.builder().expiryDate(LocalDateTime.now()
                .plusHours(2)).user(user).token(token).build();
    }

    private void sendPasswordResetMail(String emailId, String token) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailId);
        mail.setSubject("Reset Your Password");
        //TODO UI URL needs to set
        mail.setText("Click here to reset your password: http://localhost:8080/mock/yatra/auth/reset/password?token=" + token);
        mailSender.send(mail);
    }

    public void performResetPassword(String token, String newPassword) {
        PasswordResetTokenEntity resetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(
                () -> new BusinessException("User not found", HttpStatus.UNAUTHORIZED));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Password Link expired", HttpStatus.BAD_REQUEST);
        }

        UserDetailsEntity user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }
}
