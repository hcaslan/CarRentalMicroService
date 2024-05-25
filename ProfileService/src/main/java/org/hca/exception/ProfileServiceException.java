package org.hca.exception;


import lombok.Getter;

@Getter
public class ProfileServiceException extends RuntimeException{
    private ErrorType errorType;

    public ProfileServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ProfileServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}