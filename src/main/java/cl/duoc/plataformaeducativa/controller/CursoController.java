package cl.duoc.plataformaeducativa.controller;

import cl.duoc.plataformaeducativa.model.Curso;
import cl.duoc.plataformaeducativa.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos() {
        List<Curso> cursos = cursoService.listarCursos();
        return ResponseEntity.ok(cursos);
    }

    @PostMapping
    public ResponseEntity<Curso> agregarCurso(@Valid @RequestBody Curso curso) {
        Curso nuevoCurso = cursoService.agregarCurso(curso);
        return ResponseEntity.ok(nuevoCurso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarCursoPorId(@PathVariable Long id) {
        Curso curso = cursoService.buscarCursoPorId(id);
        return ResponseEntity.ok(curso);
    }
}