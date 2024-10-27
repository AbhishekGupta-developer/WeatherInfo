package com.myorganisation.weatherinfo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeatherResponse {

    private String pincode;

    private String name;
    private double lat;
    private double lon;
    private String country;

    private LocalDate date;

    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;

    private String base;

    private double temp;
    private double feelsLike;
    private double tempMin;
    private double tempMax;
    private int pressure;
    private int humidity;
    private int seaLevel;
    private int grndLevel;

    private int visibility;

    private double windSpeed;
    private int windDeg;
    private double windGust;

    private int cloudiness;

    private long sunrise;
    private long sunset;

    private int timezone;
}
