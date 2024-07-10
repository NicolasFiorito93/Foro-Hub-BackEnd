package com.api.foro_hub.domain.curso;

public record DatosRespuestaCurso(
        Long id,
        String nombre,
        Categoria categoria
) {
    public DatosRespuestaCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
