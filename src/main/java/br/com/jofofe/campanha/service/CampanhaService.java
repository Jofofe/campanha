package br.com.jofofe.campanha.service;

import br.com.jofofe.campanha.dto.CampanhaConsultaDTO;
import br.com.jofofe.campanha.entidades.Campanha;
import br.com.jofofe.campanha.exception.CampanhaComVigenciaVencidaException;
import br.com.jofofe.campanha.exception.CampanhaNaoEncontradaException;
import br.com.jofofe.campanha.exception.TimeNaoEncontradoException;
import br.com.jofofe.campanha.repository.CampanhaRepository;
import br.com.jofofe.campanha.repository.TimeRepository;
import br.com.jofofe.campanha.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class CampanhaService extends AbstractService<Campanha, Integer, CampanhaRepository> {

    private final TimeRepository timeRepository;

    CampanhaService(CampanhaRepository repository, TimeRepository timeRepository) {
        super(repository);
        this.timeRepository = timeRepository;
    }

    @Transactional
    public void incluirCampanha(Campanha campanha) {
        List<Campanha> campanhasMesmaVigencia = null;
        List<Campanha> campanhasAlteradas = new ArrayList<>();
        Date dataFimAtual = campanha.getDataFim();
        timeRepository.findById(campanha.getTime().getId()).orElseThrow(TimeNaoEncontradoException::new);
        do {
            campanhasMesmaVigencia = repository.findByDataFim(dataFimAtual);
            dataFimAtual = Utils.adicionaQuantidadeDiasData(campanha.getDataFim(), 1);
            aumentarDataVigenciaCampanhas(campanhasMesmaVigencia, campanhasAlteradas, dataFimAtual);
        } while (nonNull(campanhasMesmaVigencia) && !campanhasMesmaVigencia.isEmpty());
        salvarCampanhas(campanha, campanhasAlteradas);
    }

    private void aumentarDataVigenciaCampanhas(List<Campanha> campanhasMesmaVigencia, List<Campanha> campanhasAlteradas, Date dataFimAtual) {
        for(Campanha campanhaMesmaVigencia : campanhasMesmaVigencia) {
            campanhaMesmaVigencia.setDataFim(dataFimAtual);
            campanhaMesmaVigencia.setDiasProrrogracaoVigencia(campanhaMesmaVigencia.getDiasProrrogracaoVigencia()+1);
            campanhasAlteradas.add(campanhaMesmaVigencia);
        }
    }

    private void salvarCampanhas(Campanha campanha, List<Campanha> campanhasAlteradas) {
        for(Campanha campanhaAlterada : campanhasAlteradas) {
            repository.save(campanhaAlterada);
        }
        repository.save(campanha);
    }

    @Transactional
    public CampanhaConsultaDTO buscarCampanha(Integer id) {
        Campanha campanha = repository.findById(id).orElseThrow(CampanhaNaoEncontradaException::new);
        verificaVigenciaCampanha(campanha);
        return montaCampanhaConsulta(campanha);
    }

    private void verificaVigenciaCampanha(Campanha campanha) {
        Date dataAtual = new Date();
        if(campanha.getDataInicio().before(dataAtual) || campanha.getDataFim().after(dataAtual)) {
            throw new CampanhaComVigenciaVencidaException();
        }
    }

    private CampanhaConsultaDTO montaCampanhaConsulta(Campanha campanha) {
        return CampanhaConsultaDTO.builder()
                .nomeCampanha(campanha.getNomeCampanha())
                .idTime(campanha.getTime().getId())
                .dataVigencia(Utils.getDataVigencia(campanha.getDataInicio(), campanha.getDataFim()))
                .diasProrrogracaoVigencia(campanha.getDiasProrrogracaoVigencia())
                .build();
    }

    @Transactional
    public List<CampanhaConsultaDTO> buscarCampanhasComDataVigenciaProrrogada() {
        List<Campanha> campanhas = repository.findCampanhaComVigenciaProrrogada(new Date());
        return campanhas.stream().map(c -> montaCampanhaConsulta(c)).collect(Collectors.toList());
    }

    @Transactional
    public void alterarCampanha(Campanha campanha) {
        if(repository.findById(campanha.getId()).isPresent()) {
            repository.save(campanha);
        } else {
            throw new CampanhaNaoEncontradaException();
        }
    }

    @Transactional
    public void deletarCampanha(Integer id) {
        Campanha campanha = repository.findById(id).orElseThrow(CampanhaNaoEncontradaException::new);
        repository.delete(campanha);
    }
}
