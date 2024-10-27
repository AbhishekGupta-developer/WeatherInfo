package com.myorganisation.weatherinfo.repository;

import com.myorganisation.weatherinfo.model.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {
    Optional<WeatherInfo> findByPincodeAndDate(String pincode, LocalDate date);
}
