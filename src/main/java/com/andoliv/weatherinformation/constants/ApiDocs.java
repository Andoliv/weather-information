package com.andoliv.weatherinformation.constants;

public class ApiDocs {

    /**
     * Default
     */
    public static final String DEFAULT_500_RESPONSE = "Something was wrong";

    /**
     * Weather Information Service
     */
    public static final String WEATHER_CONTROLLER_API_DESC = "Weather Information Service";

    public static final String ADD_WEATHER_201_RESPONSE = "Weather Service successfully added to cache list";
    public static final String GET_WEATHER_LIST_200_RESPONSE = "Weather Service list successfully retrieved";
    public static final String GET_WEATHER_200_RESPONSE = "Weather Service by city successfully retrieved";
    public static final String GET_WEATHER_404_RESPONSE = "Weather Service by city not found";
    public static final String INVALID_REQUEST_BODY_400_RESPONSE = "City cannot be null or empty";

    public static final String GET_WEATHERS_OPERATION = "Get Weather Service cached list";
    public static final String GET_WEATHER_BY_CITY_OPERATION = "Get Weather Service by city name persisted on cache list";
    public static final String ADD_WEATHER_OPERATION = "Add Weather Service to cache list";
    public static final String DELETE_WEATHER_OPERATION = "Delete Weather Service from cache list";
}
