package br.mackenzie.mackleaps.apimeteorologia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.write.Point;

import br.mackenzie.mackleaps.apimeteorologia.dtos.ReportDTO;

@Service
public class InfluxServices {

    private final InfluxDBClient influxDBClient;

    @Autowired
    public InfluxServices(InfluxDBClient influxDBClient) {
        this.influxDBClient = influxDBClient;
    }

    public void createPoint(ReportDTO report) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        Point point = Point.measurement("weather_report")
                .addTag("city", report.getCity())
                .addField("temperature", report.getTemperature())
                .addField("humidity", report.getHumidity());

        writeApi.writePoint(point);
    }

}
