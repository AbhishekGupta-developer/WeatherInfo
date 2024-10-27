package com.myorganisation.weatherinfo.dto;

import lombok.Data;

@Data
public class OpenWeatherMapGeoApiResponse {
    private String name;
    private double lat;
    private double lon;
    private String country;
}
