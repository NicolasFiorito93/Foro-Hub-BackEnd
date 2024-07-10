package com.api.foro_hub.domain.curso;

import com.api.foro_hub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Topico> topicos;
    private Boolean activo;

    public Curso(DatosRegistroCurso datosRegistroCurso) {
        this.nombre = datosRegistroCurso.nombre();
        this.categoria = datosRegistroCurso.categoria();
        this.activo = true;
    }

    public void actualizar(DatosActualizarCurso datosActualizarCurso) {
        if (datosActualizarCurso.nombre() != null) {
            this.nombre = datosActualizarCurso.nombre();
        }
        if (datosActualizarCurso.categoria() != null) {
            this.categoria = datosActualizarCurso.categoria();
        }
    }

    public void activarCurso() {
        this.activo = false;
    }

}
