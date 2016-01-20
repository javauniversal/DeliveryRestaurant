package zonaapp.co.deliveryrestaurant.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;

/**
 * Created by GerGarGa on 2015/06/18.
 */
public class Cliente implements Serializable {

    private int codigo;
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String longitud;
    private String latitud;
    private String direccion;
    private String referencia;
    private String active;
    protected String data;
    protected Bitmap photo;
    private byte[] imageByteArray;

    /* Datos static */
    static String nombreS;
    static String apellidoS;
    static String emailS;
    static String celularS;
    static String direccionS;
    static String estadoS;
    static Bitmap photoS;
    static byte[] imageByteArrayS;


    public Cliente(){}

    public Cliente(int codigo, String nombre, String apellido, String email, String celular, String longitud, String latitud, String direccion, String referencia, String active, String data) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.longitud = longitud;
        this.latitud = latitud;
        this.direccion = direccion;
        this.referencia = referencia;
        this.active = active;
        this.data = data;
    }

    public int getCodigo() {return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getCelular() { return celular; }

    public void setCelular(String celular) { this.celular = celular; }

    public String getLongitud() { return longitud; }

    public void setLongitud(String longitud) { this.longitud = longitud; }

    public String getLatitud() {return latitud;}

    public void setLatitud(String latitud) { this.latitud = latitud; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getReferencia() {return referencia;}

    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getActive() { return active; }

    public void setActive(String active) { this.active = active; }

    public String getData() {return data;}

    public void setData(String data) {
        this.data = data;
        try {
            byte[] byteData = Base64.decode(data, Base64.DEFAULT);
            this.photo = BitmapFactory.decodeByteArray(byteData, 0, byteData.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getPhoto() { return photo;}

    public byte[] getImageByteArray() {
        return imageByteArray;
    }
    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    public static byte[] getImageByteArrayS() {
        return imageByteArrayS;
    }
    public static void setImageByteArrayS(byte[] imageByteArray) {
        Cliente.imageByteArrayS = imageByteArray;
    }


    /* Metodos static */
    public static String getNombreS() { return nombreS; }

    public static String getApellidoS() { return apellidoS; }

    public static void setNombreS(String nombre) {nombreS = nombre; }

    public static void setApellidoS(String apellido) {
        apellidoS = apellido;
    }

    public static String getEmailS() {
        return emailS;
    }

    public static void setEmailS(String emailS) {
        Cliente.emailS = emailS;
    }

    public static String getCelularS() {
        return celularS;
    }

    public static void setCelularS(String celularS) {
        Cliente.celularS = celularS;
    }

    public static String getDireccionS() {
        return direccionS;
    }

    public static void setDireccionS(String direccionS) {
        Cliente.direccionS = direccionS;
    }

    public static String getEstadoS() {
        return estadoS;
    }

    public static void setEstadoS(String estadoS) {
        Cliente.estadoS = estadoS;
    }

}
