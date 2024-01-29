package com.iteria.Prueba.exception;

public class TipoDocumentoNuloException extends RuntimeException {

    public TipoDocumentoNuloException(String message) {
        super(message);
    }

    public TipoDocumentoNuloException(String message, Throwable cause) {
        super(message, cause);
    }
}
