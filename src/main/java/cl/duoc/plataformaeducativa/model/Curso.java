package cl.duoc.plataformaeducativa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "CURSOS")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curso_seq")
    @SequenceGenerator(name = "curso_seq", sequenceName = "SEQ_CURSO", allocationSize = 1)
    @Column(name = "ID_CURSO")
    private Long id;

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El instructor es obligatorio")
    @Column(name = "INSTRUCTOR", nullable = false, length = 100)
    private String instructor;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración debe ser mayor a 0 horas")
    @Column(name = "DURACION_HORAS", nullable = false)
    private Integer duracionHoras;

    @NotNull(message = "El costo es obligatorio")
    @Positive(message = "El costo debe ser mayor a 0")
    @Column(name = "COSTO", nullable = false)
    private Integer costo;

    public Curso() {
    }

    public Curso(Long id, String nombre, String instructor, Integer duracionHoras, Integer costo) {
        this.id = id;
        this.nombre = nombre;
        this.instructor = instructor;
        this.duracionHoras = duracionHoras;
        this.costo = costo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Integer getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }
}