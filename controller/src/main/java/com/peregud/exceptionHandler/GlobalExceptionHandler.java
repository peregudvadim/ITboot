package com.peregud.exceptionHandler;

import com.peregud.dto.ErrorCommonDto;
import com.peregud.exception.EmployeeNotFoundException;
import com.peregud.exception.ProjectNotFoundException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmployeeNotFoundException.class, ProjectNotFoundException.class})
    public ResponseEntity<ErrorCommonDto> handleEmployeeNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorCommonDto.builder()
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ErrorCommonDto> handleValidationExceptions(PropertyValueException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorCommonDto.builder()
                .message("Property Value Exception: " + ex.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCommonDto> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorCommonDto.builder()
                .message("Internal Server Error: " + ex.getMessage())
                .build());
    }
}
