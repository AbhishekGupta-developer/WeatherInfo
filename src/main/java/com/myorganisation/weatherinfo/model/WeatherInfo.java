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
    private double latitude;
    private double longitude;
    private LocalDate date;
    private String weatherDescription;
    private double temperature;
}
