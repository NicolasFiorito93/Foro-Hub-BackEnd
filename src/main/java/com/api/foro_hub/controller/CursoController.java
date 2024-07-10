package com.api.foro_hub.controller;

import com.api.foro_hub.domain.curso.CursoService;
import com.api.foro_hub.domain.curso.DatosActualizarCurso;
import com.api.foro_hub.domain.curso.DatosRegistroCurso;
import com.api.foro_hub.domain.curso.DatosRespuestaCurso;
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
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> registrarCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso, UriComponentsBuilder uriComponentsBuilder) {
        var datos = cursoService.registrarCurso(datosRegistroCurso);
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(datos.id()).toUri();
        return ResponseEntity.created(url).body(datos);
    }

    @GetMapping
    public ResponseEntity<List<DatosRespuestaCurso>> listarCursos(@PageableDefault(size = 10) Pageable pageable) {
        var cursos = cursoService.listarCursos(pageable);
        return ResponseEntity.ok(cursos.getContent());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> modificarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso) {
        var datos = cursoService.actualizar(datosActualizarCurso);
        return ResponseEntity.ok(datos);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> bajaCurso(@PathVariable Long id) {
        cursoService.bajaCurso(id);
        return ResponseEntity.noContent().build();
    }

}
