package cl.duoc.plataformaeducativa.controller;

import cl.duoc.plataformaeducativa.dto.InscripcionRequestDTO;
import cl.duoc.plataformaeducativa.dto.InscripcionResponseDTO;
import cl.duoc.plataformaeducativa.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> crearInscripcion(
            @Valid @RequestBody InscripcionRequestDTO request) {

        InscripcionResponseDTO respuesta = inscripcionService.crearInscripcion(request);
        return ResponseEntity.ok(respuesta);
    }
}