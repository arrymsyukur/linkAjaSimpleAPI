package com.linkaja.demo.exception;

import com.linkaja.demo.model.ApiErrorModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<ApiErrorModel> handleDataNotFound(RuntimeException ex, WebRequest request) {
        ApiErrorModel apiError = new ApiErrorModel(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(value = {TransactionException.class})
    protected ResponseEntity<ApiErrorModel> handleTransactionException(RuntimeException ex, WebRequest request) {
        ApiErrorModel apiError = new ApiErrorModel(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ApiErrorModel> handleOtherException(RuntimeException ex, WebRequest request) {
        ApiErrorModel apiError = new ApiErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went Wrong, Please Call Customer Service");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
