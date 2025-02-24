package br.mackenzie.mackleaps.apimeteorologia.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.mackenzie.mackleaps.apimeteorologia.services.InfluxServices;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private InfluxServices influxServices;

    public WeatherController() {
        this.influxServices = new InfluxServices();
    }

    @PostMapping
    public String postData(@RequestBody String json) throws IOException {
        influxServices.CreateBucket(json);
        influxServices.WriteOnBucket(json);

        return "Bucket creation and data written successfully.";
    }
}
