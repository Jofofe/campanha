package br.com.jofofe.campanha.exception;

public class ClienteJaCadastradoException extends RuntimeException {

    public ClienteJaCadastradoException() {
        super("Cliente já cadastrado");
    }
}
