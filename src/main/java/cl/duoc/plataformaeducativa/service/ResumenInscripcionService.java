package cl.duoc.plataformaeducativa.service;

import cl.duoc.plataformaeducativa.model.Curso;
import cl.duoc.plataformaeducativa.model.Inscripcion;
import cl.duoc.plataformaeducativa.repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ResumenInscripcionService {

    private final InscripcionRepository inscripcionRepository;

    public ResumenInscripcionService(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    @Transactional
    public Path generarArchivoResumen(Long idInscripcion) {
        Inscripcion inscripcion = inscripcionRepository.findById(idInscripcion)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + idInscripcion));

        try {
            Path carpetaResumenes = Path.of("resumenes-locales");
            Files.createDirectories(carpetaResumenes);

            Path archivo = carpetaResumenes.resolve("resumen-inscripcion-" + idInscripcion + ".txt");

            String contenido = construirContenidoResumen(inscripcion);

            Files.writeString(archivo, contenido);

            return archivo;

        } catch (IOException e) {
            throw new RuntimeException("Error al generar archivo físico del resumen: " + e.getMessage());
        }
    }

    private String construirContenidoResumen(Inscripcion inscripcion) {
        StringBuilder contenido = new StringBuilder();

        contenido.append("RESUMEN DE INSCRIPCIÓN\n");
        contenido.append("======================\n\n");

        contenido.append("Número de resumen: ").append(inscripcion.getId()).append("\n");
        contenido.append("Estudiante: ").append(inscripcion.getEstudiante()).append("\n");
        contenido.append("Correo: ").append(inscripcion.getCorreo()).append("\n");
        contenido.append("Fecha de inscripción: ").append(inscripcion.getFechaInscripcion()).append("\n\n");

        contenido.append("Cursos inscritos:\n");

        List<Curso> cursos = inscripcion.getCursos();

        for (Curso curso : cursos) {
            contenido.append("- ID curso: ").append(curso.getId()).append("\n");
            contenido.append("  Nombre: ").append(curso.getNombre()).append("\n");
            contenido.append("  Instructor: ").append(curso.getInstructor()).append("\n");
            contenido.append("  Duración: ").append(curso.getDuracionHoras()).append(" horas\n");
            contenido.append("  Costo: $").append(curso.getCosto()).append("\n\n");
        }

        contenido.append("Total a pagar: $").append(inscripcion.getTotalPagar()).append("\n");

        return contenido.toString();
    }
}