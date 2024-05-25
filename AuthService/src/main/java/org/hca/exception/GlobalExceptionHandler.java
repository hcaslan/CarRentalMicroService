package org.hca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException ex) {
        System.err.println(ex.getMessage());
        return ResponseEntity.badRequest()
                .body("RunTime Error : " + ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleException2(MethodArgumentNotValidException ex){
        Map<String,String > error = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(errors -> {
            String fieldName = ((FieldError) errors ).getField();
            String errorMessage = errors.getDefaultMessage();
            error.put(fieldName,errorMessage);
        });
        return error;
    }

    @ExceptionHandler(AuthServiceException.class)
    public ResponseEntity<ErrorMessage> handleDemoException(AuthServiceException ex) {
        ErrorType errorType = ex.getErrorType();
        return new ResponseEntity(createErrorMessage(ex,
                errorType),
                errorType.getHttpStatus());
    }

    private ErrorMessage createErrorMessage(Exception ex, ErrorType errorType) {
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }
}