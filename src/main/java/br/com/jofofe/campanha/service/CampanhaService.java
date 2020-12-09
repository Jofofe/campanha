package br.com.jofofe.campanha.service;

import br.com.jofofe.campanha.dto.CampanhaConsultaDTO;
import br.com.jofofe.campanha.entidades.Campanha;
import br.com.jofofe.campanha.entidades.Time;
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
        equipararTimeEnviadoRequisicao(campanha);
        do {
            campanhasMesmaVigencia = repository.findByDataFim(dataFimAtual);
            dataFimAtual = Utils.adicionaQuantidadeDiasData(campanha.getDataFim(), 1);
            aumentarDataVigenciaCampanhas(campanhasMesmaVigencia, campanhasAlteradas, dataFimAtual);
        } while (nonNull(campanhasMesmaVigencia) && !campanhasMesmaVigencia.isEmpty());
        salvarCampanhas(campanha, campanhasAlteradas);
    }

    private void equipararTimeEnviadoRequisicao(Campanha campanha) {
        Time time = timeRepository.findById(campanha.getTime().getId())
                .orElseThrow(TimeNaoEncontradoException::new);
        campanha.setTime(time);
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
        return campanhas.stream().map(this::montaCampanhaConsulta).collect(Collectors.toList());
    }

    @Transactional
    public void alterarCampanha(Campanha campanha) {
        equipararInformacoesCampanha(campanha);
        repository.save(campanha);
    }

    private void equipararInformacoesCampanha(Campanha campanha) {
        equipararTimeEnviadoRequisicao(campanha);
        Campanha campanhaOriginal = repository.findById(campanha.getId())
                .orElseThrow(CampanhaNaoEncontradaException::new);
        campanha.setDiasProrrogracaoVigencia(campanhaOriginal.getDiasProrrogracaoVigencia());
    }

    @Transactional
    public void deletarCampanha(Integer id) {
        Campanha campanha = repository.findById(id).orElseThrow(CampanhaNaoEncontradaException::new);
        repository.delete(campanha);
    }
}
