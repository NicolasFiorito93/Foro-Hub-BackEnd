package com.api.foro_hub.domain.topico;

import com.api.foro_hub.domain.curso.CursoRepository;
import com.api.foro_hub.domain.usuario.UsuarioRepository;
import com.api.foro_hub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    public DatosRespuestaTopico registrar(DatosRegistroTopico datosRegistroTopico) {
        if (!usuarioRepository.existsById(datosRegistroTopico.idUsuario())) {
            throw new ValidacionDeIntegridad("Usuario no encontrado");
        }
        if (!cursoRepository.existsById(datosRegistroTopico.idCurso())) {
            throw new ValidacionDeIntegridad("Curso no encontrado");
        }
        if (topicoRepository.existsByTitulo(datosRegistroTopico.titulo())) {
            throw new ValidacionDeIntegridad("Ya existe un topico con este titulo: " + datosRegistroTopico.titulo());
        }
        if (topicoRepository.existsByMensaje(datosRegistroTopico.mensaje())) {
            throw new ValidacionDeIntegridad("Ya existe un topico con este mensaje: " + datosRegistroTopico.mensaje());
        }

        var usuario = usuarioRepository.getReferenceById(datosRegistroTopico.idUsuario());

        if (!usuario.getActivo()) {
            throw new ValidacionDeIntegridad("El usuario tiene que estar activo para hacer una publicacion");
        }

        var curso = cursoRepository.getReferenceById(datosRegistroTopico.idCurso());
        var topico = new Topico(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje(), usuario, curso);

        topicoRepository.save(topico);

        return new DatosRespuestaTopico(topico);
    }

    public Page<DatosListaTopico> listarTopicos(Pageable pageable) {
        return topicoRepository.findByEstadoNot(Estado.BORRADO, pageable);
        //return topicoRepository.findAll(pageable).map(DatosListaTopico::new);
    }

    public Page<DatosListaTopico> buscarTopicos(DatosBusquedaTopico datosBusquedaTopico, Pageable pageable) {
        if (datosBusquedaTopico.dia() == null && datosBusquedaTopico.curso() == null) {
            throw new ValidacionDeIntegridad("No se llenaron los campos necesarios");
        }

        Page<DatosListaTopico> resultados = topicoRepository.findByDiaAndCurso(
                datosBusquedaTopico.dia(),
                datosBusquedaTopico.curso(),
                pageable
        );

        if (resultados.isEmpty()) {
            throw new ValidacionDeIntegridad("No se encontraron resultados para la búsqueda.");
        }

        return resultados;
    }

    public DatosListaTopico busquedaPorId(Long id) {
        var topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            throw new ValidacionDeIntegridad("No se encontro el topico");
        }
        return new DatosListaTopico(topicoRepository.getReferenceById(id));
    }

    public DatosRespuestaTopico actualizarTopico(DatosActualizarTopico datosActualizarTopico, Long id) {
        var topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            throw new ValidacionDeIntegridad("No se encontro el topico");
        }
        var topico = topicoOptional.get();
        if (datosActualizarTopico.titulo() == null || datosActualizarTopico.mensaje() == null) {
            throw new ValidacionDeIntegridad("No pueden haber campos vacios.");
        }
        topico.setTitulo(datosActualizarTopico.titulo());
        topico.setMensaje(datosActualizarTopico.mensaje());

        return new DatosRespuestaTopico(topico);
    }

    public void borrarTopico(Long id) {
        var topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            throw new ValidacionDeIntegridad("No se encontro el topico");
        }
        var topico = topicoOptional.get();
        if (topico.getEstado() == Estado.BORRADO) {
            throw new ValidacionDeIntegridad("El topico ya se encuentra borrado");
        }
        topico.setEstado(Estado.BORRADO);
    }
}
