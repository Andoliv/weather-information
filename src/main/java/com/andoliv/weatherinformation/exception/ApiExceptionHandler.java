package com.andoliv.weatherinformation.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.andoliv.weatherinformation.constants.ApiDocs.DEFAULT_500_RESPONSE;
import static com.andoliv.weatherinformation.constants.ApiDocs.GET_WEATHER_404_RESPONSE;


@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(WeatherNotFoundException.class)
    public ResponseEntity<String> handleWeatherNotFoundException(WeatherNotFoundException ex) {
        LOG.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GET_WEATHER_404_RESPONSE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        LOG.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DEFAULT_500_RESPONSE);
    }

}
