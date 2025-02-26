package br.mackenzie.mackleaps.apimeteorologia.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private String station;
    private double windSpeed;
    private double windDirection;
    private double temperature;
    private double humidityRel;
    private double airPressure;
    private double radiation;
    private double precipitation;
    private double leafMoistening;
    private double tensiometer;
}

