package zonaapp.co.deliveryrestaurant.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedioPago {

    @SerializedName("idmediopago")
    private int idmediopago;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("estado")
    private int estado;

    @SerializedName("idsedes")
    private int idsedes;

    public static List<MedioPago> medioPagoListstatic;


    //Metodo GET SET
    public int getIdmediopago() {
        return idmediopago;
    }

    public void setIdmediopago(int idmediopago) {
        this.idmediopago = idmediopago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdsedes() {
        return idsedes;
    }

    public void setIdsedes(int idsedes) {
        this.idsedes = idsedes;
    }

    public static List<MedioPago> getMedioPagoListstatic() {
        return medioPagoListstatic;
    }

    public static void setMedioPagoListstatic(List<MedioPago> medioPagoListstatic) {
        MedioPago.medioPagoListstatic = medioPagoListstatic;
    }

}
