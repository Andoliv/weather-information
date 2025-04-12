package com.andoliv.weatherinformation.external;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExtRestApiMessage {

    private final int code;
    private final String status;
    private final LocalDateTime timestamp;
    private final String message;

    public ExtRestApiMessage(Exception exception, HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = exception.getMessage();
    }

    public ExtRestApiMessage(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
