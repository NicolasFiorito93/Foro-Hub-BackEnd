package com.api.foro_hub.controller;

import com.api.foro_hub.domain.respuesta.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta, UriComponentsBuilder uriComponentsBuilder) {
        var respuesta = respuestaService.registrarRespuesta(datosRegistroRespuesta);
        URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(uri).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaRespuesta>> listarRespuesta(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        var respuesta = respuestaService.listar(pageable);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaRespuesta> modificarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta,
                                                                      @PathVariable Long id) {
        var respuesta = respuestaService.actualizarRespuesta(datosActualizarRespuesta, id);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> eliminarRespuesta(@PathVariable Long id) {
        respuestaService.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}
