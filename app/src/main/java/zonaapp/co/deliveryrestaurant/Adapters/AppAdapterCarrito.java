package zonaapp.co.deliveryrestaurant.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.util.List;

import zonaapp.co.deliveryrestaurant.DataBase.DBHelper;
import zonaapp.co.deliveryrestaurant.Entities.AddProductCar;
import zonaapp.co.deliveryrestaurant.Entities.Adiciones;
import zonaapp.co.deliveryrestaurant.R;

public class AppAdapterCarrito extends BaseAdapter {

    private Activity actx;
    List<AddProductCar> data;
    private ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    private DBHelper mydb;

    public AppAdapterCarrito(Activity actx, List<AddProductCar> data){
        this.actx = actx;
        this.data = data;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(actx).build();
        imageLoader1 = ImageLoader.getInstance();
        imageLoader1.init(config);

        options1 = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();

        mydb = new DBHelper(actx);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public AddProductCar getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_list_app_carrito, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        AddProductCar item = getItem(position);

        String adicion = mostrarAdiciones(item.getIdProduct(), item.getIdcompany(), item.getIdsede());

        holder.tv_name.setText(item.getNameProduct());
        holder.tv_preci.setText("Precio: $"+item.getValueunitary());
        holder.tv_cantidad.setText("Cantidad: "+item.getQuantity());

        if (adicion != null && adicion != ""){
            holder.tv_adiciones.setVisibility(View.VISIBLE);
            holder.tv_adiciones.setText(adicion);
        }else {
            holder.tv_adiciones.setVisibility(View.GONE);
        }

        CargarImagen(holder,item);
        return convertView;
    }

    public String mostrarAdiciones(int idcarrito, int idempresa, int idsede){

        List<Adiciones> adicionesList = mydb.getAdiciones(idcarrito,idempresa,idsede);

        String formato = "";

        if(adicionesList.size() > 0){
            String concatAdiciones = "";
            for (int i = 0; i < adicionesList.size(); i++) {
                concatAdiciones = concatAdiciones +" | "+ adicionesList.get(i).getDescripcion();
            }

            formato = String.format(" %1s %2s", "Adiciones:", concatAdiciones);
        }

        return formato;
    }


    private void CargarImagen(ViewHolder holder,AddProductCar item){
        ImageLoadingListener listener = new ImageLoadingListener(){
            @Override
            public void onLoadingStarted(String arg0, View arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                //holder.image.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                // TODO Auto-generated method stub
            }
        };

        imageLoader1.displayImage(item.getUrlimagen(), holder.iv_icon, options1 , listener);
    }

    class ViewHolder {

        ImageView iv_icon;
        TextView tv_name;
        TextView tv_preci;
        TextView tv_cantidad;
        TextView tv_adiciones;

        public ViewHolder(View view) {
            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_preci = (TextView) view.findViewById(R.id.tv_preci);
            tv_cantidad = (TextView) view.findViewById(R.id.tv_cantidad);
            tv_adiciones = (TextView) view.findViewById(R.id.tv_adiciones);
            view.setTag(this);
        }
    }

}
