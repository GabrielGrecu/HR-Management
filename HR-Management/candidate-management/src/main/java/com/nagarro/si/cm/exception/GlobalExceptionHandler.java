package com.nagarro.si.cm.exception;

import com.nagarro.si.cm.service.CandidateServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException ex) {
        logger.error("Handling DuplicateResourceException: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
