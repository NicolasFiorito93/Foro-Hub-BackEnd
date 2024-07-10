package com.api.foro_hub.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException{
    public ValidacionDeIntegridad(String msg) {
        super(msg);
    }
}
