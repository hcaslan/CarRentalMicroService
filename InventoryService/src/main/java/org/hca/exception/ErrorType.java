package org.hca.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    ACCESS_DENIED(3001,
            "Access denied.",
            HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR(3002,
            "Service is not responding.",
            HttpStatus.INTERNAL_SERVER_ERROR),
    BRAND_NOT_FOUND(3003,"Brand cannot found" , HttpStatus.BAD_REQUEST),
    MODEL_NOT_FOUND(3003,"Model cannot found" , HttpStatus.BAD_REQUEST),
    CAR_NOT_FOUND(3003,"Car cannot found" , HttpStatus.BAD_REQUEST);

    private Integer code;
    private String message;
    private HttpStatus httpStatus;

}