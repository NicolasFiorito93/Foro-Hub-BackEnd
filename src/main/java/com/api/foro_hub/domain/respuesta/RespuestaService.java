package com.api.foro_hub.domain.respuesta;

import com.api.foro_hub.domain.topico.DatosListaTopico;
import com.api.foro_hub.domain.topico.Estado;
import com.api.foro_hub.domain.topico.TopicoRepository;
import com.api.foro_hub.domain.usuario.UsuarioRepository;
import com.api.foro_hub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public DatosRespuestaRespuesta registrarRespuesta(DatosRegistroRespuesta datosRegistroRespuesta) {
        if (datosRegistroRespuesta.respuesta().isEmpty()) {
            throw new ValidacionDeIntegridad("No se puede enviar una respuesta vacia");
        }
        if (!topicoRepository.existsById(datosRegistroRespuesta.topicoId())) {
            throw new ValidacionDeIntegridad("No se encontro el topico");
        }
        if (!usuarioRepository.existsById(datosRegistroRespuesta.autorId())) {
            throw new ValidacionDeIntegridad("No se encontro el usuario");
        }
        if (respuestaRepository.existsByRespuesta(datosRegistroRespuesta.topicoId(), datosRegistroRespuesta.respuesta())) {
            throw new ValidacionDeIntegridad("El contenido de tu respuesta coincide con otra en el mismo topico");
        }
        var usuario = usuarioRepository.getReferenceById(datosRegistroRespuesta.autorId());
        if (!usuario.getActivo()) {
            throw new ValidacionDeIntegridad("El usuario debe estar activo");
        }
        var topico = topicoRepository.getReferenceById(datosRegistroRespuesta.topicoId());
        if (topico.getEstado() != Estado.ABIERTO) {
            throw new ValidacionDeIntegridad("El topico esta cerrado o borrado");
        }
        var respuesta = new Respuesta(datosRegistroRespuesta, usuario, topico);
        respuestaRepository.save(respuesta);
        return new DatosRespuestaRespuesta(respuesta);
    }

    public Page<DatosListaRespuesta> listar(Pageable pageable) {
        return respuestaRepository.findAll(pageable).map(DatosListaRespuesta::new);
    }

    public DatosRespuestaRespuesta actualizarRespuesta(DatosActualizarRespuesta datosActualizarRespuesta, Long id) {
        var respuestaOptional = respuestaRepository.findById(id);
        if (respuestaOptional.isEmpty()) {
            throw new ValidacionDeIntegridad("No se encontro el topico a modificar");
        }
        var respuesta = respuestaOptional.get();
        respuesta.setRespuesta(datosActualizarRespuesta.mensaje());
        return new DatosRespuestaRespuesta(respuesta);
    }

    public void eliminarRespuesta(Long id) {
        var respuestaOptional = respuestaRepository.findById(id);
        if (respuestaOptional.isEmpty()) {
            throw new ValidacionDeIntegridad("No se encontro el topico a modificar");
        }
        var respuesta = respuestaOptional.get();
        if (respuesta.getBorrado()) {
            throw new ValidacionDeIntegridad("El mensaje se encuentra actualmente borrado");
        }
        respuesta.setBorrado(true);
    }
}
