package br.com.descompila.exception;

public class ConexaoFalhouException extends Exception {
    public ConexaoFalhouException(Throwable cause){
        super(cause);
    }

    public ConexaoFalhouException(String cause){
        super(cause);
    }
}