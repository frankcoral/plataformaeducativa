package cl.duoc.plataformaeducativa.service;

import cl.duoc.plataformaeducativa.model.Curso;
import cl.duoc.plataformaeducativa.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public Curso agregarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso buscarCursoPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
    }
}