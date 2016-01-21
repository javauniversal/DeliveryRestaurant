package zonaapp.co.deliveryrestaurant.Entities;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EstadoPedido {

    @SerializedName("idordencompra")
    private int idordencompra;

    @SerializedName("idempresa")
    private int idempresa;

    @SerializedName("idsede")
    private int idsede;

    @SerializedName("nombrecliente")
    private String nombrecliente;

    @SerializedName("celular")
    private String celular;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("dirreferencia")
    private String dirreferencia;

    @SerializedName("estado")
    private String estado;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("emeicel")
    private String emeicel;

    @SerializedName("empresa")
    private String empresa;

    @SerializedName("sede")
    private String sede;

    @SerializedName("medioPago")
    private String medioPago;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("valor")
    private double valor;

    @SerializedName("detalle")
    private List<DetallePedido> detallePedidoList;

    public List<DetallePedido> getDetallePedidoList() {
        return detallePedidoList;
    }

    public void setDetallePedidoList(List<DetallePedido> detallePedidoList) {
        this.detallePedidoList = detallePedidoList;
    }

    public int getIdordencompra() {
        return idordencompra;
    }

    public void setIdordencompra(int idordencompra) {
        this.idordencompra = idordencompra;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDirreferencia() {
        return dirreferencia;
    }

    public void setDirreferencia(String dirreferencia) {
        this.dirreferencia = dirreferencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEmeicel() {
        return emeicel;
    }

    public void setEmeicel(String emeicel) {
        this.emeicel = emeicel;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
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
}
