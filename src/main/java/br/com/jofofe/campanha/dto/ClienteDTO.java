package br.com.jofofe.campanha.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO extends BaseDTO {

    private Integer id;

    private String email;

    private String nomeCompleto;

    private Date dataNascimento;

    private TimeDTO time;

}