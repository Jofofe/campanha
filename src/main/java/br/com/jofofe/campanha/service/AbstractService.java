package br.com.jofofe.campanha.service;

import br.com.jofofe.campanha.entidades.BaseEntidade;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public abstract class AbstractService<E extends BaseEntidade, I extends Serializable, R extends CrudRepository<E, I>> {

    protected final R repository;

    AbstractService(R repository) {
        super();
        this.repository = repository;
    }

}
