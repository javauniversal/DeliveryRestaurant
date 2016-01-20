package zonaapp.co.deliveryrestaurant.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import zonaapp.co.deliveryrestaurant.Activities.ActMenu;
import zonaapp.co.deliveryrestaurant.Entities.SedesLogin;
import zonaapp.co.deliveryrestaurant.R;

import static zonaapp.co.deliveryrestaurant.Entities.Login.setSedeStatic;

public class AppAdapter extends BaseAdapter {

    private Activity actx;
    List<SedesLogin> data;

    public AppAdapter(Activity actx, List<SedesLogin> data){
        this.actx = actx;
        this.data = data;
    }



    @Override
    public int getCount() {

        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public SedesLogin getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_list_app, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        final SedesLogin item = getItem(position);
        holder.tv_name.setText(item.getDescripcion());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSedeStatic(item);
                actx.startActivity(new Intent(actx, ActMenu.class));
                actx.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                actx.finish();
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tv_name;

        public ViewHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(this);
        }
    }

}
