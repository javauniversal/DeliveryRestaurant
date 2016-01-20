package zonaapp.co.deliveryrestaurant.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import zonaapp.co.deliveryrestaurant.Adapters.AppAdapter;
import zonaapp.co.deliveryrestaurant.Entities.Login;
import zonaapp.co.deliveryrestaurant.R;

import static zonaapp.co.deliveryrestaurant.Entities.Login.setLoginStatic;

public class ActMain extends AppCompatActivity {

    private TextView editUsuario;
    private TextView editPassword;
    private Button btnIngresar;
    private CoordinatorLayout coordinatorLayout;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        alertDialog = new SpotsDialog(this, R.style.Custom);

        editUsuario = (EditText) findViewById(R.id.editUsuario);

        editPassword = (EditText) findViewById(R.id.editPassword);

        btnIngresar = (Button) findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidNumber(editUsuario.getText().toString().trim())) {
                    editUsuario.setError("Campo requerido");
                    editUsuario.requestFocus();
                } else if (isValidNumber(editPassword.getText().toString().trim())){
                    editPassword.setError("Campo requerido");
                    editPassword.requestFocus();
                }else {
                    loginServices();
                }


            }
        });

    }


    private void loginServices(){
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base),"login_empleado_admin");
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
                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user", editUsuario.getText().toString().trim());
                params.put("pass", editPassword.getText().toString().trim());

                return params;
            }
        };

        rq.add(jsonRequest);
    }

    private boolean isValidNumber(String number){return number == null || number.length() == 0;}

    private boolean parseJSON(String json) {
        boolean indicant = false;
        Gson gson = new Gson();
        if (!json.equals("[]")){
            try {

                final Login login = gson.fromJson(json, Login.class);
                setLoginStatic(login);

                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.sedes_dialog, null);
                ListView listview = (ListView) dialoglayout.findViewById(R.id.list_item);
                AppAdapter adapter = new AppAdapter(this, login.getSedesLogins());
                listview.setAdapter(adapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setView(dialoglayout);
                builder.show();
                indicant = true;
                alertDialog.dismiss();
            }catch (IllegalStateException ex) {
                ex.printStackTrace();
                indicant = false;
                alertDialog.dismiss();
            }
        }else {
            //alertDialog.dismiss();
            Snackbar.make(coordinatorLayout, "Usuario/Password incorrectos", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            alertDialog.dismiss();
        }
        return indicant;
    }


}
