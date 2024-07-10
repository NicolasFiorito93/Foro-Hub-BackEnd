package com.api.foro_hub.domain.usuario;

import com.api.foro_hub.infra.errores.ValidacionDeIntegridad;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public DatosRespuestaUsuario registrarUsuario(DatosRegistroUsuario datosRegistroUsuario) {
        if (usuarioRepository.existsByEmail(datosRegistroUsuario.email())) {
            throw new ValidationException("El email ya se encuentra registrado");
        }
        var usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return new DatosRespuestaUsuario(usuario);
    }

    public Page<DatosRespuestaUsuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findByActivoTrue(pageable).map(DatosRespuestaUsuario::new);
    }

    public DatosRespuestaUsuario actualizarUsuario(DatosActualizarUsuario datosActualizarUsuario) {
        if (usuarioRepository.findById(datosActualizarUsuario.id()).isEmpty()) {
            throw new ValidacionDeIntegridad("No se encontro el usuario.");
        }

        var usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());
        if (!usuario.getEmail().equalsIgnoreCase(datosActualizarUsuario.email()) && usuarioRepository.existsByEmail(datosActualizarUsuario.email())) {
            throw new ValidationException("El email ya se encuentra registrado");
        }
        if (datosActualizarUsuario.nombre() != null) {
            usuario.setNombre(datosActualizarUsuario.nombre());
        }
        if (datosActualizarUsuario.email() != null) {
            usuario.setEmail(datosActualizarUsuario.email());
        }
        if (datosActualizarUsuario.password() != null) {
            usuario.setPassword(passwordEncoder.encode(datosActualizarUsuario.password()));
        }
        return new DatosRespuestaUsuario(usuario);
    }

    public void desactivarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ValidationException("No se encontro el usuario.");
        }
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.setActivo(false);
    }
}
