package org.hca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException3(MethodArgumentTypeMismatchException ex) {
        Map<String, String> error = new HashMap<>();
        String parameterName = ex.getName();
        String errorMessage = String.format("Parameter '%s' should be of type '%s'",
                parameterName,
                ex.getRequiredType().getSimpleName());
        error.put(parameterName, errorMessage);
        return error;
    }

    @ExceptionHandler(ProfileServiceException.class)
    public ResponseEntity<ErrorMessage> handleDemoException(ProfileServiceException ex) {
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