package cl.duoc.plataformaeducativa.evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionRepository
        extends JpaRepository<Evaluacion, Long> {

    List<Evaluacion> findByCursoIdOrderByIdAsc(Long idCurso);
}