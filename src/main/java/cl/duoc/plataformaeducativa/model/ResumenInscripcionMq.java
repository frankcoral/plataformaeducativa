package cl.duoc.plataformaeducativa.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESUMENES_INSCRIPCION_MQ")
public class ResumenInscripcionMq {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resumen_mq_seq")
    @SequenceGenerator(name = "resumen_mq_seq", sequenceName = "SEQ_RESUMEN_MQ", allocationSize = 1)
    @Column(name = "ID_RESUMEN_MQ")
    private Long id;

    @Column(name = "ID_INSCRIPCION", nullable = false)
    private Long idInscripcion;

    @Column(name = "ESTUDIANTE", nullable = false, length = 100)
    private String estudiante;

    @Column(name = "CORREO", nullable = false, length = 100)
    private String correo;

    @Column(name = "FECHA_INSCRIPCION")
    private LocalDate fechaInscripcion;

    @Column(name = "CURSOS", length = 1000)
    private String cursos;

    @Column(name = "TOTAL_PAGAR")
    private Integer totalPagar;

    @Column(name = "FECHA_RECEPCION")
    private LocalDateTime fechaRecepcion;

    @Column(name = "ESTADO", length = 50)
    private String estado;

    public ResumenInscripcionMq() {
    }

    public Long getId() {
        return id;
    }

    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getCursos() {
        return cursos;
    }

    public void setCursos(String cursos) {
        this.cursos = cursos;
    }

    public Integer getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Integer totalPagar) {
        this.totalPagar = totalPagar;
    }

    public LocalDateTime getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDateTime fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}