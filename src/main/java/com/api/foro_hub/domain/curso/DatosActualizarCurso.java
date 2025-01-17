package com.api.foro_hub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(
        @NotNull
        Long id,
        @NotBlank
        String nombre,
        @NotNull
        Categoria categoria
) {

}
