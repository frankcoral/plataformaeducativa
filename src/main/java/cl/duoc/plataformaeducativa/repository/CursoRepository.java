package cl.duoc.plataformaeducativa.repository;

import cl.duoc.plataformaeducativa.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}