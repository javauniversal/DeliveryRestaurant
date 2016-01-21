package zonaapp.co.deliveryrestaurant.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zonaapp.co.deliveryrestaurant.Adapters.AppAdapterCarrito;
import zonaapp.co.deliveryrestaurant.DataBase.DBHelper;
import zonaapp.co.deliveryrestaurant.Entities.AddProductCar;
import zonaapp.co.deliveryrestaurant.Entities.PedidoWebCabeza;
import zonaapp.co.deliveryrestaurant.R;

import static zonaapp.co.deliveryrestaurant.Entities.Login.getSedeStatic;
import static zonaapp.co.deliveryrestaurant.Entities.Producto.getProductoStatic;


public class ActCar extends AppCompatActivity implements View.OnClickListener{

    private AppAdapterCarrito mAdapter;
    private DBHelper mydb;
    private TextView total;
    private Button pedirService;
    private SwipeMenuListView mListView;
    private List<AddProductCar> mAppListPublico;
    private PedidoWebCabeza objeto = new PedidoWebCabeza();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_car);
        mydb = new DBHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(getSedeStatic().getDescripcion());
        toolbar.setNavigationIcon(R.mipmap.ic_action_cartw);
        setSupportActionBar(toolbar);

        total = (TextView) findViewById(R.id.totaltexto);
        pedirService = (Button) findViewById(R.id.pedirServices);
        pedirService.setOnClickListener(this);

        mListView = (SwipeMenuListView) findViewById(R.id.listView);

        llenarData();

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                //AddProductCar item = mAppList.get(position);
                switch (index) {
                    case 0:
                        createDialog(position);
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // other setting
        // listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createDialog(position);
                return false;
            }
        });

    }

    private void llenarData() {
        List<AddProductCar> mAppList = mydb.getProductCar(getSedeStatic().getIdempleado(), getSedeStatic().getIdsedes());
        mAdapter = new AppAdapterCarrito(ActCar.this, mAppList);
        mListView.setAdapter(mAdapter);
        sumarValoresFinales(mAppList);
        mAppListPublico = mAppList;
    }

    private void sumarValoresFinales(List<AddProductCar> data){

        if(data.size() > 0){
            double dValor = 0;

            for (int i = 0; i < data.size(); i++) {
                dValor = dValor + data.get(i).getValueoverall();
                //dValor = dValor * data.get(i).getQuantity();
            }

            pedirService.setVisibility(View.VISIBLE);

            total.setText( "Total: $"+dValor);
        }else{
            pedirService.setVisibility(View.GONE);
        }

    }

    private void createDialog(final int position){
        new MaterialDialog.Builder(ActCar.this)
                .title("Eliminar producto")
                .content("Esta seguro de eliminar del carrito")
                .positiveText("Aceptar")
                .negativeText("Cancelar")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //Aceptar
                        if (!mydb.DeleteProduct(mAppListPublico.get(position).getIdProduct(), mAppListPublico.get(position).getIdcompany())) {
                            Toast.makeText(getApplicationContext(), "Problemas al eliminar el producto", Toast.LENGTH_SHORT).show();
                        } else {
                            mAppListPublico.remove(position);
                            mAdapter.notifyDataSetChanged();
                            sumarValoresFinales(mAppListPublico);
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        //Cancelar
                        dialog.dismiss();
                    }
                }).show();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();
        /*
        if (id == R.id.action_left) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            return true;
        }
        if (id == R.id.action_right) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pedirServices:
                enviarPedido();
                break;
        }
    }

    public void enviarPedido(){

        String url = String.format("%1$s%2$s", getString(R.string.url_base),"setPedidoLocal");
        RequestQueue rq = Volley.newRequestQueue(this);

        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response

                        Toast.makeText(ActCar.this, response, Toast.LENGTH_LONG).show();

                        if(mydb.DeleteProductAll(getSedeStatic().getIdempleado(), getSedeStatic().getIdsedes())){

                            Toast.makeText(ActCar.this, response, Toast.LENGTH_LONG).show();

                            startActivity(new Intent(ActCar.this, ActEstadoPedido.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();

                        }else{
                            Toast.makeText(ActCar.this, "Problemas con el pedido.", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

                objeto.setImeiPhone(telephonyManager.getDeviceId());
                objeto.setNombreUsuairo("ESTABLECIMIENTO");
                objeto.setCelularp("ESTABLECIMIENTO");
                objeto.setDireccionp("ESTABLECIMIENTO");
                objeto.setDireccionReferen("ESTABLECIMIENTO");
                objeto.setMedioPago(getProductoStatic().getMedioPagoList().get(0).getIdmediopago());

                objeto.setValorPago(0.0);
                objeto.setLatitud(Double.valueOf(0.0));
                objeto.setLongitud(Double.valueOf(0.0));
                objeto.setIdEmpresaP(getSedeStatic().getIdempleado());
                objeto.setIdSedeP(getSedeStatic().getIdsedes());

                List<AddProductCar> mAppList = mydb.getProductCar(getSedeStatic().getIdempleado(), getSedeStatic().getIdsedes());

                for (int i = 0; i < mAppList.size(); i++) {
                    mAppList.get(i).setAdicionesList(mydb.getAdiciones(mAppList.get(i).getIdProduct(),mAppList.get(i).getIdcompany(), mAppList.get(i).getIdsede()));
                }

                objeto.setProducto(mAppList);
                String parJSON = new Gson().toJson(objeto, PedidoWebCabeza.class);

                params.put("pedido", parJSON);

                return params;
            }
        };
        rq.add(jsonRequest);
    }
}
