package com.myorganisation.weatherinfo.dto;

import lombok.Data;

@Data
public class WeatherApiResponse {
    private Weather[] weather;
    private Main main;

    @Data
    public static class Weather {
        private String description;
    }

    @Data
    public static class Main {
        private double temp;
    }
}
