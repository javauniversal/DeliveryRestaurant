package zonaapp.co.deliveryrestaurant.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login {

    @SerializedName("idempleado")
    private int idempleado;

    @SerializedName("cedula")
    private String cedula;

    @SerializedName("nombre_completo")
    private String nombre_completo;

    @SerializedName("idperfil")
    private int idperfil;

    @SerializedName("idplanes")
    private int idplanes;

    @SerializedName("sedes")
    private List<SedesLogin> sedesLogins;

    public static Login loginStatic;

    public static SedesLogin sedeStatic;

    public static SedesLogin getSedeStatic() {
        return sedeStatic;
    }

    public static void setSedeStatic(SedesLogin sedeStatic) {
        Login.sedeStatic = sedeStatic;
    }

    public static Login getLoginStatic() {
        return loginStatic;
    }

    public static void setLoginStatic(Login loginStatic) {
        Login.loginStatic = loginStatic;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public int getIdperfil() {
        return idperfil;
    }

    public void setIdperfil(int idperfil) {
        this.idperfil = idperfil;
    }

    public int getIdplanes() {
        return idplanes;
    }

    public void setIdplanes(int idplanes) {
        this.idplanes = idplanes;
    }

    public List<SedesLogin> getSedesLogins() {
        return sedesLogins;
    }

    public void setSedesLogins(List<SedesLogin> sedesLogins) {
        this.sedesLogins = sedesLogins;
    }
}
