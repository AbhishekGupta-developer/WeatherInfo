package com.myorganisation.weatherinfo.dto;

import lombok.Data;

@Data
public class OpenWeatherMapWeatherApiResponse {

    private Coord coord;
    private Weather[] weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;
    private int timezone;
    private String name;

    @Data
    public static class Coord {
        private double lon;
        private double lat;
    }

    @Data
    public static class Weather {
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
        private int sea_level;
        private int grnd_level;
    }

    @Data
    public static class Wind {
        private double speed;
        private int deg;
        private double gust;
    }

    @Data
    public static class Clouds {
        private int all;
    }

    @Data
    public static class Sys {
        private String country;
        private long sunrise;
        private long sunset;
    }

}
