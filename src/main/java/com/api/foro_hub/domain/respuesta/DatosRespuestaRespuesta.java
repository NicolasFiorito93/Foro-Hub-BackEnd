package com.api.foro_hub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosRespuestaRespuesta(
        Long id,
        String respuesta,
        Long topicoId,
        Long autorId,
        LocalDateTime fecha
) {
    public DatosRespuestaRespuesta(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getRespuesta(), respuesta.getTopico().getId(), respuesta.getAutor().getId(), respuesta.getFechaCreacion());
    }
}
