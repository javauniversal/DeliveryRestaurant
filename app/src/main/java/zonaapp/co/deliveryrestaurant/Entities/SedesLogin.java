package zonaapp.co.deliveryrestaurant.Entities;


import com.google.gson.annotations.SerializedName;

public class SedesLogin {

    @SerializedName("idempleado")
    private int idempleado;

    @SerializedName("idsedes")
    private int idsedes;

    @SerializedName("descripcion")
    private String descripcion;

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public int getIdsedes() {
        return idsedes;
    }

    public void setIdsedes(int idsedes) {
        this.idsedes = idsedes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "SedesLogin{" +
                "descripcion='" + descripcion + '\'' +
                '}';
    }
}
