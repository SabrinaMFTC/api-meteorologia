package br.mackenzie.mackleaps.apimeteorologia.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.WriteApiBlocking;

/*
 * Classe de configuração para o cliente InfluxDB. Esta classe define e fornece um bean para a conexão com o banco de dados
 * InfluxDB, utilizando valores configuráveis via propriedades do Spring Boot.
 * Os seguintes parâmetros são injetados via {@code application.properties} ou {@code application.yml}:
 * Exemplo de configuração no {@code application.properties}:
 * influxdb.url=http://localhost:8086
 * influxdb.token=meu-token-aqui
 * influxdb.org=meu-org
 * influxdb.bucket=meu-bucket
 */

@Configuration
public class InfluxDBConfig {

    /*
     * URL do servidor InfluxDB, injetada a partir das configurações da aplicação.
     */
    @Value("${influxdb.url}")
    private String influxUrl;

    /* Token de autenticação do InfluxDB, necessário para conexão. */
    @Value("${influxdb.token}")
    private String influxToken;

    /* Organização utilizada dentro do InfluxDB. */
    @Value("${influxdb.org}")
    private String influxOrg;

    /* Bucket onde os dados serão armazenados no InfluxDB. */
    @Value("${influxdb.bucket}")
    private String influxBucket;

    /*
     * Cria e fornece um bean para o cliente InfluxDB.
     * 
     * @return Uma instância configurada de {@link InfluxDBClient}
     */
    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(influxUrl, influxToken.toCharArray(), influxOrg);
    }

  

}
