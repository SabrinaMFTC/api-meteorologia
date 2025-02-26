package br.mackenzie.mackleaps.apimeteorologia.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.mackenzie.mackleaps.apimeteorologia.dtos.ReportDTO;
import br.mackenzie.mackleaps.apimeteorologia.services.InfluxServices;

/*
 * Controller responsável por expor os endpoints relacionados aos dados meteorológicos. Este controller disponibiliza um endpoint para receber dados meteorológicos em formato JSON e, a partir destes dados, criar um ponto de dados no InfluxDB.
 * 
 * O endpoint é mapeado na rota <code>/weather</code> e espera receber requisições HTTP POST.
 * 
 * Exemplo de JSON para envio via Postman:
 * {
 *   "id": 1,
 *   "city": "São Paulo",
 *   "temperature": 25.0,
 *   "humidity": 70.0,
 *   "reportDate": "2023-08-15T15:30:00"
 * }
 * Ao receber a requisição, o controller delega a criação do ponto de dados para o serviço
 * {@link InfluxServices}.
 * @see ReportDTO
 * @see InfluxServices
 */

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private InfluxServices influxServices;
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    public WeatherController(InfluxServices influxServices) {
        this.influxServices = influxServices;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> postData(@RequestBody ReportDTO report) {
        try {
            influxServices.createPoint(report);
            logger.info("Registro criado com sucesso!");
            return new ResponseEntity<>("Ponto criado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Falha ao inserir dados no banco!: {}", e.getMessage());
            return new ResponseEntity<>("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
