package cl.duoc.plataformaeducativa.evaluacion;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(
            EvaluacionService evaluacionService) {

        this.evaluacionService = evaluacionService;
    }

    @PostMapping("/curso/{idCurso}")
    public ResponseEntity<Evaluacion> crear(
            @PathVariable Long idCurso,
            @RequestBody Evaluacion evaluacion) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(evaluacionService.crear(
                        idCurso,
                        evaluacion
                ));
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<Evaluacion>> listarPorCurso(
            @PathVariable Long idCurso) {

        return ResponseEntity.ok(
                evaluacionService.listarPorCurso(idCurso)
        );
    }

    @PutMapping("/{idEvaluacion}")
    public ResponseEntity<Evaluacion> actualizar(
            @PathVariable Long idEvaluacion,
            @RequestBody Evaluacion evaluacion) {

        return ResponseEntity.ok(
                evaluacionService.actualizar(
                        idEvaluacion,
                        evaluacion
                )
        );
    }

    @DeleteMapping("/{idEvaluacion}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long idEvaluacion) {

        evaluacionService.eliminar(idEvaluacion);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idEvaluacion}/respuestas")
    public ResponseEntity<ResultadoEvaluacion>
    enviarRespuestas(
            @PathVariable Long idEvaluacion,
            @RequestBody EnvioRespuestasRequest request) {

        ResultadoEvaluacion resultado =
                evaluacionService.enviarRespuestas(
                        idEvaluacion,
                        request.estudianteId(),
                        request.respuestas()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultado);
    }

    @GetMapping("/{idEvaluacion}/resultados")
    public ResponseEntity<List<ResultadoEvaluacion>>
    listarResultados(
            @PathVariable Long idEvaluacion) {

        return ResponseEntity.ok(
                evaluacionService.listarResultados(
                        idEvaluacion
                )
        );
    }

    @PutMapping("/resultados/{idResultado}/calificar")
    public ResponseEntity<ResultadoEvaluacion> calificar(
            @PathVariable Long idResultado,
            @RequestBody CalificacionRequest request) {

        return ResponseEntity.ok(
                evaluacionService.calificar(
                        idResultado,
                        request.nota(),
                        request.retroalimentacion()
                )
        );
    }

    public record EnvioRespuestasRequest(
            String estudianteId,
            String respuestas) {
    }

    public record CalificacionRequest(
            Double nota,
            String retroalimentacion) {
    }
}