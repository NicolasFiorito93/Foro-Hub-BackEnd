package com.api.foro_hub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosListaRespuesta(
        String tituloTopico,
        String respuesta,
        String autor,
        LocalDateTime fecha
) {
    public DatosListaRespuesta(Respuesta respuesta) {
        this(respuesta.getTopico().getTitulo(), respuesta.getRespuesta(), respuesta.getAutor().getNombre(), respuesta.getFechaCreacion());
    }
}
