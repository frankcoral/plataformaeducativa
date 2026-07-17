package cl.duoc.plataformaeducativa.contenido;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.duoc.plataformaeducativa.model.Curso;
import cl.duoc.plataformaeducativa.repository.CursoRepository;

@Service
public class ContenidoService {

    private final ContenidoRepository contenidoRepository;
    private final CursoRepository cursoRepository;

    public ContenidoService(
            ContenidoRepository contenidoRepository,
            CursoRepository cursoRepository) {

        this.contenidoRepository = contenidoRepository;
        this.cursoRepository = cursoRepository;
    }

    public Contenido crear(Long idCurso, Contenido contenido) {

        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curso no encontrado"
                ));

        contenido.setId(null);
        contenido.setCurso(curso);

        return contenidoRepository.save(contenido);
    }

    public List<Contenido> listarPorCurso(Long idCurso) {

        if (!cursoRepository.existsById(idCurso)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Curso no encontrado"
            );
        }

        return contenidoRepository.findByCursoId(idCurso);
    }

    public Contenido actualizar(Long idContenido, Contenido datos) {

        Contenido contenido = contenidoRepository.findById(idContenido)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Contenido no encontrado"
                ));

        contenido.setTitulo(datos.getTitulo());
        contenido.setDescripcion(datos.getDescripcion());
        contenido.setTipo(datos.getTipo());
        contenido.setUrlRecurso(datos.getUrlRecurso());

        return contenidoRepository.save(contenido);
    }

    public void eliminar(Long idContenido) {

        Contenido contenido = contenidoRepository.findById(idContenido)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Contenido no encontrado"
                ));

        contenidoRepository.delete(contenido);
    }
}