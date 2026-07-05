package cl.duoc.plataformaeducativa.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ResumenInscripcionMensajeDTO {

    private Long idInscripcion;
    private String estudiante;
    private String correo;
    private LocalDate fechaInscripcion;
    private List<String> cursos;
    private Integer totalPagar;
    private LocalDateTime fechaMensaje;

    public ResumenInscripcionMensajeDTO() {
    }

    public ResumenInscripcionMensajeDTO(Long idInscripcion, String estudiante, String correo,
                                        LocalDate fechaInscripcion, List<String> cursos,
                                        Integer totalPagar, LocalDateTime fechaMensaje) {
        this.idInscripcion = idInscripcion;
        this.estudiante = estudiante;
        this.correo = correo;
        this.fechaInscripcion = fechaInscripcion;
        this.cursos = cursos;
        this.totalPagar = totalPagar;
        this.fechaMensaje = fechaMensaje;
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

    public List<String> getCursos() {
        return cursos;
    }

    public void setCursos(List<String> cursos) {
        this.cursos = cursos;
    }

    public Integer getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Integer totalPagar) {
        this.totalPagar = totalPagar;
    }

    public LocalDateTime getFechaMensaje() {
        return fechaMensaje;
    }

    public void setFechaMensaje(LocalDateTime fechaMensaje) {
        this.fechaMensaje = fechaMensaje;
    }
}