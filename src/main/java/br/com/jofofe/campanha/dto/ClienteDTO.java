package br.com.jofofe.campanha.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO extends BaseDTO {

    private Integer id;

    @Email(message = "{email.valid}")
    private String email;

    @NotBlank(message = "{nomeCompleto.not.blank}")
    private String nomeCompleto;

    @NotNull(message = "{dataNascimento.not.null}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    @NotNull(message = "{time.not.null}")
    private TimeDTO time;

}