package com.myorganisation.weatherinfo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pincode;
    private LocalDate date;

    private double lon;
    private double lat;

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

    private String country;
    private long sunrise;
    private long sunset;

    private int timezone;
    private String name;

}
