package zonaapp.co.deliveryrestaurant.Entities;

import com.google.gson.annotations.SerializedName;

public class Adiciones {

    @SerializedName("idadicionales")
    private int idadicionales;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("valor")
    private double valor;

    @SerializedName("estado")
    private int estado;

    @SerializedName("idproductos")
    private int idproductos;

    private int idSede;

    private int idEmpresa;

    private int idCarritoCompra;

    private int AutoIncrementAdicion;

    public Adiciones(){}

    public Adiciones(int idadicionales, String descripcion, String tipo, double valor, int estado, int idproductos, int idSede, int idEmpresa) {
        this.idadicionales = idadicionales;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.valor = valor;
        this.estado = estado;
        this.idproductos = idproductos;
        this.idSede = idSede;
        this.idEmpresa = idEmpresa;
    }



    //Metodos GET SET
    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdCarritoCompra() {
        return idCarritoCompra;
    }

    public void setIdCarritoCompra(int idCarritoCompra) {
        this.idCarritoCompra = idCarritoCompra;
    }

    public int getIdadicionales() {
        return idadicionales;
    }

    public void setIdadicionales(int idadicionales) {
        this.idadicionales = idadicionales;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdproductos() {
        return idproductos;
    }

    public void setIdproductos(int idproductos) {
        this.idproductos = idproductos;
    }

    public int getAutoIncrementAdicion() {
        return AutoIncrementAdicion;
    }

    public void setAutoIncrementAdicion(int autoIncrementAdicion) {
        AutoIncrementAdicion = autoIncrementAdicion;
    }

}
