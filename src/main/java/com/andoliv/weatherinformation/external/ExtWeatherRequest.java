package com.andoliv.weatherinformation.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtWeatherRequest {

    @JsonProperty(required = true)
    public String city;

    public ExtWeatherRequest(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
