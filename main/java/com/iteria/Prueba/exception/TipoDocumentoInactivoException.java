package com.iteria.Prueba.exception;

public class TipoDocumentoInactivoException extends RuntimeException {

    public TipoDocumentoInactivoException() {
        super("El tipo de documento está inactivo.");
    }

    public TipoDocumentoInactivoException(String message) {
        super(message);
    }
}