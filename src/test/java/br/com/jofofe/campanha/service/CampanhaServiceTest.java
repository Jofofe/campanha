package br.com.jofofe.campanha.service;

import br.com.jofofe.campanha.CampanhaApplication;
import br.com.jofofe.campanha.dto.CampanhaConsultaDTO;
import br.com.jofofe.campanha.entidades.Campanha;
import br.com.jofofe.campanha.entidades.Time;
import br.com.jofofe.campanha.exception.CampanhaNaoEncontradaException;
import br.com.jofofe.campanha.exception.CampanhaSemIdentificadorException;
import br.com.jofofe.campanha.repository.CampanhaRepository;
import br.com.jofofe.campanha.repository.TimeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {CampanhaApplication.class})
public class CampanhaServiceTest {

    @InjectMocks
    CampanhaService campanhaService;

    @Mock
    private CampanhaRepository repository;

    @Mock
    private TimeRepository timeRepository;

    @Test
    public void testarIncluiCampanha() {
        Mockito.when(timeRepository.findByIdAndNomeTime(anyInt(), anyString())).thenReturn(Optional.of(criarTime()));
        Mockito.when(repository.findByDataFim(any())).thenReturn(new ArrayList<>());
        campanhaService.incluirCampanha(criarCampanha());
        verify(repository, atLeastOnce()).save(any(Campanha.class));
    }

    @Test
    public void testarBuscarCampanha() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(criarCampanhaGaloMaluco()));
        CampanhaConsultaDTO campanha = campanhaService.buscarCampanha(1);
        Assert.assertNotNull(campanha);
    }

    @Test
    public void testarBuscarCampanhasComDataVigenciaProrrogada() {
        Mockito.when(repository.findCampanhaComVigenciaProrrogada(any())).thenReturn(Arrays.asList(criarCampanha()));
        List<CampanhaConsultaDTO> campanhas = campanhaService.buscarCampanhasComDataVigenciaProrrogada();
        Assert.assertNotNull(campanhas);
    }

    @Test
    public void testarAlterarCampanha() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(criarCampanha()));
        Mockito.when(timeRepository.findByIdAndNomeTime(anyInt(), anyString())).thenReturn(Optional.of(criarTime()));
        campanhaService.alterarCampanha(criarCampanha());
        verify(repository, atLeastOnce()).save(any(Campanha.class));
    }

    @Test(expected = CampanhaSemIdentificadorException.class)
    public void testarAlterarCampanhaSemId() {
        Mockito.when(timeRepository.findByIdAndNomeTime(anyInt(), anyString())).thenReturn(Optional.of(criarTime()));
        campanhaService.alterarCampanha(criarCampanhaSemIdentificados());
    }

    @Test(expected = CampanhaNaoEncontradaException.class)
    public void testarAlterarCampanhaNaoExistente() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());
        Mockito.when(timeRepository.findByIdAndNomeTime(anyInt(), anyString())).thenReturn(Optional.of(criarTime()));
        campanhaService.alterarCampanha(criarCampanha());
    }

    @Test
    public void testarExcluirCampanha() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(criarCampanha()));
        campanhaService.deletarCampanha(1);
        verify(repository, atLeastOnce()).delete(any(Campanha.class));
    }

    @Test(expected = CampanhaNaoEncontradaException.class)
    public void testarExcluirCampanhaNaoExistente() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());
        campanhaService.deletarCampanha(1);
    }

    private Campanha criarCampanha() {
        return Campanha.builder().id(1)
                .nomeCampanha("GALO DOIDO DMAIS")
                .time(criarTime())
                .dataInicio(new Date())
                .dataFim(new Date())
                .diasProrrogracaoVigencia(0)
                .build();
    }

    private Campanha criarCampanhaSemIdentificados() {
        return Campanha.builder()
                .nomeCampanha("GALO DOIDO DMAIS")
                .time(criarTime())
                .dataInicio(new Date())
                .dataFim(new Date())
                .diasProrrogracaoVigencia(0)
                .build();
    }

    private Campanha criarCampanhaGaloMaluco() {
        return Campanha.builder().id(2)
                .nomeCampanha("GALO MALUCO DMAIS")
                .time(criarTime())
                .dataInicio(new Date(2020, 12, 05))
                .dataFim(new Date(2020, 12, 18))
                .diasProrrogracaoVigencia(0)
                .build();
    }

    private Time criarTime() {
        return Time.builder().id(1).nomeTime("GALO").build();
    }

}