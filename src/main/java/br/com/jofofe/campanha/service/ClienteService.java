package br.com.jofofe.campanha.service;

import br.com.jofofe.campanha.entidades.Campanha;
import br.com.jofofe.campanha.entidades.Cliente;
import br.com.jofofe.campanha.exception.ClienteJaCadastradoException;
import br.com.jofofe.campanha.exception.TimeNaoEncontradoException;
import br.com.jofofe.campanha.repository.CampanhaRepository;
import br.com.jofofe.campanha.repository.ClienteRepository;
import br.com.jofofe.campanha.repository.TimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;



@Slf4j
@Service
public class ClienteService extends AbstractService<Cliente, Integer, ClienteRepository>  {

    private final CampanhaRepository campanhaRepository;

    private final TimeRepository timeRepository;


    ClienteService(ClienteRepository repository, CampanhaRepository campanhaRepository, TimeRepository timeRepository) {
        super(repository);
        this.campanhaRepository = campanhaRepository;
        this.timeRepository = timeRepository;
    }

    public List<Campanha> incluirCliente(Cliente cliente) {
        Optional<Cliente> clienteBusca = repository.findByEmail(cliente.getEmail());
        List<Campanha> campanhas = null;
        Date dataAtual = new Date();
        if(!clienteBusca.isPresent()) {
            campanhas = fluxoPrimeiraAssociacao(cliente, dataAtual);
        } else {
            campanhas = fluxoDemaisAssociacoes(clienteBusca.get(), dataAtual);
        }
        return campanhas;
    }

    private void validarTimeInformado(Cliente cliente) {
        timeRepository.findByIdAndNomeTime(cliente.getTime().getId(), cliente.getTime().getNomeTime())
                .orElseThrow(TimeNaoEncontradoException::new);
    }

    private List<Campanha> fluxoPrimeiraAssociacao(Cliente cliente, Date dataAtual) {
        validarTimeInformado(cliente);
        cliente.setCampanhas(campanhaRepository.findTimePorId(cliente.getTime().getId(), dataAtual));
        repository.save(cliente);
        return cliente.getCampanhas();
    }

    private List<Campanha> fluxoDemaisAssociacoes(Cliente clienteBusca, Date dataAtual) {
        List<Campanha> campanhasNovas = null;
        if(nonNull(clienteBusca.getCampanhas()) && !clienteBusca.getCampanhas().isEmpty()) {
            campanhasNovas = campanhaRepository.findCampanhasNaoAtribuidas(
                    clienteBusca.getCampanhas().stream().map(Campanha::getId)
                            .collect(Collectors.toList()), dataAtual);
            if (isNull(campanhasNovas) || campanhasNovas.isEmpty()) {
                throw new ClienteJaCadastradoException();
            }
            clienteBusca.getCampanhas().addAll(campanhasNovas);
        } else {
            campanhasNovas = campanhaRepository.findTimePorId(clienteBusca.getTime().getId(), dataAtual);
            clienteBusca.setCampanhas(campanhasNovas);
        }
        repository.save(clienteBusca);
        return campanhasNovas;
    }

}
