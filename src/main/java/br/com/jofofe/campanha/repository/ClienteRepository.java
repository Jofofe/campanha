package br.com.jofofe.campanha.repository;

import br.com.jofofe.campanha.entidades.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

    Optional<Cliente> findByEmail(String email);

}
