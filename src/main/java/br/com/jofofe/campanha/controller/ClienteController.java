package br.com.jofofe.campanha.controller;

import br.com.jofofe.campanha.dto.ClienteDTO;
import br.com.jofofe.campanha.entidades.Campanha;
import br.com.jofofe.campanha.entidades.Cliente;
import br.com.jofofe.campanha.mapper.ObjectMapper;
import br.com.jofofe.campanha.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@Api(value = "Cliente")
public class ClienteController {

    private final ClienteService clienteService;

    private final ObjectMapper mapper;

    public ClienteController(ClienteService clienteService, ObjectMapper mapper) {
        this.clienteService = clienteService;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "Incluir cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente salvo com sucesso", response = Object.class),
            @ApiResponse(code = 404, message = "Alguma informação não foi encontrada"),
            @ApiResponse(code = 400, message = "Ooorreu algum erro negocial"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    public ResponseEntity incluirCliente(@Valid @RequestBody ClienteDTO cliente) {
        List<Campanha> campanhas = clienteService.incluirCliente(mapper.convert(cliente, Cliente.class));
        return ResponseEntity.ok(campanhas);
    }

}
