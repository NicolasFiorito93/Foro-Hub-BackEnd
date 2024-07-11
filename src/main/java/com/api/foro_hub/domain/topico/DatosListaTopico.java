package com.api.foro_hub.domain.topico;

import java.time.LocalDateTime;

public record DatosListaTopico(
        String autor,
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        String estado,
        String curso
) {
    public DatosListaTopico(Topico topico) {
        this(topico.getAutor().getNombre(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getEstado().toString(), topico.getCurso().getNombre());
    }
}
