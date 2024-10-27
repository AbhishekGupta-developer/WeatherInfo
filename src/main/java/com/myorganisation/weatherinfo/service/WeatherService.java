package com.myorganisation.weatherinfo.service;

import com.myorganisation.weatherinfo.dto.WeatherResponse;

import java.time.LocalDate;

public interface WeatherService {
    WeatherResponse getWeather(String pincode, LocalDate date);
}
