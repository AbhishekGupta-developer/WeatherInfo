package com.myorganisation.weatherinfo.service;

import com.myorganisation.weatherinfo.dto.OpenWeatherMapGeoApiResponse;
import com.myorganisation.weatherinfo.dto.OpenWeatherMapWeatherApiResponse;
import com.myorganisation.weatherinfo.model.PincodeInfo;
import com.myorganisation.weatherinfo.model.WeatherInfo;
import com.myorganisation.weatherinfo.repository.PincodeInfoRepository;
import com.myorganisation.weatherinfo.repository.WeatherInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherInfoRepository weatherInfoRepository;

    @Autowired
    private PincodeInfoRepository pincodeInfoRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openweathermap.geo.endpoint}")
    private String geoEndpoint;

    @Value("${openweathermap.weather.endpoint}")
    private String weatherEndpoint;

    @Value("${openweathermap.api.key}")
    private String apiKey;


    Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Override
    public WeatherInfo getWeather(String pincode, LocalDate date) {

        // Check if the weather data exists in the DB
        logger.info("Checking DB for existing weather data");
        Optional<WeatherInfo> existingWeather = weatherInfoRepository.findByPincodeAndDate(pincode, date);
        if(existingWeather.isPresent()) {
            return existingWeather.get();
        }

//        // Check if the given date is the current date (because the OpenWeatherMap API returns the weather data for the current date)
//        LocalDate currentDate = LocalDate.now();
//        if(!date.equals(currentDate)) {
//            throw new RuntimeException("Data not found for the given date and pincode.");
//        }

        // Fetching latitude and longitude for the pincode
        // Try to fetch lat and lon from DB
        logger.info("Checking DB for existing pincode data");
        Optional<PincodeInfo> pincodeInfoOpt = pincodeInfoRepository.findById(pincode);
        double lat, lon;

        if(pincodeInfoOpt.isPresent()) {
            PincodeInfo pincodeInfo = pincodeInfoOpt.get();
            lat = pincodeInfo.getLatitude();
            lon = pincodeInfo.getLongitude();
        } else {
            // Call Geocoding API to get latitude and longitude
            logger.info("Calling Geo API");
            String formattedGeoUrl = geoEndpoint.replace("{pincode}", pincode)
                                                .replace("{apikey}", apiKey);
            OpenWeatherMapGeoApiResponse openWeatherMapGeoApiResponse = restTemplate.getForObject(formattedGeoUrl, OpenWeatherMapGeoApiResponse.class);
            lat = openWeatherMapGeoApiResponse.getLat();
            lon = openWeatherMapGeoApiResponse.getLon();

            // Save the new pincode information in the DB
            PincodeInfo newPincodeInfo = new PincodeInfo();
            newPincodeInfo.setPincode(pincode);
            newPincodeInfo.setLatitude(lat);
            newPincodeInfo.setLongitude(lon);
            pincodeInfoRepository.save(newPincodeInfo);
        }

        // Fetch weather information from OpenWeatherMap API
        logger.info("Calling Weather API");
        String formattedWeatherUrl = weatherEndpoint.replace("{lat}", String.valueOf(lat))
                                                    .replace("{lon}", String.valueOf(lon))
                                                    .replace("{apikey}", apiKey);
        OpenWeatherMapWeatherApiResponse openWeatherMapWeatherApiResponse = restTemplate.getForObject(formattedWeatherUrl, OpenWeatherMapWeatherApiResponse.class);

        // Create and save a new WeatherInfo record
        WeatherInfo weatherInfo = new WeatherInfo();

        weatherInfo.setPincode(pincode);
        weatherInfo.setDate(date);

        weatherInfo.setLon(openWeatherMapWeatherApiResponse.getCoord().getLon());
        weatherInfo.setLat(openWeatherMapWeatherApiResponse.getCoord().getLat());

        weatherInfo.setWeatherMain(openWeatherMapWeatherApiResponse.getWeather()[0].getMain());
        weatherInfo.setWeatherDescription(openWeatherMapWeatherApiResponse.getWeather()[0].getDescription());
        weatherInfo.setWeatherIcon(openWeatherMapWeatherApiResponse.getWeather()[0].getIcon());

        weatherInfo.setBase(openWeatherMapWeatherApiResponse.getBase());

        weatherInfo.setTemp(openWeatherMapWeatherApiResponse.getMain().getTemp());
        weatherInfo.setFeelsLike(openWeatherMapWeatherApiResponse.getMain().getFeels_like());
        weatherInfo.setTempMin(openWeatherMapWeatherApiResponse.getMain().getTemp_min());
        weatherInfo.setTempMax(openWeatherMapWeatherApiResponse.getMain().getTemp_max());
        weatherInfo.setPressure(openWeatherMapWeatherApiResponse.getMain().getPressure());
        weatherInfo.setHumidity(openWeatherMapWeatherApiResponse.getMain().getHumidity());
        weatherInfo.setSeaLevel(openWeatherMapWeatherApiResponse.getMain().getSea_level());
        weatherInfo.setGrndLevel(openWeatherMapWeatherApiResponse.getMain().getGrnd_level());

        weatherInfo.setVisibility(openWeatherMapWeatherApiResponse.getVisibility());

        weatherInfo.setWindSpeed(openWeatherMapWeatherApiResponse.getWind().getSpeed());
        weatherInfo.setWindDeg(openWeatherMapWeatherApiResponse.getWind().getDeg());
        weatherInfo.setWindGust(openWeatherMapWeatherApiResponse.getWind().getGust());

        weatherInfo.setCloudiness(openWeatherMapWeatherApiResponse.getClouds().getAll());

        weatherInfo.setCountry(openWeatherMapWeatherApiResponse.getSys().getCountry());
        weatherInfo.setSunrise(openWeatherMapWeatherApiResponse.getSys().getSunrise());
        weatherInfo.setSunset(openWeatherMapWeatherApiResponse.getSys().getSunset());

        weatherInfo.setTimezone(openWeatherMapWeatherApiResponse.getTimezone());
        weatherInfo.setName(openWeatherMapWeatherApiResponse.getName());

        return weatherInfoRepository.save(weatherInfo);
    }
}
