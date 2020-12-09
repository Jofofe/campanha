package br.com.jofofe.campanha.dto;

import br.com.jofofe.campanha.entidades.BaseEntidade;
import br.com.jofofe.campanha.entidades.Time;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampanhaDTO extends BaseDTO {

    private Integer id;

    @NotBlank(message = "{nomeCampanha.not.blank}")
    private String nomeCampanha;

    @NotNull(message = "{time.not.null}")
    private TimeDTO time;

    @NotNull(message = "{dataInicio.not.null}")
    private Date dataInicio;

    @NotNull(message = "{dataFim.not.null}")
    private Date dataFim;

}
