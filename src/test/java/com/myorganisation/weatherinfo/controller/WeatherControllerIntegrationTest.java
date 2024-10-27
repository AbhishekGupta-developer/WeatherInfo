package com.myorganisation.weatherinfo.controller;

import com.myorganisation.weatherinfo.repository.PincodeInfoRepository;
import com.myorganisation.weatherinfo.repository.WeatherInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PincodeInfoRepository pincodeInfoRepository;

    @Autowired
    private WeatherInfoRepository weatherInfoRepository;

    @Test
    public void testGetWeather() throws Exception {
        String pincode = "226301";
        String forDate = "2024-10-25";

        mockMvc.perform(get("/api/weather")
                        .param("pincode", pincode)
                        .param("for_date", forDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pincode").value("226301"))
                .andExpect(jsonPath("$.name").value("Lucknow"))
                .andExpect(jsonPath("$.lat").value("26.8416"))
                .andExpect(jsonPath("$.lon").value("80.9067"))
                .andExpect(jsonPath("$.country").value("IN"))
                .andExpect(jsonPath("$.date").value("2024-10-25"))
                .andExpect(jsonPath("$.base").value("stations"))
                .andExpect(jsonPath("$.timezone").value("19800"));
    }
}
