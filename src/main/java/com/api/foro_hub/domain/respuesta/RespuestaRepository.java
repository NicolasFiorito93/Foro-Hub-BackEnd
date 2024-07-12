package com.api.foro_hub.domain.respuesta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
            FROM Respuesta r
            WHERE r.respuesta = :respuesta
            AND r.topico.id = :idTopico
            """)
    Boolean existsByRespuesta(Long idTopico, String respuesta);
}
