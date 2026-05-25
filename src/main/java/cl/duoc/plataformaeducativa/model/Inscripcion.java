package cl.duoc.plataformaeducativa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "INSCRIPCIONES")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inscripcion_seq")
    @SequenceGenerator(name = "inscripcion_seq", sequenceName = "SEQ_INSCRIPCION", allocationSize = 1)
    @Column(name = "ID_INSCRIPCION")
    private Long id;

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    @Column(name = "ESTUDIANTE", nullable = false, length = 100)
    private String estudiante;

    @NotBlank(message = "El correo del estudiante es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Column(name = "CORREO", nullable = false, length = 100)
    private String correo;

    @Column(name = "FECHA_INSCRIPCION", nullable = false)
    private LocalDate fechaInscripcion;

    @Column(name = "TOTAL_PAGAR", nullable = false)
    private Integer totalPagar;

    @ManyToMany
    @JoinTable(
            name = "INSCRIPCION_CURSO",
            joinColumns = @JoinColumn(name = "ID_INSCRIPCION"),
            inverseJoinColumns = @JoinColumn(name = "ID_CURSO")
    )
    private List<Curso> cursos = new ArrayList<>();

    public Inscripcion() {
    }

    public Inscripcion(Long id, String estudiante, String correo, LocalDate fechaInscripcion, Integer totalPagar, List<Curso> cursos) {
        this.id = id;
        this.estudiante = estudiante;
        this.correo = correo;
        this.fechaInscripcion = fechaInscripcion;
        this.totalPagar = totalPagar;
        this.cursos = cursos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Integer totalPagar) {
        this.totalPagar = totalPagar;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}