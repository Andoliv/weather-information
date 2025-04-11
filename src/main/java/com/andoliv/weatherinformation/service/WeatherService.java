package com.andoliv.weatherinformation.service;

import com.andoliv.weatherinformation.model.Weather;
import com.andoliv.weatherinformation.utils.WeatherConditionsEnum;
import com.andoliv.weatherinformation.utils.WeatherUnitsEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final Map<String, Weather> weatherCache = new HashMap<>() {{
        put("auckland", new Weather("Auckland", "24", WeatherUnitsEnum.CELSIUS, "23/10/2023", WeatherConditionsEnum.SUNNY));
        put("wellington", new Weather("Wellington", "18", WeatherUnitsEnum.CELSIUS, "23/10/2023", WeatherConditionsEnum.RAINY));
        put("christchurch", new Weather("Christchurch", "20", WeatherUnitsEnum.CELSIUS, "23/10/2023", WeatherConditionsEnum.SNOWY));
    }};

    public Weather fetchWeatherFromExternalApi(String city) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return new Weather(city, "25", "C", today, WeatherConditionsEnum.SUNNY);
    }

    public Weather getWeatherByCity(String city) {
        if (city == null || city.isEmpty()) {
            //TODO Throw Exception Invalid data
        }

        return weatherCache.getOrDefault(city.toLowerCase().trim(), fetchWeatherFromExternalApi(city));
    }

    public void addWeather(Weather weather) {
        weatherCache.put(weather.getCity().toLowerCase().trim(), weather);
    }

    public void removeWeather(String city) {
        weatherCache.remove(city.toLowerCase().trim());
    }

    public List<Weather> getAllWeatherData() {
        return new ArrayList<>(weatherCache.values());
    }
}
