package zonaapp.co.deliveryrestaurant.Entities;


import com.google.gson.annotations.SerializedName;

public class DetallePedido {

    @SerializedName("iddetordencompra")
    private int iddetordencompra;

    @SerializedName("idsede")
    private int idsede;

    @SerializedName("idproducto")
    private int idproducto;

    @SerializedName("nombreproducto")
    private String nombreproducto;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("valor")
    private double valor;

    @SerializedName("comentario")
    private String comentario;

    @SerializedName("idordencompra")
    private int idordencompra;

    public int getIddetordencompra() {
        return iddetordencompra;
    }

    public void setIddetordencompra(int iddetordencompra) {
        this.iddetordencompra = iddetordencompra;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdordencompra() {
        return idordencompra;
    }

    public void setIdordencompra(int idordencompra) {
        this.idordencompra = idordencompra;
    }
}
