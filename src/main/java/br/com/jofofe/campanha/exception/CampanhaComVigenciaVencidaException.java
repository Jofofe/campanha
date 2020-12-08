package br.com.jofofe.campanha.exception;

public class CampanhaComVigenciaVencidaException extends RuntimeException {

    public CampanhaComVigenciaVencidaException() {
        super("Campanha com vigência vencida.");
    }

}
