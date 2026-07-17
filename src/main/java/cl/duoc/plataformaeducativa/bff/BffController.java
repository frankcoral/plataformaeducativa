package cl.duoc.plataformaeducativa.bff;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/bff")
public class BffController {

    private final RestClient restClient;

    public BffController(
            RestClient.Builder restClientBuilder,
            @Value("${app.backend.base-url:http://localhost:8080}") String backendBaseUrl) {

        this.restClient = restClientBuilder
                .baseUrl(backendBaseUrl)
                .build();
    }

    @PostMapping("/inscripciones/{idInscripcion}/procesar-resumen")
    public ResponseEntity<Map<String, Object>> procesarResumen(
            @PathVariable Long idInscripcion,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        Object respuestaProductor = restClient.post()
                .uri(
                        "/api/resumenes-mq/{idInscripcion}/enviar-cola",
                        idInscripcion)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .retrieve()
                .body(Object.class);

        Object respuestaConsumidor = restClient.post()
                .uri("/api/resumenes-mq/consumir-guardar")
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .retrieve()
                .body(Object.class);

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", "Proceso BFF completado correctamente");
        respuesta.put("idInscripcion", idInscripcion);
        respuesta.put("productorRabbitMQ", respuestaProductor);
        respuesta.put("consumidorRabbitMQ", respuestaConsumidor);

        return ResponseEntity.ok(respuesta);
    }
}