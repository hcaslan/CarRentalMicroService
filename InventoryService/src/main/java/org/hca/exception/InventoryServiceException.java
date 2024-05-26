package org.hca.exception;


import lombok.Getter;

@Getter
public class InventoryServiceException extends RuntimeException{
    private ErrorType errorType;

    public InventoryServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public InventoryServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}