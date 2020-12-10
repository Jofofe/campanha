package br.com.jofofe.campanha.controller;

import br.com.jofofe.campanha.CampanhaApplication;
import br.com.jofofe.campanha.dto.CampanhaDTO;
import br.com.jofofe.campanha.dto.TimeDTO;
import br.com.jofofe.campanha.utils.Util;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {CampanhaApplication.class})
public class CampanhaControllerTest extends AbstractBaseControllerTest {

    @Test
    public void testarIncluirCampanha() throws Exception {
        mockMvc.perform(post("/cliente")
                .content(new Gson().toJson(criarCampanhaSemIdentificados()).getBytes())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    @Test
    public void testarBuscarCampanha() throws Exception {
        mockMvc.perform(get("/cliente/" + 1).accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    @Test
    public void testarDeletarCampanha() throws Exception {
        mockMvc.perform(delete("/cliente/" + 1).accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    @Test
    public void testarAlterarCampanha() throws Exception {
        mockMvc.perform(put("/cliente/" + 1).accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    @Test
    public void testarBuscarCampanhasComVigenciasProrrogadas() throws Exception {
        mockMvc.perform(get("/cliente/campanhas-vigencia-prorrogada" + 1).accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(Util.statusMatcher);
    }

    private CampanhaDTO criarCampanhaSemIdentificados() {
        return CampanhaDTO.builder()
                .nomeCampanha("GALO DOIDO DMAIS")
                .time(criarTime())
                .dataInicio(new Date())
                .dataFim(new Date())
                .build();
    }

    private TimeDTO criarTime() {
        return TimeDTO.builder().id(1).nomeTime("GALO").build();
    }

}