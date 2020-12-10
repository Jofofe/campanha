package br.com.jofofe.campanha.controller;

import br.com.jofofe.campanha.CampanhaApplication;
import br.com.jofofe.campanha.dto.ClienteDTO;
import br.com.jofofe.campanha.dto.TimeDTO;
import br.com.jofofe.campanha.utils.Util;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {CampanhaApplication.class})
public class ClienteControllerTest extends AbstractBaseControllerTest {

    @Test
    public void testarIncluirCliente() throws Exception {
        mockMvc.perform(post("/cliente")
                .content(new Gson().toJson(criarClienteParaCadastro()).getBytes())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    private ClienteDTO criarClienteParaCadastro() {
        return ClienteDTO.builder()
                .nomeCompleto("dsfsd")
                .dataNascimento(new Date())
                .email("sajdaiok@skjdfslkfdj.com")
                .time(criarTime())
                .build();
    }

    private TimeDTO criarTime() {
        return TimeDTO.builder().id(1).nomeTime("GALO").build();
    }

}