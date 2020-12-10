package br.com.jofofe.campanha.entidades;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Entity
@Table(name = "CLIENTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class Cliente extends BaseEntidade {

    @Id
    @Column(name = "IDCLIENTE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "NOMECOMPLETO", nullable = false)
    private String nomeCompleto;

    @Column(name = "DATANASCIMENTO", nullable = false)
    private Date dataNascimento;

    @ManyToOne
    @JoinColumn(name = "IDTIME", nullable = false)
    private Time time;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "CLIENTE_CAMPANHA", joinColumns = {
            @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE")
    }, inverseJoinColumns = {
            @JoinColumn(name = "IDCAMPANHA", referencedColumnName = "IDCAMPANHA")
    })
    private List<Campanha> campanhas;

    public void addCampanha(Campanha campanha) {
        if(nonNull(campanha)) {
            if(isNull(this.campanhas)) {
                this.campanhas = new ArrayList<>();
            }
            this.campanhas.add(campanha);
        }
    }

    public void addCampanhas(List<Campanha> campanhas) {
        if(nonNull(campanhas) && !campanhas.isEmpty()) {
            if(isNull(this.campanhas)) {
                this.campanhas = new ArrayList<>();
            }
            this.campanhas.addAll(campanhas);
        }
    }

}

