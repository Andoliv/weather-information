package com.andoliv.weatherinformation.controller;

import com.andoliv.weatherinformation.external.ExtRestApiMessage;
import com.andoliv.weatherinformation.external.ExtWeatherRequest;
import com.andoliv.weatherinformation.model.Weather;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.andoliv.weatherinformation.constants.ApiDocs.*;

@Tag(name = WEATHER_CONTROLLER_API_NAME, description = WEATHER_CONTROLLER_API_DESC)
@RequestMapping("/api/weather")
public interface WeatherController {

    @Operation(
            summary = GET_WEATHERS_OPERATION,
            description = GET_WEATHERS_OPERATION_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HTTP_200_CODE,
                    description = GET_WEATHER_200_RESPONSE,
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Weather.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = HTTP_500_CODE,
                    description = DEFAULT_500_RESPONSE,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    @GetMapping
    ResponseEntity<List<Weather>> getAllWeatherData();

    @Operation(
            summary = GET_WEATHER_BY_CITY_OPERATION,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExtWeatherRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HTTP_200_CODE,
                    description = GET_WEATHER_200_RESPONSE,
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExtRestApiMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = HTTP_404_CODE,
                    description = GET_WEATHER_404_RESPONSE,
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExtRestApiMessage.class)
                            )
                    }
            )
    })
    @PostMapping("/city")
    ResponseEntity<Weather> getWeatherForCity(@Valid @NotNull @RequestBody ExtWeatherRequest extWeatherRequest);

    @Operation(summary = ADD_WEATHER_OPERATION)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HTTP_201_CODE,
                    description = ADD_WEATHER_201_RESPONSE,
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExtRestApiMessage.class)
                            )
                    }
            )
    })
    @PostMapping
    ResponseEntity<ExtRestApiMessage> addWeather(@RequestBody Weather weather);

    @Operation(summary = DELETE_WEATHER_OPERATION)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HTTP_202_CODE,
                    description = DELETE_WEATHER_OPERATION,
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExtRestApiMessage.class)
                            )
                    }
            )
    })
    @DeleteMapping("/{city}")
    ResponseEntity<ExtRestApiMessage> removeWeather(@PathVariable String city);

}
