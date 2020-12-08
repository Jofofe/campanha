package br.com.jofofe.campanha.entidades;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CAMPANHA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class Campanha extends BaseEntidade {

    @Id
    @Column(name = "IDCAMPANHA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOMECAMPANHA", nullable = false)
    private String nomeCampanha;

    @ManyToOne
    @JoinColumn(name = "IDTIME", nullable = false)
    private Time time;

    @Column(name = "DATAINICIO", nullable = false)
    private Date dataInicio;

    @Column(name = "DATAFIM", nullable = false)
    private Date dataFim;

    @Column(name = "DIASVIGENCIAPRORROGADA", columnDefinition = "int default 0")
    private Integer diasProrrogracaoVigencia;

}
