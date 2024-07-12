package com.api.foro_hub.controller;

import com.api.foro_hub.domain.usuario.*;
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
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        var datos = usuarioService.registrarUsuario(datosRegistroUsuario);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(datos.id()).toUri();
        return ResponseEntity.created(url).body(datos);
    }

    @GetMapping
    public ResponseEntity<List<DatosRespuestaUsuario>> listarUsuarios(@PageableDefault(size = 10) Pageable pageable) {
        var usuarios = usuarioService.listarUsuarios(pageable);
        return ResponseEntity.ok(usuarios.getContent());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        var datos = usuarioService.actualizarUsuario(datosActualizarUsuario);
        return ResponseEntity.ok(datos);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity desactivarUsuario(@PathVariable Long id) {
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
