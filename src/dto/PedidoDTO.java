package dto;

public class PedidoDTO {
    private int id;
    private int idCliente;
    private int idPez;
    private int cantidadPeces;
    private int pecesEnviados;
   
    public PedidoDTO(int id, int idCliente, int idPez, int cantidadPeces, int pecesEnviados) {
        this.id = id;
        this.idCliente = idCliente;
        this.idPez = idPez;
        this.cantidadPeces = cantidadPeces;
        this.pecesEnviados = pecesEnviados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPez() {
        return idPez;
    }

    public void setIdPez(int idPez) {
        this.idPez = idPez;
    }

    public int getCantidadPeces() {
        return cantidadPeces;
    }

    public void setCantidadPeces(int cantidadPeces) {
        this.cantidadPeces = cantidadPeces;
    }

    public int getPecesEnviados() {
        return pecesEnviados;
    }

    public void setPecesEnviados(int pecesEnviados) {
        this.pecesEnviados = pecesEnviados;
    }
    
}
