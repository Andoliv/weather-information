package com.andoliv.weatherinformation.controller;

import com.andoliv.weatherinformation.external.ExtRestApiMessage;
import com.andoliv.weatherinformation.external.ExtWeatherRequest;
import com.andoliv.weatherinformation.model.Weather;
import com.andoliv.weatherinformation.service.WeatherService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.andoliv.weatherinformation.constants.ApiDocs.*;

@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = WEATHER_CONTROLLER_API_DESC, version = "1.0"))
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private static final String WEATHER_ADDED_SUCCESS = "Weather added successfully";
    private static final String WEATHER_REMOVED_SUCCESS = "Weather removed successfully";

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Operation(summary = GET_WEATHERS_OPERATION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = GET_WEATHER_LIST_200_RESPONSE)
    })
    @GetMapping
    public ResponseEntity<List<Weather>> getAllWeatherData() {
        return ResponseEntity.ok(weatherService.getAllWeatherData());
    }

    @Operation(summary = GET_WEATHER_BY_CITY_OPERATION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = GET_WEATHER_200_RESPONSE)
    })
    @PostMapping("/city")
    public ResponseEntity<Weather> getWeatherForCity(@RequestBody ExtWeatherRequest extWeatherRequest) {

        Weather weather = weatherService.getWeatherByCity(extWeatherRequest.getCity());

        if (weather == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(weather);
    }

    @Operation(summary = ADD_WEATHER_OPERATION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ADD_WEATHER_201_RESPONSE)
    })
    @PostMapping
    public ResponseEntity<String> addWeather(@RequestBody Weather weather) {
        try {
            weatherService.addWeather(weather);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        URI location = URI.create("/api/weather");

        return ResponseEntity.created(location).body(WEATHER_ADDED_SUCCESS);
    }

    @Operation(summary = DELETE_WEATHER_OPERATION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = DELETE_WEATHER_OPERATION)
    })
    @DeleteMapping("/{city}")
    public ResponseEntity<String> removeWeather(@PathVariable String city) {
        try {
            weatherService.removeWeather(city);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(DEFAULT_500_RESPONSE);
        }

        return ResponseEntity.accepted().build();
    }

}
