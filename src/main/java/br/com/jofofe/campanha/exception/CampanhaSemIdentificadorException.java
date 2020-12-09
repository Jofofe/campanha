package br.com.jofofe.campanha.exception;

public class CampanhaSemIdentificadorException extends RuntimeException {

    public CampanhaSemIdentificadorException() {
        super("Id da campanha não pode estar nulo");
    }

}
