package com.peregud.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
