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
    MAIL_SEND_FAIL(3003,"Failed to send email." , HttpStatus.INTERNAL_SERVER_ERROR);

    private Integer code;
    private String message;
    private HttpStatus httpStatus;

}