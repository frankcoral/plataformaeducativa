package cl.duoc.plataformaeducativa.repository;

import cl.duoc.plataformaeducativa.model.ResumenInscripcionMq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumenInscripcionMqRepository extends JpaRepository<ResumenInscripcionMq, Long> {
}