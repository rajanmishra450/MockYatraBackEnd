package com.mock.yatra.exception;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mock.yatra.model.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {
		log.error("Business Error occurred while calling this request {} ,Exception : {} ",request,ex.toString());
        ErrorResponse error = ErrorResponse.builder()
        		.error(ex.getStatus().getReasonPhrase())
        		.message(ex.getMessage())
        		.path(request.getRequestURI())
        		.status(ex.getStatus().value())
        		.timestamp(LocalDateTime.now())
        		.build();

        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(
            Exception ex, HttpServletRequest request) {
		log.error("Error occurred while calling this request {} ,Exception : {} ",request,ex.toString());
    	ErrorResponse error = ErrorResponse.builder()
    			.error("Internal Server Error")
    			.message("Something went wrong. Please try again later.")
         		.path(request.getRequestURI())
         		.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
         		.timestamp(LocalDateTime.now())
         		.build();
    	
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
