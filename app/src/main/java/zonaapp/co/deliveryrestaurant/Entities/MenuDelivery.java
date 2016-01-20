package zonaapp.co.deliveryrestaurant.Entities;


public class MenuDelivery {

    private int imgMenu;
    private String nombreItem;

    public MenuDelivery(int imgMenu, String nombreItem) {
        this.imgMenu = imgMenu;
        this.nombreItem = nombreItem;
    }

    public int getImgMenu() {
        return imgMenu;
    }

    public void setImgMenu(int imgMenu) {
        this.imgMenu = imgMenu;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

}
