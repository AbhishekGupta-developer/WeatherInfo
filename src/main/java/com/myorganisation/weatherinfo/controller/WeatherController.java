package com.myorganisation.weatherinfo.controller;

import com.myorganisation.weatherinfo.model.WeatherInfo;
import com.myorganisation.weatherinfo.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherInfo getWeather(@RequestParam String pincode, @RequestParam String for_date) {
        LocalDate date = LocalDate.parse(for_date);
        return weatherService.getWeather(pincode, date);
    }
}
