package cl.duoc.plataformaeducativa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class InscripcionRequestDTO {

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    private String estudiante;

    @NotBlank(message = "El correo del estudiante es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotEmpty(message = "Debe seleccionar al menos un curso")
    private List<Long> idsCursos;

    public InscripcionRequestDTO() {
    }

    public InscripcionRequestDTO(String estudiante, String correo, List<Long> idsCursos) {
        this.estudiante = estudiante;
        this.correo = correo;
        this.idsCursos = idsCursos;
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

    public List<Long> getIdsCursos() {
        return idsCursos;
    }

    public void setIdsCursos(List<Long> idsCursos) {
        this.idsCursos = idsCursos;
    }
}