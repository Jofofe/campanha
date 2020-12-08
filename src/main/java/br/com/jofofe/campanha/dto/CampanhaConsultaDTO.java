package br.com.jofofe.campanha.dto;

import br.com.jofofe.campanha.entidades.Time;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampanhaConsultaDTO extends BaseDTO {

    private String nomeCampanha;

    private Integer idTime;

    private String dataVigencia;

    private Integer diasProrrogracaoVigencia;

}
