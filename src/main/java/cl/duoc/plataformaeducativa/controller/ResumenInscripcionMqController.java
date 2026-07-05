package cl.duoc.plataformaeducativa.controller;

import cl.duoc.plataformaeducativa.model.ResumenInscripcionMq;
import cl.duoc.plataformaeducativa.service.ResumenInscripcionMqService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resumenes-mq")
public class ResumenInscripcionMqController {

    private final ResumenInscripcionMqService resumenInscripcionMqService;

    public ResumenInscripcionMqController(ResumenInscripcionMqService resumenInscripcionMqService) {
        this.resumenInscripcionMqService = resumenInscripcionMqService;
    }

    @PostMapping("/{idInscripcion}/enviar-cola")
    public ResponseEntity<Map<String, Object>> enviarResumenACola(@PathVariable Long idInscripcion) {
        Map<String, Object> respuesta = resumenInscripcionMqService.enviarResumenACola(idInscripcion);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/consumir-guardar")
    public ResponseEntity<ResumenInscripcionMq> consumirYGuardarResumen() {
        ResumenInscripcionMq resumenGuardado = resumenInscripcionMqService.consumirYGuardarResumen();
        return ResponseEntity.ok(resumenGuardado);
    }

    @GetMapping("/guardados")
    public ResponseEntity<List<ResumenInscripcionMq>> listarResumenesGuardados() {
        List<ResumenInscripcionMq> resumenes = resumenInscripcionMqService.listarResumenesGuardados();
        return ResponseEntity.ok(resumenes);
    }
}