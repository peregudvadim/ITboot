package com.peregud.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
