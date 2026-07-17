package cl.duoc.plataformaeducativa.contenido;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contenidos")
public class ContenidoController {

    private final ContenidoService contenidoService;

    public ContenidoController(ContenidoService contenidoService) {
        this.contenidoService = contenidoService;
    }

    @PostMapping("/curso/{idCurso}")
    public ResponseEntity<Contenido> crear(
            @PathVariable Long idCurso,
            @RequestBody Contenido contenido) {

        Contenido contenidoCreado =
                contenidoService.crear(idCurso, contenido);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contenidoCreado);
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<Contenido>> listarPorCurso(
            @PathVariable Long idCurso) {

        return ResponseEntity.ok(
                contenidoService.listarPorCurso(idCurso)
        );
    }

    @PutMapping("/{idContenido}")
    public ResponseEntity<Contenido> actualizar(
            @PathVariable Long idContenido,
            @RequestBody Contenido contenido) {

        return ResponseEntity.ok(
                contenidoService.actualizar(idContenido, contenido)
        );
    }

    @DeleteMapping("/{idContenido}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long idContenido) {

        contenidoService.eliminar(idContenido);

        return ResponseEntity.noContent().build();
    }
}