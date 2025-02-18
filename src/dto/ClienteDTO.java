package dto;

public class ClienteDTO {
    private int id;
    private String nombre;
    private String nif;
    private String telefono;
    
    public ClienteDTO(int id, String nombre, String nif, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.nif = nif;
        this.telefono = telefono;
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

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
