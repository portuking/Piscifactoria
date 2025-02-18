package dto;

public class PezDTO {
    private int id;
    private String nombre;
    private String nombreCientifico;
    
    public PezDTO(int id, String nombre, String nombreCientifico) {
        this.id = id;
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

}
