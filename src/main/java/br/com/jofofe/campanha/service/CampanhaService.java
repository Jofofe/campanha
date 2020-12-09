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
        validarTimeInformado(campanha);
        do {
            campanhasMesmaVigencia = repository.findByDataFim(dataFimAtual);
            dataFimAtual = Utils.adicionaQuantidadeDiasData(dataFimAtual, 1);
            aumentarDataVigenciaCampanhas(campanhasMesmaVigencia, campanhasAlteradas, dataFimAtual);
        } while (nonNull(campanhasMesmaVigencia) && !campanhasMesmaVigencia.isEmpty());
        salvarCampanhas(campanha, campanhasAlteradas);
    }

    private void validarTimeInformado(Campanha campanha) {
        timeRepository.findByIdAndNomeTime(campanha.getTime().getId(), campanha.getTime().getNomeTime())
                .orElseThrow(TimeNaoEncontradoException::new);
    }

    private void aumentarDataVigenciaCampanhas(List<Campanha> campanhasMesmaVigencia, List<Campanha> campanhasAlteradas, Date dataFimAtual) {
        for(Campanha campanhaMesmaVigencia : campanhasMesmaVigencia) {
            campanhasAlteradas.add(Campanha.builder()
                    .id(campanhaMesmaVigencia.getId())
                    .nomeCampanha(campanhaMesmaVigencia.getNomeCampanha())
                    .time(campanhaMesmaVigencia.getTime())
                    .dataInicio(campanhaMesmaVigencia.getDataInicio())
                    .dataFim(dataFimAtual)
                    .diasProrrogracaoVigencia(campanhaMesmaVigencia.getDiasProrrogracaoVigencia()+1)
                    .build()
            );
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
        if(campanha.getDataFim().compareTo(dataAtual) < 0) {
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
        return campanhas.stream().map(this::montaCampanhaConsulta).collect(Collectors.toList());
    }

    @Transactional
    public void alterarCampanha(Campanha campanha) {
        validarTimeInformado(campanha);
        Campanha campanhaOriginal = repository.findById(campanha.getId())
                .orElseThrow(CampanhaNaoEncontradaException::new);
        campanha.setDiasProrrogracaoVigencia(campanhaOriginal.getDiasProrrogracaoVigencia());
        repository.save(campanha);
    }

    @Transactional
    public void deletarCampanha(Integer id) {
        Campanha campanha = repository.findById(id).orElseThrow(CampanhaNaoEncontradaException::new);
        repository.delete(campanha);
    }
}
