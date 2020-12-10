package br.com.jofofe.campanha.exception;

public class CampanhaComIdentificadorException extends RuntimeException {

    public CampanhaComIdentificadorException() {
        super("Id da campanha não pode estar na inclusão");
    }

}
