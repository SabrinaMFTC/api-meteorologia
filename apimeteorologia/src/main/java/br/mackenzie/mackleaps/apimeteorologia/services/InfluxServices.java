package br.mackenzie.mackleaps.apimeteorologia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.write.Point;

import br.mackenzie.mackleaps.apimeteorologia.dtos.ReportDTO;

/**
 * Serviço responsável por gerenciar a escrita de dados meteorológicos no
 * InfluxDB.
 * <p>
 * Essa classe utiliza o {@link InfluxDBClient} para criar e enviar pontos de
 * dados
 * para o InfluxDB. O método {@link #createPoint(ReportDTO)} converte um objeto
 * {@link ReportDTO} em um ponto de dados e o envia para o banco de dados.
 * </p>
 * 
 * <p>
 * Exemplo de uso:
 * </p>
 * 
 * <pre>
 * ReportDTO report = new ReportDTO(1L, "São Paulo", 25.0, 70.0);
 * influxServices.createPoint(report);
 * </pre>
 * 
 * @author Sabrina Midori
 * @version 1.0
 */
@Service
public class InfluxServices {

    private final InfluxDBClient influxDBClient;

    /**
     * Construtor que injeta o cliente do InfluxDB.
     *
     * @param influxDBClient instância do InfluxDBClient utilizada para a
     *                       comunicação com o banco de dados
     */
    @Autowired
    public InfluxServices(InfluxDBClient influxDBClient) {
        this.influxDBClient = influxDBClient;
    }

    /**
     * Cria um ponto de dados no InfluxDB com base nos dados do relatório fornecido.
     * <p>
     * O ponto é criado com a medição <i>weather_report</i> e inclui:
     * <ul>
     * <li>Tag <b>city</b>: valor obtido por {@link ReportDTO#getCity()}</li>
     * <li>Campo <b>temperature</b>: valor obtido por
     * {@link ReportDTO#getTemperature()}</li>
     * <li>Campo <b>humidity</b>: valor obtido por
     * {@link ReportDTO#getHumidity()}</li>
     * </ul>
     * </p>
     *
     * @param report objeto contendo os dados do relatório meteorológico
     */
    public void createPoint(ReportDTO report) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        Point point = Point.measurement("weather_report")
                .addTag("city", report.getCity())
                .addField("temperature", report.getTemperature())
                .addField("humidity", report.getHumidity());

        writeApi.writePoint(point);
    }

}
