package zonaapp.co.deliveryrestaurant;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gcm.GCMBaseIntentService;

import java.util.HashMap;
import java.util.Map;

import zonaapp.co.deliveryrestaurant.Activities.ActEstadoPedido;

public class GCMIntentService extends GCMBaseIntentService {

    private static final int NOTIF_ALERTA_ID = 1;

    public GCMIntentService(){
        super("918001884534");
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        String msg = intent.getExtras().getString("price");

        Log.d("GCM", "Mensaje: " + msg);

        notificarMensaje(context, msg);
    }

    @Override
    protected void onError(Context context, String errorId) {
        Log.d("GCM", "Error: " + errorId);
    }

    @Override
    protected void onRegistered(Context context, String regId) {
        Log.d("GCM", "onRegistered: Registrado OK.");
        //En este punto debeis obtener el usuario donde lo tengais guardado.
        //Si no teneis un sistema de login y los usuarios son an�nimos podeis simplemente almacenar el regId

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String usuario = telephonyManager.getDeviceId();

        registrarUsuario(usuario, regId, context);
    }

    @Override
    protected void onUnregistered(Context context, String s) {
        Log.d("GCM", "onUnregistered: Desregistrado OK.");
    }

    private void registrarUsuario(final String username, final String regId, final Context context){

        String url = "http://zonaapp.co/domidomi/service/notificaciones/";
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context, "Hola", Toast.LENGTH_LONG).show();
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
                params.put("tag", "usersave");
                params.put("username", username);
                params.put("gcmcode", regId);
                return params;
            }
        };
        rq.add(jsonRequest);
    }

    private void notificarMensaje(Context context, String msg){

        long[] vibrate = {100,100,200,300};
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        //.setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(bm)
                        .setContentTitle("Mensaje de Alerta")
                        .setContentText(msg)
                        .setContentInfo("Domi Domi")
                        .setVibrate(vibrate).setAutoCancel(true)
                        .setLights(Color.RED, 1, 0)
                        .setSound(alarmSound)
                        //.setPriority(Notification.PRIORITY_HIGH)
                        .setTicker("Alerta!");

        Intent notIntent = new Intent(context, ActEstadoPedido.class);
        PendingIntent contIntent = PendingIntent.getActivity(context, 0, notIntent, 0);
        mBuilder.setContentIntent(contIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());

    }
}
