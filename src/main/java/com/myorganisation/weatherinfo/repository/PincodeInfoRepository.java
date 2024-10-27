package com.myorganisation.weatherinfo.repository;

import com.myorganisation.weatherinfo.model.PincodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PincodeInfoRepository extends JpaRepository<PincodeInfo, String> {
}
