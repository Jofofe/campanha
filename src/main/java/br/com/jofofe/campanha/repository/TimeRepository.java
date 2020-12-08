package br.com.jofofe.campanha.repository;

import br.com.jofofe.campanha.entidades.Campanha;
import br.com.jofofe.campanha.entidades.Time;
import org.springframework.data.repository.CrudRepository;

public interface TimeRepository extends CrudRepository<Time, Integer> {
}
