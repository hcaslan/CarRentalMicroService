package org.hca.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    ACCESS_DENIED(2001,
            "Access denied.",
            HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR(2002,
            "Service is not responding.",
            HttpStatus.INTERNAL_SERVER_ERROR),
    USER_ALREADY_EXIST(2003,"User already exists." , HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(2004,"User not found." , HttpStatus.BAD_REQUEST),
    USER_ALREADY_DELETED(2005,"User already deleted." , HttpStatus.BAD_REQUEST),
    INCORRECT_EMAIL_OR_PASSWORD(2006,"Email or password is incorrect." , HttpStatus.BAD_REQUEST),
    PASSWORDS_NOT_MATCHING(2007,"Passwords do not match." , HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(2007,"Invalid token." , HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(2007,"Token expired or already confirmed." , HttpStatus.BAD_REQUEST);

    private Integer code;
    private String message;
    private HttpStatus httpStatus;

}