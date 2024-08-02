package br.com.descompila.exception;

public class ConexaoFalhouException extends Exception {
    
    public ConexaoFalhouException(String cause){
        super(cause);
    }

    public ConexaoFalhouException(Throwable cause){
        super(cause);
    }
}