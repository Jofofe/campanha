package br.com.jofofe.campanha.exception;

public class TimeNaoEncontradoException extends RuntimeException {

    public TimeNaoEncontradoException() {
        super("Nenhum time foi encontrado com este ID.");
    }
}
