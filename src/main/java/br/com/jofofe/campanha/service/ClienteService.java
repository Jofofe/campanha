package br.com.jofofe.campanha.service;

import br.com.jofofe.campanha.entidades.Campanha;
import br.com.jofofe.campanha.entidades.Cliente;
import br.com.jofofe.campanha.exception.ClienteJaCadastradoException;
import br.com.jofofe.campanha.repository.CampanhaRepository;
import br.com.jofofe.campanha.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


@Slf4j
@Service
public class ClienteService extends AbstractService<Cliente, Integer, ClienteRepository>  {

    private final CampanhaRepository campanhaRepository;

    ClienteService(ClienteRepository repository, CampanhaRepository campanhaRepository) {
        super(repository);
        this.campanhaRepository = campanhaRepository;
    }

    public List<Campanha> incluirCliente(Cliente cliente) {
        Optional<Cliente> clienteBusca = repository.findByEmail(cliente.getEmail());
        equipararClienteEnviadoRequisicao(cliente, clienteBusca);
        Date dataAtual = new Date();
        List<Campanha> campanhas = null;
        if(!clienteBusca.isPresent()) {
            campanhas = campanhaRepository.findTimePorId(cliente.getTime().getId(), dataAtual);
            cliente.setCampanhas(new ArrayList<>());
        } else {
            campanhas = campanhaRepository.findCampanhasNaoAtribuidas(clienteBusca.get().getCampanhas().stream()
                    .map(Campanha::getId).collect(Collectors.toList()), dataAtual);
            if (isNull(campanhas) || campanhas.isEmpty()) {
                throw new ClienteJaCadastradoException();
            }
        }
        cliente.getCampanhas().addAll(campanhas);
        repository.save(cliente);
        return campanhas;
    }

    private void equipararClienteEnviadoRequisicao(Cliente cliente, Optional<Cliente> clienteBusca) {
        cliente.setCampanhas(clienteBusca.get().getCampanhas());
    }

}
