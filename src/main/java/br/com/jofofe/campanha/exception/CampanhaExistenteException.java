package br.com.jofofe.campanha.exception;

public class CampanhaExistenteException extends RuntimeException {

    public CampanhaExistenteException() {
        super("Campanha já existente na base de dados.");
    }


}
