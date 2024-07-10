package com.api.foro_hub.domain.curso;

import com.api.foro_hub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public DatosRespuestaCurso registrarCurso(DatosRegistroCurso datosRegistroCurso) {
        if (cursoRepository.existsByNombre(datosRegistroCurso.nombre())) {
            throw new ValidacionDeIntegridad("Ya existe el cruso:" + datosRegistroCurso.nombre());
        }
        var curso = cursoRepository.save(new Curso(datosRegistroCurso));
        return new DatosRespuestaCurso(curso);
    }

    public Page<DatosRespuestaCurso> listarCursos(Pageable pageable) {
        return cursoRepository.findByActivoTrue(pageable);
    }

    public void bajaCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new ValidacionDeIntegridad("No se encontro el id.");
        }
        var curso = cursoRepository.getReferenceById(id);
        curso.activarCurso();
    }

    public DatosRespuestaCurso actualizar(DatosActualizarCurso datosActualizarCurso) {
        if (cursoRepository.findById(datosActualizarCurso.id()).isEmpty()) {
            throw new ValidacionDeIntegridad("No se encontro el id");
        }
        var curso = cursoRepository.getReferenceById(datosActualizarCurso.id());
        if (!curso.getNombre().equalsIgnoreCase(datosActualizarCurso.nombre()) && cursoRepository.existsByNombre(datosActualizarCurso.nombre())) {
            throw new ValidacionDeIntegridad("Ya existe un curso con el nombre: " + datosActualizarCurso.nombre());
        }

        curso.actualizar(datosActualizarCurso);
        return new DatosRespuestaCurso(curso);
    }
}
