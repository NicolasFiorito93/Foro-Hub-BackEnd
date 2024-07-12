package com.api.foro_hub.controller;

import com.api.foro_hub.domain.usuario.DatosAutenticarUsuario;
import com.api.foro_hub.domain.usuario.Usuario;
import com.api.foro_hub.infra.security.DatosJWTToken;
import com.api.foro_hub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticarUsuario datosAutenticarUsuario) {
        Authentication autToken = new UsernamePasswordAuthenticationToken(datosAutenticarUsuario.email(), datosAutenticarUsuario.password());
        var usuarioAutenticado = authenticationManager.authenticate(autToken);
        var JWTToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTToken));
    }
}
