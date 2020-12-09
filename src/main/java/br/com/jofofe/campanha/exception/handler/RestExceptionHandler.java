package br.com.jofofe.campanha.exception.handler;

import br.com.jofofe.campanha.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CampanhaNaoEncontradaException.class})
    public ResponseEntity campanhaNaoEncontrada(CampanhaNaoEncontradaException ex, WebRequest request) {
        log.debug("manipulação de CampanhaNaoEncontradaException...");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CampanhaSemIdentificadorException.class})
    public ResponseEntity campanhaSemIdentificador(CampanhaSemIdentificadorException ex, WebRequest request) {
        log.debug("manipulação de CampanhaSemIdentificadorException...");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CampanhaExistenteException.class})
    public ResponseEntity canpanhaExistente(CampanhaExistenteException ex, WebRequest request) {
        log.debug("manipulação de CampanhaExistenteException...");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CampanhaComVigenciaVencidaException.class})
    public ResponseEntity campanhaComVigenciaVencida(CampanhaComVigenciaVencidaException ex, WebRequest request) {
        log.debug("manipulação de CampanhaComVigenciaVencidaException...");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TimeNaoEncontradoException.class})
    public ResponseEntity timeNaoEncontrado(TimeNaoEncontradoException ex, WebRequest request) {
        log.debug("manipulação de TimeNaoEncontradoException...");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
