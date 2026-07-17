package cl.duoc.plataformaeducativa.contenido;

import cl.duoc.plataformaeducativa.model.Curso;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "CONTENIDOS")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contenido_seq")
    @SequenceGenerator(
            name = "contenido_seq",
            sequenceName = "SEQ_CONTENIDO",
            allocationSize = 1
    )
    @Column(name = "ID_CONTENIDO")
    private Long id;

    @NotBlank(message = "El título del contenido es obligatorio")
    @Column(name = "TITULO", nullable = false, length = 150)
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "DESCRIPCION", nullable = false, length = 500)
    private String descripcion;

    @NotBlank(message = "El tipo de contenido es obligatorio")
    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @NotBlank(message = "La URL del recurso es obligatoria")
    @Column(name = "URL_RECURSO", nullable = false, length = 500)
    private String urlRecurso;

    @NotNull(message = "El curso es obligatorio")
    @ManyToOne
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    public Contenido() {
    }

    public Contenido(
            Long id,
            String titulo,
            String descripcion,
            String tipo,
            String urlRecurso,
            Curso curso) {

        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.urlRecurso = urlRecurso;
        this.curso = curso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrlRecurso() {
        return urlRecurso;
    }

    public void setUrlRecurso(String urlRecurso) {
        this.urlRecurso = urlRecurso;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}