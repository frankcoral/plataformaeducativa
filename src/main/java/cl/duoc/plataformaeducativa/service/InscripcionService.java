package cl.duoc.plataformaeducativa.service;

import cl.duoc.plataformaeducativa.dto.CursoResumenDTO;
import cl.duoc.plataformaeducativa.dto.InscripcionRequestDTO;
import cl.duoc.plataformaeducativa.dto.InscripcionResponseDTO;
import cl.duoc.plataformaeducativa.model.Curso;
import cl.duoc.plataformaeducativa.model.Inscripcion;
import cl.duoc.plataformaeducativa.repository.CursoRepository;
import cl.duoc.plataformaeducativa.repository.InscripcionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final CursoRepository cursoRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository, CursoRepository cursoRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.cursoRepository = cursoRepository;
    }

    public InscripcionResponseDTO crearInscripcion(InscripcionRequestDTO request) {
        List<Curso> cursos = cursoRepository.findAllById(request.getIdsCursos());

        if (cursos.isEmpty()) {
            throw new RuntimeException("No se encontraron cursos para la inscripción");
        }

        if (cursos.size() != request.getIdsCursos().size()) {
            throw new RuntimeException("Uno o más cursos seleccionados no existen");
        }

        Integer totalPagar = cursos.stream()
                .mapToInt(Curso::getCosto)
                .sum();

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(request.getEstudiante());
        inscripcion.setCorreo(request.getCorreo());
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setCursos(cursos);
        inscripcion.setTotalPagar(totalPagar);

        Inscripcion inscripcionGuardada = inscripcionRepository.save(inscripcion);

        List<CursoResumenDTO> cursosResumen = cursos.stream()
                .map(curso -> new CursoResumenDTO(
                        curso.getId(),
                        curso.getNombre(),
                        curso.getInstructor(),
                        curso.getDuracionHoras(),
                        curso.getCosto()
                ))
                .toList();

        return new InscripcionResponseDTO(
                inscripcionGuardada.getId(),
                inscripcionGuardada.getEstudiante(),
                inscripcionGuardada.getCorreo(),
                inscripcionGuardada.getFechaInscripcion(),
                cursosResumen,
                inscripcionGuardada.getTotalPagar()
        );
    }
}