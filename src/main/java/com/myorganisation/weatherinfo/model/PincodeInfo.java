package com.myorganisation.weatherinfo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PincodeInfo {

    @Id
    private String pincode;

    private String name;
    private double lat;
    private double lon;
    private String country;
}
