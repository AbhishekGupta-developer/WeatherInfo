package com.myorganisation.weatherinfo.service;

import com.myorganisation.weatherinfo.dto.GeoApiResponse;
import com.myorganisation.weatherinfo.dto.WeatherApiResponse;
import com.myorganisation.weatherinfo.model.WeatherInfo;
import com.myorganisation.weatherinfo.repository.WeatherInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherInfoRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openweather.geo.endpoint}")
    private String geoEndpoint;

    @Value("${openweather.weather.endpoint}")
    private String weatherEndpoint;

    @Value("${openweather.api.key}")
    private String apiKey;

    public WeatherServiceImpl(WeatherInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public WeatherInfo getWeather(String pincode, LocalDate date) {

        // Check if the weather data exists in the DB
        System.out.println("Checking DB");
        Optional<WeatherInfo> existingData = repository.findByPincodeAndDate(pincode, date);
        if (existingData.isPresent()) {
            return existingData.get();
        }

        // Fetch latitude and longitude from Geocoding API
        System.out.println("Calling Geo API");
        String formattedGeoUrl = geoEndpoint.replace("{pincode}", pincode)
                .replace("{apikey}", apiKey);
        GeoApiResponse geoApiResponse = restTemplate.getForObject(formattedGeoUrl, GeoApiResponse.class);
        double lat = geoApiResponse.getLat();
        double lon = geoApiResponse.getLon();

        // Fetch weather information from OpenWeatherMap API
        System.out.println("Calling Weather API");
        String formattedWeatherUrl = weatherEndpoint.replace("{lat}", String.valueOf(lat))
                .replace("{lon}", String.valueOf(lon))
                .replace("{apikey}", apiKey);
        WeatherApiResponse weatherApiResponse = restTemplate.getForObject(formattedWeatherUrl, WeatherApiResponse.class);

        // Create and save a new WeatherInfo record
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setPincode(pincode);
        weatherInfo.setLatitude(lat);
        weatherInfo.setLongitude(lon);
        weatherInfo.setDate(date);
        weatherInfo.setWeatherDescription(weatherApiResponse.getWeather()[0].getDescription());
        weatherInfo.setTemperature(weatherApiResponse.getMain().getTemp());

        return repository.save(weatherInfo);
    }
}
