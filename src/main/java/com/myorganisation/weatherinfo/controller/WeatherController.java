package com.myorganisation.weatherinfo.controller;

import com.myorganisation.weatherinfo.model.WeatherInfo;
import com.myorganisation.weatherinfo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherInfo> getWeather(@RequestParam String pincode, @RequestParam String for_date) {
        LocalDate date = LocalDate.parse(for_date);
        return new ResponseEntity<>(weatherService.getWeather(pincode, date), HttpStatus.OK);
    }
}
