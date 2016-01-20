package zonaapp.co.deliveryrestaurant.Entities;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import zonaapp.co.deliveryrestaurant.R;

public class MenuInterface extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView nombreItem;

    public MenuInterface(View itemView, Context context) {
        super(itemView);

        this.imageView = (ImageView) itemView.findViewById(R.id.imgMenu);
        //this.nombreItem = (TextView) itemView.findViewById(R.id.txtMenuItem);

    }
}
