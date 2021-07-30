package com.linkaja.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiErrorModel {

    private HttpStatus status;
    private String message;

    public ApiErrorModel(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

}
