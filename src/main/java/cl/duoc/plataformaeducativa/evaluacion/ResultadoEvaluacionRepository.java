package cl.duoc.plataformaeducativa.evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoEvaluacionRepository
        extends JpaRepository<ResultadoEvaluacion, Long> {

    List<ResultadoEvaluacion>
    findByEvaluacionIdOrderByFechaEnvioAsc(Long idEvaluacion);

    boolean existsByEvaluacionIdAndEstudianteId(
            Long idEvaluacion,
            String estudianteId
    );

    void deleteByEvaluacionId(Long idEvaluacion);
}