package com.andoliv.weatherinformation.controller;

import com.andoliv.weatherinformation.exception.WeatherNotFoundException;
import com.andoliv.weatherinformation.external.ExtRestApiMessage;
import com.andoliv.weatherinformation.external.ExtWeatherRequest;
import com.andoliv.weatherinformation.model.Weather;
import com.andoliv.weatherinformation.service.WeatherService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.andoliv.weatherinformation.constants.ApiDocs.GET_WEATHER_404_RESPONSE;

@RestController
public class WeatherRestController implements WeatherController {

    private static final String WEATHER_ADDED_SUCCESS = "Weather added successfully";
    private static final String WEATHER_REMOVED_SUCCESS = "Weather removed successfully";

    private final WeatherService weatherService;

    @Autowired
    public WeatherRestController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<List<Weather>> getAllWeatherData() {
        return ResponseEntity.ok(weatherService.getAllWeatherData());
    }

    @PostMapping("/city")
    public ResponseEntity<Weather> getWeatherForCity(@Valid @NotNull @RequestBody ExtWeatherRequest extWeatherRequest) {

        Weather weather = weatherService.getWeatherByCity(extWeatherRequest.getCity());

        if (weather == null) {
            throw new WeatherNotFoundException(GET_WEATHER_404_RESPONSE);
        }

        return ResponseEntity.ok(weather);
    }

    @PostMapping
    public ResponseEntity<ExtRestApiMessage> addWeather(@RequestBody Weather weather) {
        try {
            weatherService.addWeather(weather);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ExtRestApiMessage(e, HttpStatus.INTERNAL_SERVER_ERROR));
        }

        URI location = URI.create("/api/weather");

        return ResponseEntity.created(location).body(new ExtRestApiMessage(HttpStatus.CREATED, WEATHER_ADDED_SUCCESS));
    }

    @DeleteMapping("/{city}")
    public ResponseEntity<ExtRestApiMessage> removeWeather(@PathVariable String city) {
        try {
            weatherService.removeWeather(city);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ExtRestApiMessage(e, HttpStatus.INTERNAL_SERVER_ERROR));
        }

        return ResponseEntity.accepted().body(new ExtRestApiMessage(HttpStatus.ACCEPTED, WEATHER_REMOVED_SUCCESS));
    }
}
