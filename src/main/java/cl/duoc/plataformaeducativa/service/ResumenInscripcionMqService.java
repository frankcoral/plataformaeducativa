package cl.duoc.plataformaeducativa.service;

import cl.duoc.plataformaeducativa.dto.ResumenInscripcionMensajeDTO;
import cl.duoc.plataformaeducativa.model.Curso;
import cl.duoc.plataformaeducativa.model.Inscripcion;
import cl.duoc.plataformaeducativa.model.ResumenInscripcionMq;
import cl.duoc.plataformaeducativa.repository.InscripcionRepository;
import cl.duoc.plataformaeducativa.repository.ResumenInscripcionMqRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResumenInscripcionMqService {

    private final RabbitTemplate rabbitTemplate;
    private final InscripcionRepository inscripcionRepository;
    private final ResumenInscripcionMqRepository resumenInscripcionMqRepository;

    @Value("${app.rabbitmq.queue}")
    private String queueName;

    @Value("${app.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    public ResumenInscripcionMqService(RabbitTemplate rabbitTemplate,
                                       InscripcionRepository inscripcionRepository,
                                       ResumenInscripcionMqRepository resumenInscripcionMqRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.inscripcionRepository = inscripcionRepository;
        this.resumenInscripcionMqRepository = resumenInscripcionMqRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> enviarResumenACola(Long idInscripcion) {
        Inscripcion inscripcion = inscripcionRepository.findById(idInscripcion)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + idInscripcion));

        ResumenInscripcionMensajeDTO mensaje = new ResumenInscripcionMensajeDTO(
                inscripcion.getId(),
                inscripcion.getEstudiante(),
                inscripcion.getCorreo(),
                inscripcion.getFechaInscripcion(),
                inscripcion.getCursos().stream()
                        .map(Curso::getNombre)
                        .toList(),
                inscripcion.getTotalPagar(),
                LocalDateTime.now()
        );

        rabbitTemplate.convertAndSend(exchangeName, routingKey, mensaje);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Resumen de inscripción enviado correctamente a RabbitMQ");
        respuesta.put("cola", queueName);
        respuesta.put("exchange", exchangeName);
        respuesta.put("routingKey", routingKey);
        respuesta.put("idInscripcion", idInscripcion);
        respuesta.put("estudiante", inscripcion.getEstudiante());
        respuesta.put("totalPagar", inscripcion.getTotalPagar());

        return respuesta;
    }

    public ResumenInscripcionMq consumirYGuardarResumen() {
        Object mensajeRecibido = rabbitTemplate.receiveAndConvert(queueName);

        if (mensajeRecibido == null) {
            throw new RuntimeException("No hay mensajes disponibles en la cola RabbitMQ");
        }

        ResumenInscripcionMensajeDTO mensaje = (ResumenInscripcionMensajeDTO) mensajeRecibido;

        ResumenInscripcionMq resumen = new ResumenInscripcionMq();
        resumen.setIdInscripcion(mensaje.getIdInscripcion());
        resumen.setEstudiante(mensaje.getEstudiante());
        resumen.setCorreo(mensaje.getCorreo());
        resumen.setFechaInscripcion(mensaje.getFechaInscripcion());
        resumen.setCursos(String.join(", ", mensaje.getCursos()));
        resumen.setTotalPagar(mensaje.getTotalPagar());
        resumen.setFechaRecepcion(LocalDateTime.now());
        resumen.setEstado("CONSUMIDO_DESDE_RABBITMQ");

        return resumenInscripcionMqRepository.save(resumen);
    }

    public java.util.List<ResumenInscripcionMq> listarResumenesGuardados() {
        return resumenInscripcionMqRepository.findAll();
    }
}