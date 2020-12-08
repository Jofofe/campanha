package br.com.jofofe.campanha.repository;

import br.com.jofofe.campanha.entidades.Campanha;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CampanhaRepository extends CrudRepository<Campanha, Integer> {

    List<Campanha> findByDataInicioDataFim(Date dataInicio, Date dataFim);

    List<Campanha> findByDataFim(Date dataFim);

    @Query("SELECT c FROM Campanha c WHERE c.diasProrrogracaoVigencia != 0 " +
            "AND :dataAtual BETWEEN c.dataInicio AND c.dataFim")
    List<Campanha> findCampanhaComVigenciaProrrogada(@Param("dataAtual") Date dataAtual);
}
