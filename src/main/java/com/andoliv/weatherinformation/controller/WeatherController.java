package com.andoliv.weatherinformation.controller;

import com.andoliv.weatherinformation.model.Weather;
import com.andoliv.weatherinformation.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private static final String WEATHER_ADDED_SUCCESS = "Weather added successfully";
    private static final String WEATHER_REMOVED_SUCCESS = "Weather removed successfully";

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<List<Weather>> getAllWeatherData() {
        return ResponseEntity.ok(weatherService.getAllWeatherData());
    }

    @GetMapping("/{city}")
    public ResponseEntity<Weather> getWeatherForCity(@PathVariable String city) {
        Weather weather = weatherService.getWeatherByCity(city);

        if (weather == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(weather);
    }

    @PostMapping
    public ResponseEntity<String> addWeather(@RequestBody Weather weather) {
        try {
            weatherService.addWeather(weather);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(WEATHER_ADDED_SUCCESS);
    }

    @DeleteMapping("/{city}")
    public ResponseEntity<String> removeWeather(@PathVariable String city) {
        try {
            weatherService.removeWeather(city);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(WEATHER_REMOVED_SUCCESS);
    }

}
