package br.com.jofofe.campanha.repository;

import br.com.jofofe.campanha.entidades.Campanha;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CampanhaRepository extends CrudRepository<Campanha, Integer> {

    List<Campanha> findByDataFim(Date dataFim);

    @Query("SELECT c FROM Campanha c WHERE c.diasProrrogracaoVigencia != 0 " +
            "AND c.dataFim >= :dataAtual")
    List<Campanha> findCampanhaComVigenciaProrrogada(@Param("dataAtual") Date dataAtual);

    @Query("SELECT c FROM Campanha c join c.time t WHERE t.id = :idTime " +
            "AND :dataAtual BETWEEN c.dataInicio AND c.dataFim")
    List<Campanha> findTimePorId(@Param("idTime") Integer idTime, Date dataAtual);

    @Query("SELECT c FROM Campanha c WHERE c.id not in (:campanhas) " +
            "AND :dataAtual BETWEEN c.dataInicio AND c.dataFim")
    List<Campanha> findCampanhasNaoAtribuidas(@Param("campanhas") List<Integer> campanhas, Date dataAtual);
}
