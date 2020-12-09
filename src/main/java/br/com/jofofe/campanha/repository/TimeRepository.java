package br.com.jofofe.campanha.repository;

import br.com.jofofe.campanha.entidades.Time;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TimeRepository extends CrudRepository<Time, Integer> {

    Optional<Time> findByIdAndNomeTime(Integer id, String nomeTime);

}
