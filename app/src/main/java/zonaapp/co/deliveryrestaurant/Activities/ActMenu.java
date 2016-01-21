package zonaapp.co.deliveryrestaurant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zonaapp.co.deliveryrestaurant.Adapters.AdapterRecyclerMenu;
import zonaapp.co.deliveryrestaurant.Adapters.RecyclerItemClickListener;
import zonaapp.co.deliveryrestaurant.Entities.IntentIntegrator;
import zonaapp.co.deliveryrestaurant.Entities.IntentResult;
import zonaapp.co.deliveryrestaurant.Entities.MenuDelivery;
import zonaapp.co.deliveryrestaurant.Entities.Producto;
import zonaapp.co.deliveryrestaurant.R;

import static zonaapp.co.deliveryrestaurant.Entities.Login.getLoginStatic;
import static zonaapp.co.deliveryrestaurant.Entities.Login.getSedeStatic;
import static zonaapp.co.deliveryrestaurant.Entities.Producto.setProductoStatic;

public class ActMenu extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getSedeStatic().getDescripcion());
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final List<MenuDelivery> menuDeliveries = new ArrayList<>();

        menuDeliveries.add(new MenuDelivery(R.drawable.ic_settings_remote_black_48dp, "SCANEAR"));
        menuDeliveries.add(new MenuDelivery(R.drawable.ic_local_grocery_store_black_48dp, "CARRITO"));
        menuDeliveries.add(new MenuDelivery(R.drawable.ic_print_black_48dp, "IMPRIMIR"));
        menuDeliveries.add(new MenuDelivery(R.drawable.ic_settings_black_48dp, "CONFIGURACION"));
        menuDeliveries.add(new MenuDelivery(R.drawable.ic_help_outline_black_48dp, "AYUDA"));
        menuDeliveries.add(new MenuDelivery(R.drawable.ic_exit_to_app_black_48dp, "SALIR"));

        mAdapter = new AdapterRecyclerMenu(this, menuDeliveries);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                switch (menuDeliveries.get(position).getNombreItem()){
                    case "SCANEAR":

                        IntentIntegrator scanIntegrator = new IntentIntegrator(ActMenu.this);
                        scanIntegrator.initiateScan();

                        break;

                    case "SALIR":

                        new MaterialDialog.Builder(ActMenu.this)
                                .title("Cerrar la Aplicación")
                                .content("Esta seguro de Cerrar la aplicación")
                                .positiveText("Aceptar")
                                .negativeText("Cancelar")
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        quit();
                                    }
                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();

                        break;

                    case "CARRITO":
                        startActivity(new Intent(ActMenu.this, ActEstadoPedido.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        }));

    }

    public void quit() {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            String scanContent = scanningResult.getContents();
            //String scanFormat = scanningResult.getFormatName();
            if (scanContent != null){
                loadProductId(scanContent);
            }else {
                Toast toast = Toast.makeText(getApplicationContext(),"No SCAN ningun producto", Toast.LENGTH_SHORT);
                toast.show();
            }

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void loadProductId(final String id){

        String url = String.format("%1$s%2$s", getString(R.string.url_base),"getProductQR");
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(),"No se pudo conectar.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idproducto", id);
                params.put("iduser", String.valueOf(getLoginStatic().getIdempleado()));
                params.put("idsede", String.valueOf(getSedeStatic().getIdsedes()));

                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private boolean parseJSON(String json) {
        boolean indicant = false;
        Gson gson = new Gson();

        if (!json.equals("[]")){
            try {

                final Producto listProduct = gson.fromJson(json, Producto.class);
                setProductoStatic(listProduct);

                startActivity(new Intent(ActMenu.this, ActProductAdd.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                indicant = true;

            }catch (IllegalStateException ex) {
                ex.printStackTrace();
                indicant = false;
            }

        }else {
            //alertDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Problemas al recuperar la información", Toast.LENGTH_SHORT).show();
        }
        return indicant;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();  // optional depending on your needs
    }

}
