package cl.duoc.plataformaeducativa.controller;

import cl.duoc.plataformaeducativa.service.ResumenInscripcionService;
import cl.duoc.plataformaeducativa.service.S3Service;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/resumenes")
public class ArchivoResumenController {

    private final ResumenInscripcionService resumenInscripcionService;
    private final S3Service s3Service;

    public ArchivoResumenController(ResumenInscripcionService resumenInscripcionService, S3Service s3Service) {
        this.resumenInscripcionService = resumenInscripcionService;
        this.s3Service = s3Service;
    }

    @GetMapping("/{idInscripcion}/generar")
    public ResponseEntity<FileSystemResource> generarArchivoResumen(@PathVariable Long idInscripcion) {
        Path archivo = resumenInscripcionService.generarArchivoResumen(idInscripcion);
        FileSystemResource recurso = new FileSystemResource(archivo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + archivo.getFileName().toString())
                .contentType(MediaType.TEXT_PLAIN)
                .body(recurso);
    }

    @PostMapping("/{idInscripcion}/subir")
    public ResponseEntity<Map<String, Object>> subirArchivoResumen(
            @PathVariable Long idInscripcion,
            @RequestParam("archivo") MultipartFile archivo) {

        String key = s3Service.subirArchivo(idInscripcion, archivo);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Archivo subido correctamente a S3");
        respuesta.put("bucket", "plataformaeducativa-resumenes-frank");
        respuesta.put("ruta", key);

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{idInscripcion}/reemplazar")
    public ResponseEntity<Map<String, Object>> reemplazarArchivoResumen(
            @PathVariable Long idInscripcion,
            @RequestParam("archivo") MultipartFile archivo) {

        String key = s3Service.reemplazarArchivo(idInscripcion, archivo);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Archivo reemplazado correctamente en S3");
        respuesta.put("ruta", key);

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{idInscripcion}/descargar/{nombreArchivo}")
    public ResponseEntity<ByteArrayResource> descargarArchivoResumen(
            @PathVariable Long idInscripcion,
            @PathVariable String nombreArchivo) {

        ByteArrayResource recurso = s3Service.descargarArchivo(idInscripcion, nombreArchivo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo)
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(recurso.contentLength())
                .body(recurso);
    }

    @DeleteMapping("/{idInscripcion}/eliminar/{nombreArchivo}")
    public ResponseEntity<Map<String, Object>> eliminarArchivoResumen(
            @PathVariable Long idInscripcion,
            @PathVariable String nombreArchivo) {

        String mensaje = s3Service.eliminarArchivo(idInscripcion, nombreArchivo);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);

        return ResponseEntity.ok(respuesta);
    }
}