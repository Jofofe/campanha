package br.com.jofofe.campanha.service;

import br.com.jofofe.campanha.CampanhaApplication;
import br.com.jofofe.campanha.entidades.Campanha;
import br.com.jofofe.campanha.entidades.Cliente;
import br.com.jofofe.campanha.entidades.Time;
import br.com.jofofe.campanha.exception.ClienteJaCadastradoException;
import br.com.jofofe.campanha.repository.CampanhaRepository;
import br.com.jofofe.campanha.repository.ClienteRepository;
import br.com.jofofe.campanha.repository.TimeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {CampanhaApplication.class})
public class ClienteServiceTest {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    private ClienteRepository repository;

    @Mock
    private CampanhaRepository campanhaRepository;

    @Mock
    private TimeRepository timeRepository;

    @Test
    public void testarIncluiClienteNovo() {
        Mockito.when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        Mockito.when(timeRepository.findByIdAndNomeTime(anyInt(), anyString())).thenReturn(Optional.of(criarTime()));
        Mockito.when(campanhaRepository.findTimePorId(anyInt(), any())).thenReturn(Arrays.asList(criarCampanhaGaloMaluco()));
        List<Campanha> campanhas = clienteService.incluirCliente(criarClienteParaCadastro());
        Assert.assertNotNull(campanhas);
        verify(repository, atLeastOnce()).save(any(Cliente.class));
    }

    @Test(expected = ClienteJaCadastradoException.class)
    public void testarIncluiClienteExistenteComCampanhasESemCampanhaParaCadastrar() {
        Mockito.when(repository.findByEmail(anyString())).thenReturn(Optional.of(criarClienteComCampanha()));
        Mockito.when(timeRepository.findByIdAndNomeTime(anyInt(), anyString())).thenReturn(Optional.of(criarTime()));
        Mockito.when(campanhaRepository.findCampanhasNaoAtribuidas(any(), any())).thenReturn(null);
        clienteService.incluirCliente(criarClienteParaCadastro());
    }

    @Test
    public void testarIncluiClienteExistenteSemCampanhas() {
        Mockito.when(repository.findByEmail(anyString())).thenReturn(Optional.of(criarClienteCadastradado()));
        Mockito.when(timeRepository.findByIdAndNomeTime(anyInt(), anyString())).thenReturn(Optional.of(criarTime()));
        Mockito.when(campanhaRepository.findTimePorId(any(), any())).thenReturn(Arrays.asList(criarCampanhaGaloMaluco()));
        List<Campanha> campanhas = clienteService.incluirCliente(criarClienteParaCadastro());
        Assert.assertNotNull(campanhas);
        verify(repository, atLeastOnce()).save(any(Cliente.class));
    }


    private Cliente criarClienteParaCadastro() {
        return Cliente.builder()
                .nomeCompleto("dsfsd")
                .dataNascimento(new Date())
                .email("sajdaiok@skjdfslkfdj.com")
                .time(criarTime())
                .build();
    }

    private Cliente criarClienteCadastradado() {
        return Cliente.builder()
                .id(1)
                .nomeCompleto("dsfsd")
                .dataNascimento(new Date())
                .email("sajdaiok@skjdfslkfdj.com")
                .time(criarTime())
                .build();
    }

    private Cliente criarClienteComCampanha() {
        return Cliente.builder()
                .id(2)
                .nomeCompleto("dsfsd")
                .dataNascimento(new Date())
                .email("sajdaiok@skjdfslkfdj.com")
                .time(criarTime())
                .campanhas(Arrays.asList(criarCampanhaGaloDoido()))
                .build();
    }

    private Time criarTime() {
        return Time.builder().id(1).nomeTime("GALO").build();
    }

    private Campanha criarCampanhaGaloDoido() {
        return Campanha.builder().id(1)
                .nomeCampanha("GALO DOIDO DMAIS")
                .time(criarTime())
                .dataInicio(new Date())
                .dataFim(new Date())
                .build();
    }

    private Campanha criarCampanhaGaloMaluco() {
        return Campanha.builder().id(2)
                .nomeCampanha("GALO MALUCO DMAIS")
                .time(criarTime())
                .dataInicio(new Date())
                .dataFim(new Date())
                .build();
    }

}