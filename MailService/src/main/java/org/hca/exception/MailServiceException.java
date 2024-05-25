package org.hca.exception;


import lombok.Getter;

@Getter
public class MailServiceException extends RuntimeException{
    private ErrorType errorType;

    public MailServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public MailServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}