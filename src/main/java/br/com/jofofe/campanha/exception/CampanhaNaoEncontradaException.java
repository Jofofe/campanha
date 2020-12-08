package br.com.jofofe.campanha.exception;

public class CampanhaNaoEncontradaException extends RuntimeException {

    public CampanhaNaoEncontradaException() {
        super("Nenhuma campanha foi encontrada.");
    }

}
