package cl.duoc.plataformaeducativa.evaluacion;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "RESULTADOS_EVALUACION")
public class ResultadoEvaluacion {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "resultado_evaluacion_seq"
    )
    @SequenceGenerator(
            name = "resultado_evaluacion_seq",
            sequenceName = "SEQ_RESULTADO_EVALUACION",
            allocationSize = 1
    )
    @Column(name = "ID_RESULTADO")
    private Long id;

    @Column(name = "ESTUDIANTE_ID", nullable = false, length = 100)
    private String estudianteId;

    @Column(name = "RESPUESTAS", nullable = false, length = 4000)
    private String respuestas;

    @Column(name = "NOTA")
    private Double nota;

    @Column(name = "RETROALIMENTACION", length = 1000)
    private String retroalimentacion;

    @Column(name = "ESTADO", nullable = false, length = 30)
    private String estado;

    @Column(name = "FECHA_ENVIO", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "FECHA_CALIFICACION")
    private LocalDateTime fechaCalificacion;

    @ManyToOne
    @JoinColumn(name = "ID_EVALUACION", nullable = false)
    private Evaluacion evaluacion;

    public ResultadoEvaluacion() {
    }

    @PrePersist
    public void prepararRegistro() {

        if (fechaEnvio == null) {
            fechaEnvio = LocalDateTime.now();
        }

        if (estado == null || estado.isBlank()) {
            estado = "ENVIADO";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(String estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(String respuestas) {
        this.respuestas = respuestas;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDateTime getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(LocalDateTime fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }
}