package zonaapp.co.deliveryrestaurant.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zonaapp.co.deliveryrestaurant.Entities.MenuDelivery;
import zonaapp.co.deliveryrestaurant.Entities.MenuInterface;
import zonaapp.co.deliveryrestaurant.R;

public class AdapterRecyclerMenu extends RecyclerView.Adapter<MenuInterface> {

    private Context context;
    List<MenuDelivery> menuDeliveryList;

    public AdapterRecyclerMenu(Context context, List<MenuDelivery> menuDeliveryList) {
        super();
        this.context = context;
        this.menuDeliveryList = menuDeliveryList;
    }

    @Override
    public MenuInterface onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuInterface(v, context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MenuInterface holder, final int position) {

        MenuDelivery items = menuDeliveryList.get(position);

        holder.imageView.setImageResource(items.getImgMenu());

    }

    @Override
    public int getItemCount() {
        if (menuDeliveryList == null) {
            return 0;
        } else {
            return menuDeliveryList.size();
        }
    }


}
