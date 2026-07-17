package cl.duoc.plataformaeducativa.evaluacion;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import cl.duoc.plataformaeducativa.model.Curso;
import cl.duoc.plataformaeducativa.repository.CursoRepository;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final ResultadoEvaluacionRepository resultadoRepository;
    private final CursoRepository cursoRepository;

    public EvaluacionService(
            EvaluacionRepository evaluacionRepository,
            ResultadoEvaluacionRepository resultadoRepository,
            CursoRepository cursoRepository) {

        this.evaluacionRepository = evaluacionRepository;
        this.resultadoRepository = resultadoRepository;
        this.cursoRepository = cursoRepository;
    }

    public Evaluacion crear(
            Long idCurso,
            Evaluacion evaluacion) {

        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curso no encontrado"
                ));

        validarDatosEvaluacion(evaluacion);

        evaluacion.setId(null);
        evaluacion.setCurso(curso);

        if (evaluacion.getActivo() == null) {
            evaluacion.setActivo(true);
        }

        return evaluacionRepository.save(evaluacion);
    }

    public List<Evaluacion> listarPorCurso(Long idCurso) {

        if (!cursoRepository.existsById(idCurso)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Curso no encontrado"
            );
        }

        return evaluacionRepository
                .findByCursoIdOrderByIdAsc(idCurso);
    }

    public Evaluacion actualizar(
            Long idEvaluacion,
            Evaluacion datos) {

        Evaluacion evaluacion = buscarEvaluacion(idEvaluacion);

        validarDatosEvaluacion(datos);

        evaluacion.setTitulo(datos.getTitulo());
        evaluacion.setInstrucciones(datos.getInstrucciones());
        evaluacion.setPuntajeMaximo(datos.getPuntajeMaximo());

        if (datos.getActivo() != null) {
            evaluacion.setActivo(datos.getActivo());
        }

        return evaluacionRepository.save(evaluacion);
    }

    @Transactional
    public void eliminar(Long idEvaluacion) {

        Evaluacion evaluacion = buscarEvaluacion(idEvaluacion);

        resultadoRepository.deleteByEvaluacionId(idEvaluacion);
        evaluacionRepository.delete(evaluacion);
    }

    public ResultadoEvaluacion enviarRespuestas(
            Long idEvaluacion,
            String estudianteId,
            String respuestas) {

        Evaluacion evaluacion = buscarEvaluacion(idEvaluacion);

        if (!Boolean.TRUE.equals(evaluacion.getActivo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La evaluación no se encuentra activa"
            );
        }

        if (estudianteId == null || estudianteId.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El identificador del estudiante es obligatorio"
            );
        }

        if (respuestas == null || respuestas.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Las respuestas son obligatorias"
            );
        }

        boolean yaEnviada =
                resultadoRepository
                        .existsByEvaluacionIdAndEstudianteId(
                                idEvaluacion,
                                estudianteId
                        );

        if (yaEnviada) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El estudiante ya envió esta evaluación"
            );
        }

        ResultadoEvaluacion resultado =
                new ResultadoEvaluacion();

        resultado.setId(null);
        resultado.setEvaluacion(evaluacion);
        resultado.setEstudianteId(estudianteId);
        resultado.setRespuestas(respuestas);
        resultado.setEstado("ENVIADO");
        resultado.setFechaEnvio(LocalDateTime.now());

        return resultadoRepository.save(resultado);
    }

    public List<ResultadoEvaluacion> listarResultados(
            Long idEvaluacion) {

        buscarEvaluacion(idEvaluacion);

        return resultadoRepository
                .findByEvaluacionIdOrderByFechaEnvioAsc(
                        idEvaluacion
                );
    }

    public ResultadoEvaluacion calificar(
            Long idResultado,
            Double nota,
            String retroalimentacion) {

        ResultadoEvaluacion resultado =
                resultadoRepository.findById(idResultado)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Resultado no encontrado"
                                )
                        );

        if (nota == null || nota < 1.0 || nota > 7.0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La nota debe estar entre 1.0 y 7.0"
            );
        }

        resultado.setNota(nota);
        resultado.setRetroalimentacion(retroalimentacion);
        resultado.setEstado("CALIFICADO");
        resultado.setFechaCalificacion(LocalDateTime.now());

        return resultadoRepository.save(resultado);
    }

    private Evaluacion buscarEvaluacion(Long idEvaluacion) {

        return evaluacionRepository.findById(idEvaluacion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Evaluación no encontrada"
                ));
    }

    private void validarDatosEvaluacion(Evaluacion evaluacion) {

        if (evaluacion.getTitulo() == null
                || evaluacion.getTitulo().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El título es obligatorio"
            );
        }

        if (evaluacion.getInstrucciones() == null
                || evaluacion.getInstrucciones().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Las instrucciones son obligatorias"
            );
        }

        if (evaluacion.getPuntajeMaximo() == null
                || evaluacion.getPuntajeMaximo() <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El puntaje máximo debe ser mayor que cero"
            );
        }
    }
}