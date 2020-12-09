package br.com.jofofe.campanha.entidades;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "TIME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class Time extends BaseEntidade {

    @Id
    @Column(name = "IDTIME")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOMETIME", nullable = false)
    private String nomeTime;

}
