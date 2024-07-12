package com.api.foro_hub.domain.respuesta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
        @NotBlank
        String respuesta,
        @NotNull
        @JsonAlias("topico_id")
        Long topicoId,
        @NotNull
        @JsonAlias("autor_id")
        Long autorId
) {
}
