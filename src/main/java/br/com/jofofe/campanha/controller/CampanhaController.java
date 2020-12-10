package br.com.jofofe.campanha.controller;

import br.com.jofofe.campanha.dto.CampanhaConsultaDTO;
import br.com.jofofe.campanha.dto.CampanhaDTO;
import br.com.jofofe.campanha.entidades.Campanha;
import br.com.jofofe.campanha.mapper.ObjectMapper;
import br.com.jofofe.campanha.service.CampanhaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/campanha")
@Api(value = "Campanha")
public class CampanhaController {

    private final CampanhaService campanhaService;

    private final ObjectMapper mapper;

    public CampanhaController(CampanhaService campanhaService, ObjectMapper mapper) {
        this.campanhaService = campanhaService;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "Incluir campanha")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campanha salva com sucesso", response = Object.class),
            @ApiResponse(code = 404, message = "Alguma informação não foi encontrada"),
            @ApiResponse(code = 400, message = "Ooorreu algum erro negocial"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    public ResponseEntity incluirCampanha(@Valid @RequestBody CampanhaDTO campanha) {
        campanhaService.incluirCampanha(mapper.convert(campanha, Campanha.class));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idCampanha}")
    @ApiOperation(value = "Buscar campanha", response = CampanhaConsultaDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campanha retornada"),
            @ApiResponse(code = 404, message = "Campanha não encontrada"),
            @ApiResponse(code = 400, message = "Ooorreu algum erro negocial"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    public ResponseEntity buscarCampanha(@PathVariable("idCampanha") Integer idCampanha) {
        CampanhaConsultaDTO campanha = campanhaService.buscarCampanha(idCampanha);
        return ResponseEntity.ok(campanha);
    }

    @DeleteMapping("/{idCampanha}")
    @ApiOperation(value = "Deletar campanha")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campanha deletada", response = Object.class),
            @ApiResponse(code = 404, message = "Campanha não encontrada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    public ResponseEntity deletarCampanha(@PathVariable("idCampanha") Integer idCampanha) {
        campanhaService.deletarCampanha(idCampanha);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    @ApiOperation(value = "Alterar campanha")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campanha alterada", response = Object.class),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    public ResponseEntity alterarCampanha(@Valid @RequestBody CampanhaDTO campanha) {
        campanhaService.alterarCampanha(mapper.convert(campanha, Campanha.class));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/campanhas-vigencia-prorrogada")
    @ApiOperation(value = "Buscar campanhas com vigencia prorrogada", response = CampanhaConsultaDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campanhas com vigencia prorrogada retornadas"),
            @ApiResponse(code = 404, message = "Alguma informação não foi encontrada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    public ResponseEntity buscarCampanhasComVigenciasProrrogadas() {
        List<Object> campanhas = campanhaService.buscarCampanhasComDataVigenciaProrrogada().stream()
                .map(t -> mapper.convert(t, CampanhaConsultaDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(campanhas);
    }



}
