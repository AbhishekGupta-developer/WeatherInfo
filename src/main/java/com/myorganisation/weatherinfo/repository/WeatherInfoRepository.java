package com.myorganisation.weatherinfo.repository;

import com.myorganisation.weatherinfo.model.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {
    Optional<WeatherInfo> findByPincodeAndDate(String pincode, LocalDate date);
}
