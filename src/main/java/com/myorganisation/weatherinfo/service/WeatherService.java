package com.myorganisation.weatherinfo.service;

import com.myorganisation.weatherinfo.model.WeatherInfo;

import java.time.LocalDate;

public interface WeatherService {
    WeatherInfo getWeather(String pincode, LocalDate date);
}
