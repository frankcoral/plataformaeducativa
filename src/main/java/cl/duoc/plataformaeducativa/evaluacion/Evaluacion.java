package cl.duoc.plataformaeducativa.evaluacion;

import cl.duoc.plataformaeducativa.model.Curso;
import jakarta.persistence.*;

@Entity
@Table(name = "EVALUACIONES")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluacion_seq")
    @SequenceGenerator(
            name = "evaluacion_seq",
            sequenceName = "SEQ_EVALUACION",
            allocationSize = 1
    )
    @Column(name = "ID_EVALUACION")
    private Long id;

    @Column(name = "TITULO", nullable = false, length = 150)
    private String titulo;

    @Column(name = "INSTRUCCIONES", nullable = false, length = 1000)
    private String instrucciones;

    @Column(name = "PUNTAJE_MAXIMO", nullable = false)
    private Integer puntajeMaximo;

    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    public Evaluacion() {
    }

    public Evaluacion(
            Long id,
            String titulo,
            String instrucciones,
            Integer puntajeMaximo,
            Boolean activo,
            Curso curso) {

        this.id = id;
        this.titulo = titulo;
        this.instrucciones = instrucciones;
        this.puntajeMaximo = puntajeMaximo;
        this.activo = activo;
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

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public Integer getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public void setPuntajeMaximo(Integer puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}