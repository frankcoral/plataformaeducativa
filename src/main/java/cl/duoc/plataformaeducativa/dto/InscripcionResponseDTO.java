package cl.duoc.plataformaeducativa.dto;

import java.time.LocalDate;
import java.util.List;

public class InscripcionResponseDTO {

    private Long idInscripcion;
    private String estudiante;
    private String correo;
    private LocalDate fechaInscripcion;
    private List<CursoResumenDTO> cursos;
    private Integer totalPagar;

    public InscripcionResponseDTO() {
    }

    public InscripcionResponseDTO(Long idInscripcion, String estudiante, String correo,
                                  LocalDate fechaInscripcion, List<CursoResumenDTO> cursos,
                                  Integer totalPagar) {
        this.idInscripcion = idInscripcion;
        this.estudiante = estudiante;
        this.correo = correo;
        this.fechaInscripcion = fechaInscripcion;
        this.cursos = cursos;
        this.totalPagar = totalPagar;
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

    public List<CursoResumenDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoResumenDTO> cursos) {
        this.cursos = cursos;
    }

    public Integer getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Integer totalPagar) {
        this.totalPagar = totalPagar;
    }
}