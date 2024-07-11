package com.api.foro_hub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTitulo(String titulo);

    boolean existsByMensaje(String mensaje);

    @Query("""
            SELECT t FROM Topico t
            WHERE DAY(t.fechaCreacion) = :dia
            """)
    Page<DatosListaTopico> findByFechaCreacion(String dia, Pageable pageable);

    @Query("""
            SELECT t FROM Topico t
            WHERE t.curso.nombre ILIKE %:curso%
            """)
    Page<DatosListaTopico> findByCurso(String curso, Pageable pageable);

    @Query("""
            SELECT t FROM Topico t
            WHERE (:dia IS NULL OR FUNCTION('DAY', t.fechaCreacion) = :dia)
            AND (:curso IS NULL OR LOWER(t.curso.nombre) ILIKE %:curso%)
            """)
    Page<DatosListaTopico> findByDiaAndCurso(String dia, String curso, Pageable pageable);

    @Query("""
            SELECT t FROM Topico t
            WHERE t.estado != :estado
            """)
    Page<DatosListaTopico> findByEstadoNot(Estado estado, Pageable pageable);
}
