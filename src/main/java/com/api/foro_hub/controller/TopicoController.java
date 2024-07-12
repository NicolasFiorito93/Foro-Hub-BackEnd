package com.api.foro_hub.controller;

import com.api.foro_hub.domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        var datos = topicoService.registrar(datosRegistroTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(datos.id()).toUri();
        return ResponseEntity.created(url).body(datos);
    }

    @GetMapping
    public ResponseEntity<List<DatosListaTopico>> listarTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        var topicos = topicoService.listarTopicos(pageable);
        return ResponseEntity.ok(topicos.getContent());
    }

    @GetMapping("/busqueda")
    public ResponseEntity<List<DatosListaTopico>> buscarTopicos(@RequestBody @Valid DatosBusquedaTopico datosBusquedaTopico,
                                                                @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        var topicos = topicoService.buscarTopicos(datosBusquedaTopico, pageable);
        return ResponseEntity.ok(topicos.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListaTopico> buscarPorId(@PathVariable Long id) {
        var topicos = topicoService.busquedaPorId(id);
        return ResponseEntity.ok(topicos);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> modificarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico,
                                                                @PathVariable Long id) {
        var topico = topicoService.actualizarTopico(datosActualizarTopico, id);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> eliminarTopico(@PathVariable Long id) {
        topicoService.borrarTopico(id);
        return ResponseEntity.noContent().build();
    }

}
