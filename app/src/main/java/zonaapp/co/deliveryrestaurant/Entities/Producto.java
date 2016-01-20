package zonaapp.co.deliveryrestaurant.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Producto {

    @SerializedName("idproductos")
    private int idproductos;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("ingredientes")
    private String ingredientes;

    @SerializedName("precio")
    private double precio;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("foto")
    private String foto;

    @SerializedName("estado")
    private int estado;

    @SerializedName("idmenumovil")
    private int idmenumovil;

    @SerializedName("adiciones")
    private List<Adiciones> adicionesList;

    @SerializedName("mpm")
    private List<MedioPago> medioPagoList;

    public List<MedioPago> getMedioPagoList() {
        return medioPagoList;
    }

    public void setMedioPagoList(List<MedioPago> medioPagoList) {
        this.medioPagoList = medioPagoList;
    }

    public static Producto productoStatic;


    public static Producto getProductoStatic() {
        return productoStatic;
    }

    public static void setProductoStatic(Producto productoStatic) {
        Producto.productoStatic = productoStatic;
    }

    public int getIdproductos() {
        return idproductos;
    }

    public void setIdproductos(int idproductos) {
        this.idproductos = idproductos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdmenumovil() {
        return idmenumovil;
    }

    public void setIdmenumovil(int idmenumovil) {
        this.idmenumovil = idmenumovil;
    }

    public List<Adiciones> getAdicionesList() {
        return adicionesList;
    }

    public void setAdicionesList(List<Adiciones> adicionesList) {
        this.adicionesList = adicionesList;
    }

}
