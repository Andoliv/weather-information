package com.andoliv.weatherinformation.service;

import com.andoliv.weatherinformation.constants.ApiDocs;
import com.andoliv.weatherinformation.model.Weather;
import com.andoliv.weatherinformation.utils.WeatherConditionsEnum;
import com.andoliv.weatherinformation.utils.WeatherUnitsEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.andoliv.weatherinformation.constants.ApiDocs.INVALID_REQUEST_BODY_400_RESPONSE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    private WeatherService weatherService;

    @BeforeEach
    public void setUp() {
        weatherService = spy(new WeatherService());
    }

    @Test
    void fetchWeatherFromExternalApi_ShouldReturnWeather() {
        // Given
        String city = "Sydney";
        String expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // When
        Weather weather = weatherService.fetchWeatherFromExternalApi(city);

        // Then
        assertNotNull(weather);
        assertEquals(city, weather.getCity());
        assertEquals("25", weather.getTemp());
        assertEquals(WeatherUnitsEnum.CELSIUS, weather.getUnit());
        assertEquals(expectedDate, weather.getDate());
        assertEquals(WeatherConditionsEnum.SUNNY, weather.getWeather());
    }

    @Test
    void getWeatherByCity_ShouldReturnWeather_FromCache() {
        // Given
        String city = "Auckland";

        // When
        Weather weather = weatherService.getWeatherByCity(city);

        // Then
        assertNotNull(weather);
        assertEquals(city, weather.getCity());
        assertEquals("24", weather.getTemp());
        assertEquals(WeatherUnitsEnum.CELSIUS, weather.getUnit());
        assertEquals(WeatherConditionsEnum.SUNNY, weather.getWeather());
    }

    @Test
    void getWeatherByCity_ShouldFetchFromExternalApi_WhenNotInCache() {
        // Given
        String city = "Sydney";
        String expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // When
        Weather weather = weatherService.getWeatherByCity(city);

        // Then
        verify(weatherService, times(1)).fetchWeatherFromExternalApi(city);
        assertNotNull(weather);
        assertEquals(city, weather.getCity());
        assertEquals("25", weather.getTemp());
        assertEquals(WeatherUnitsEnum.CELSIUS.toString(), weather.getUnit());
        assertEquals(expectedDate, weather.getDate());
    }

    @Test
    void getWeatherByCity_ShouldThrowException_ForNullCity() {
        // Given
        String city = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> weatherService.getWeatherByCity(city)
        );

        assertEquals(INVALID_REQUEST_BODY_400_RESPONSE, exception.getMessage());
    }

    @Test
    void getWeatherByCity_ShouldThrowException_ForEmptyCity() {
        // Given
        String city = "  ";

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> weatherService.getWeatherByCity(city)
        );

        assertEquals(INVALID_REQUEST_BODY_400_RESPONSE, exception.getMessage());
    }

    @Test
    void addWeather_ShouldAddWeatherToCache() {
        // Given
        Weather newWeather = new Weather("Sydney", "30", WeatherUnitsEnum.CELSIUS, "25/10/2023", WeatherConditionsEnum.SUNNY);

        // When
        weatherService.addWeather(newWeather);

        // Then
        Weather fromCache = weatherService.getWeatherByCity("sydney");
        assertNotNull(fromCache);
        assertEquals(newWeather, fromCache);
    }

    @Test
    void removeWeather_ShouldRemoveWeatherFromCache() {
        // Given
        String city = "Auckland";

        // When
        weatherService.removeWeather(city);

        // Then
        List<Weather> weatherData = weatherService.getAllWeatherData();
        verify(weatherService, times(1)).removeWeather(city);
        assertEquals(2, weatherData.size());
    }

    @Test
    void getAllWeatherData_ShouldReturnAllWeatherFromCache() {
        // When
        List<Weather> weatherData = weatherService.getAllWeatherData();

        // Then
        assertNotNull(weatherData);
        assertEquals(3, weatherData.size());
    }

}
