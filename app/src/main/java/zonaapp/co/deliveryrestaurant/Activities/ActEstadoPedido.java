package zonaapp.co.deliveryrestaurant.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import zonaapp.co.deliveryrestaurant.Adapters.AdapterEstadoPedido;
import zonaapp.co.deliveryrestaurant.Entities.ListPedidoEstado;
import zonaapp.co.deliveryrestaurant.R;

public class ActEstadoPedido extends AppCompatActivity {
    private SwipeMenuListView listView;
    private AlertDialog alertDialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_estado_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActEstadoPedido.this, ActMenu.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        listView = (SwipeMenuListView) findViewById(R.id.listView);
        alertDialog = new SpotsDialog(this, R.style.Custom);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enviarPedido();
    }

    public void enviarPedido() {

        alertDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "estadoPedido");
        RequestQueue rq = Volley.newRequestQueue(this);

        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(ActEstadoPedido.this, "Problemas al recuperar la información", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                params.put("idTelefono", telephonyManager.getDeviceId());

                return params;
            }
        };
        rq.add(jsonRequest);
    }

    private boolean parseJSON(String json) {

        if (!json.equals("[]")) {
            try {
                Gson gson = new Gson();
                ListPedidoEstado pedidos = gson.fromJson(json, ListPedidoEstado.class);
                AdapterEstadoPedido adapter = new AdapterEstadoPedido(this, pedidos);
                listView.setAdapter(adapter);
                alertDialog.dismiss();

                return true;
            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            }
        } else {
            alertDialog.dismiss();
            Toast.makeText(ActEstadoPedido.this, "Problemas al recuperar la información", Toast.LENGTH_LONG).show();
        }

        return false;

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActEstadoPedido.this, ActMenu.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
        super.onBackPressed();  // optional depending on your needs
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ActEstadoPedido Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://zonaapp.co.deliveryrestaurant.Activities/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ActEstadoPedido Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://zonaapp.co.deliveryrestaurant.Activities/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
