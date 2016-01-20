package zonaapp.co.deliveryrestaurant.Entities;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddProductCar {

    @SerializedName("idProduct")
    private int idProduct;

    @SerializedName("codeProcut")
    private int codeProcut;

    @SerializedName("nameProduct")
    private String nameProduct;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("valueunitary")
    private Double valueunitary;

    @SerializedName("valueoverall")
    private Double valueoverall;

    @SerializedName("comment")
    private String comment;

    @SerializedName("idcompany")
    private int idcompany;

    @SerializedName("idsede")
    private int idsede;

    @SerializedName("urlimagen")
    private String urlimagen;

    @SerializedName("nameSede")
    private String nameSede;

    private int idAutoIncrement;

    private List<Adiciones> adicionesList;




    //Metodos GET SET

    public int getIdAutoIncrement() { return idAutoIncrement; }

    public void setIdAutoIncrement(int idAutoIncrement) { this.idAutoIncrement = idAutoIncrement; }

    public int getIdProduct() { return idProduct; }

    public void setIdProduct(int idProduct) { this.idProduct = idProduct; }

    public int getCodeProcut() {
        return codeProcut;
    }

    public void setCodeProcut(int codeProcut) {
        this.codeProcut = codeProcut;
    }

    public String getNameProduct() { return nameProduct; }

    public void setNameProduct(String nameProduct) { this.nameProduct = nameProduct; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getValueunitary() {
        return valueunitary;
    }

    public void setValueunitary(Double valueunitary) {
        this.valueunitary = valueunitary;
    }

    public Double getValueoverall() {
        return valueoverall;
    }

    public void setValueoverall(Double valueoverall) {
        this.valueoverall = valueoverall;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(int idcompany) {
        this.idcompany = idcompany;
    }

    public String getUrlimagen() { return urlimagen; }

    public void setUrlimagen(String urlimagen) { this.urlimagen = urlimagen; }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public List<Adiciones> getAdicionesList() {
        return adicionesList;
    }

    public void setAdicionesList(List<Adiciones> adicionesList) {
        this.adicionesList = adicionesList;
    }

    public String getNameSede() {
        return nameSede;
    }

    public void setNameSede(String nameSede) {
        this.nameSede = nameSede;
    }

}
