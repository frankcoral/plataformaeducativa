package cl.duoc.plataformaeducativa.dto;

public class CursoResumenDTO {

    private Long id;
    private String nombre;
    private String instructor;
    private Integer duracionHoras;
    private Integer costo;

    public CursoResumenDTO() {
    }

    public CursoResumenDTO(Long id, String nombre, String instructor, Integer duracionHoras, Integer costo) {
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