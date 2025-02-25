package br.mackenzie.mackleaps.apimeteorologia.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;

/**
 * Classe de configuração para o cliente InfluxDB.
 * 
 * Esta classe define e fornece um bean para a conexão com o banco de dados
 * InfluxDB, utilizando valores configuráveis via propriedades do Spring Boot.
 * 
 * <p>
 * Os seguintes parâmetros são injetados via {@code application.properties} ou
 * {@code application.yml}:
 * </p>
 * <ul>
 * <li>{@code influxdb.url} - URL do servidor InfluxDB</li>
 * <li>{@code influxdb.token} - Token de autenticação do InfluxDB</li>
 * <li>{@code influxdb.org} - Organização utilizada no InfluxDB</li>
 * <li>{@code influxdb.bucket} - Bucket para armazenar os dados</li>
 * </ul>
 * 
 * Exemplo de configuração no {@code application.properties}:
 * 
 * <pre>
 * influxdb.url=http://localhost:8086
 * influxdb.token=meu-token-aqui
 * influxdb.org=meu-org
 * influxdb.bucket=meu-bucket
 * </pre>
 * 
 * @author Sabrina Midori
 * @version 1.0
 */
@Configuration
public class InfluxDBConfig {

    /**
     * URL do servidor InfluxDB, injetada a partir das configurações da aplicação.
     */
    @Value("${influxdb.url}")
    private String influxUrl;

    /**
     * Token de autenticação do InfluxDB, necessário para conexão.
     */
    @Value("${influxdb.token}")
    private String influxToken;

    /**
     * Organização utilizada dentro do InfluxDB.
     */
    @Value("${influxdb.org}")
    private String influxOrg;

    /**
     * Bucket onde os dados serão armazenados no InfluxDB.
     */
    @Value("${influxdb.bucket}")
    private String influxBucket;

    /**
     * Cria e fornece um bean para o cliente InfluxDB.
     * 
     * @return Uma instância configurada de {@link InfluxDBClient}
     */
    @Bean
    public InfluxDBClient influxDBClient() {

        return InfluxDBClientFactory.create(influxUrl, influxToken.toCharArray(), influxOrg, influxBucket);
    }

}
