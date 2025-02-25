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

/**
 * Controller responsável por expor os endpoints relacionados aos dados
 * meteorológicos.
 * <p>
 * Este controller disponibiliza um endpoint para receber dados meteorológicos
 * em formato JSON
 * e, a partir destes dados, criar um ponto de dados no InfluxDB.
 * </p>
 * <p>
 * O endpoint é mapeado na rota <code>/weather</code> e espera receber
 * requisições HTTP POST.
 * </p>
 * 
 * <p>
 * Exemplo de JSON para envio via Postman:
 * </p>
 * 
 * <pre>
 * {
 *   "id": 1,
 *   "city": "São Paulo",
 *   "temperature": 25.0,
 *   "humidity": 70.0,
 *   "reportDate": "2023-08-15T15:30:00"
 * }
 * </pre>
 * 
 * <p>
 * Ao receber a requisição, o controller delega a criação do ponto de dados para
 * o serviço
 * {@link InfluxServices}.
 * </p>
 *
 * @see ReportDTO
 * @see InfluxServices
 * @author Sabrina Midori
 * @version 1.0
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

    /**
     * Serviço responsável pela interação com o InfluxDB para a criação dos pontos
     * de dados.
     */
    private InfluxServices influxServices;

    /**
     * Logger para registro de eventos e erros na execução dos endpoints.
     */
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    /**
     * Construtor com injeção de dependência do {@link InfluxServices}.
     * 
     * @param influxServices instância do serviço que realiza operações no InfluxDB.
     */
    @Autowired
    public WeatherController(InfluxServices influxServices) {
        this.influxServices = influxServices;
    }

    /**
     * Endpoint para criação de um ponto de dados no InfluxDB a partir do ReportDto.
     * 
     * @param report Objeto com os dados do relatório meteorológico.
     * @return ResponseEntity com status CREATED em caso de sucesso ou
     *         INTERNAL_SERVER_ERROR em caso de falha.
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> postData(@RequestBody ReportDTO report) {
        try {
            influxServices.createPoint(report);
            logger.info("Registro criado com sucesso!");
            return new ResponseEntity<>("Ponto criado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {

            logger.error("Falha ao inserir dados no banco!: {}", e.getMessage());
            return new ResponseEntity<>("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
