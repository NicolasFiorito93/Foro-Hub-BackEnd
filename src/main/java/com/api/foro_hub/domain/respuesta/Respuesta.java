package com.api.foro_hub.domain.respuesta;

import com.api.foro_hub.domain.topico.Topico;
import com.api.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String respuesta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topico")
    private Topico topico;
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario autor;
    private Boolean solucion;
    private Boolean borrado;


    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta, Usuario usuario, Topico topico) {
        this.respuesta = datosRegistroRespuesta.respuesta();
        this.topico = topico;
        this.fechaCreacion = LocalDateTime.now();
        this.autor = usuario;
        this.borrado = false;
    }
}
