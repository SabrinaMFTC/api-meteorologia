package br.mackenzie.mackleaps.apimeteorologia.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para representar um relatório meteorológico.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    /**
     * Identificador único do relatório.
     */
    private Long id;

    /**
     * Nome da cidade onde o relatório foi gerado.
     */
    private String city;

    /**
     * Temperatura medida (em graus Celsius).
     */
    private double temperature;

    /**
     * Umidade relativa do ar (em porcentagem).
     */
    private double humidity;

}
