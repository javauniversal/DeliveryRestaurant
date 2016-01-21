package zonaapp.co.deliveryrestaurant.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zonaapp.co.deliveryrestaurant.Activities.ActEstadoPedido;
import zonaapp.co.deliveryrestaurant.Entities.EstadoPedido;
import zonaapp.co.deliveryrestaurant.R;


public class AdapterEstadoPedido extends BaseAdapter {

    private Activity actx;
    private List<EstadoPedido> data;
    private DecimalFormat format;

    public AdapterEstadoPedido(Activity actx, List<EstadoPedido> data){
        this.actx = actx;
        this.data = data;
        format = new DecimalFormat("#,###.##");
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
    public EstadoPedido getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(actx, R.layout.item_estado_pedido, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        EstadoPedido item = getItem(position);

        holder.txtEmpresa.setText(String.format("Empresa: %s",item.getEmpresa()));
        holder.txtDireccion.setText(String.format("Dirección: %s", item.getDireccion()));
        holder.txtFecha.setText(String.format("Fecha del Pedido: %s", item.getFecha()));
        holder.txtCantidad.setText(String.format("Cantidad: %s", item.getCantidad()));

        String estado = null;

        switch (item.getEstado()){
            case "Iniciado":
                holder.cancelPedido.setVisibility(View.VISIBLE);
                estado = "Iniciado";
                holder.txtEstado.setTextColor(Color.parseColor("#088A08"));
                break;
            case "En proceso":
                estado = "En proceso";
                holder.cancelPedido.setVisibility(View.GONE);
                holder.txtEstado.setTextColor(Color.parseColor("#AEB404"));
                break;
            case "Alistado":
                estado = "Alistado";
                holder.cancelPedido.setVisibility(View.GONE);
                holder.txtEstado.setTextColor(Color.parseColor("#0000FF"));
                break;
            case "Enviado":
                estado = "Enviado";
                holder.cancelPedido.setVisibility(View.GONE);
                holder.txtEstado.setTextColor(Color.parseColor("#FF8000"));
                break;
            case "Finalizado":
                estado = "Finalizado";
                holder.cancelPedido.setVisibility(View.GONE);
                holder.txtEstado.setTextColor(Color.parseColor("#F20505"));
                break;

        }

        holder.txtEstado.setText(String.format("Estado del pedido: %s", estado));

        holder.cancelPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPedido(data.get(position).getEmeicel(), data.get(position).getIdordencompra(),data.get(position).getIdsede());
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(actx);
                dialog.setContentView(R.layout.my_layout_carrito_dialog);

                dialog.setTitle(Html.fromHtml("<font color='#000'>Detalle del Pedido</font>"));

                TableLayout tabla = (TableLayout) dialog.findViewById(R.id.tabla);

                TableLayout cabecera = (TableLayout) dialog.findViewById(R.id.cabecera);

                TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);

                int anchoTable = (int) actx.getResources().getDimension(R.dimen.ancho_tabla);

                int anchoTablePrecio = (int) actx.getResources().getDimension(R.dimen.ancho_table_precio);

                TableRow.LayoutParams layoutProducto = new TableRow.LayoutParams(anchoTable,TableRow.LayoutParams.MATCH_PARENT);
                TableRow.LayoutParams layoutCantidad = new TableRow.LayoutParams(anchoTablePrecio,TableRow.LayoutParams.MATCH_PARENT);
                TableRow.LayoutParams layoutPrecio = new TableRow.LayoutParams(anchoTable,TableRow.LayoutParams.MATCH_PARENT);

                TableRow filaCa;
                TextView txtProductoCa;
                TextView txtCantidadCa;
                TextView txtPrecioCa;

                filaCa = new TableRow(actx);
                filaCa.setLayoutParams(layoutFila);

                txtProductoCa = new TextView(actx);
                txtCantidadCa = new TextView(actx);
                txtPrecioCa = new TextView(actx);


                txtProductoCa.setText("PRODUCTO");
                txtProductoCa.setGravity(Gravity.CENTER_HORIZONTAL);
                txtProductoCa.setBackgroundResource(R.drawable.tabla_celda_cabecera);
                txtProductoCa.setTypeface(null, Typeface.BOLD);
                txtProductoCa.setTextSize(12);
                txtProductoCa.setTextColor(Color.parseColor("#FFFFFF"));
                txtProductoCa.setLayoutParams(layoutProducto);

                txtCantidadCa.setText("CANT");
                txtCantidadCa.setGravity(Gravity.CENTER_HORIZONTAL);
                txtCantidadCa.setBackgroundResource(R.drawable.tabla_celda_cabecera);
                txtCantidadCa.setTypeface(null, Typeface.BOLD);
                txtCantidadCa.setTextSize(12);
                txtCantidadCa.setTextColor(Color.parseColor("#FFFFFF"));
                txtCantidadCa.setLayoutParams(layoutCantidad);

                txtPrecioCa.setText("PRECIO");
                txtPrecioCa.setGravity(Gravity.CENTER_HORIZONTAL);
                txtPrecioCa.setTypeface(null, Typeface.BOLD);
                txtPrecioCa.setTextSize(12);
                txtPrecioCa.setTextColor(Color.parseColor("#FFFFFF"));
                txtPrecioCa.setBackgroundResource(R.drawable.tabla_celda_cabecera);
                txtPrecioCa.setLayoutParams(layoutPrecio);


                filaCa.addView(txtProductoCa);
                filaCa.addView(txtCantidadCa);
                filaCa.addView(txtPrecioCa);

                cabecera.addView(filaCa);

                //Cargar Las Filas...
                TableRow row;
                TextView txtProductoFil;
                TextView txtCantidadFil;
                TextView txtPrecioFil;
                TextView txtTotal;

                double valorTotal = 0;

                for (int i = 0; i < data.get(position).getDetallePedidoList().size(); i++) {

                    double valorCantida = 0;

                    row = new TableRow(actx);
                    row.setLayoutParams(layoutFila);

                    txtProductoFil = new TextView(actx);
                    txtCantidadFil = new TextView(actx);
                    txtPrecioFil = new TextView(actx);

                    txtProductoFil.setText(data.get(position).getDetallePedidoList().get(i).getNombreproducto());
                    txtProductoFil.setGravity(Gravity.CENTER_HORIZONTAL);
                    txtProductoFil.setBackgroundResource(R.drawable.tabla_celda);
                    txtProductoFil.setTextSize(10);
                    txtProductoFil.setLayoutParams(layoutProducto);

                    txtCantidadFil.setText(data.get(position).getDetallePedidoList().get(i).getCantidad()+"");
                    txtCantidadFil.setGravity(Gravity.CENTER_HORIZONTAL);
                    txtCantidadFil.setTextSize(10);
                    txtCantidadFil.setBackgroundResource(R.drawable.tabla_celda);
                    txtCantidadFil.setLayoutParams(layoutCantidad);

                    valorCantida = data.get(position).getDetallePedidoList().get(i).getValor() * data.get(position).getDetallePedidoList().get(i).getCantidad();

                    txtPrecioFil.setText(String.format("$ %s",format.format(valorCantida)));
                    txtPrecioFil.setGravity(Gravity.CENTER_HORIZONTAL);
                    txtPrecioFil.setTextSize(10);
                    txtPrecioFil.setBackgroundResource(R.drawable.tabla_celda);
                    txtPrecioFil.setLayoutParams(layoutPrecio);

                    row.addView(txtProductoFil);
                    row.addView(txtCantidadFil);
                    row.addView(txtPrecioFil);

                    tabla.addView(row);

                    valorTotal = valorTotal + valorCantida;


                }

                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.span = 2;

                row = new TableRow(actx);
                row.setLayoutParams(layoutFila);

                txtTotal = new TextView(actx);
                txtPrecioFil = new TextView(actx);

                txtTotal.setText("TOTAL:");
                txtTotal.setGravity(Gravity.RIGHT);
                txtTotal.setTextSize(12);
                txtTotal.setTypeface(null, Typeface.BOLD);
                txtTotal.setPadding(8,8,8,8);
                txtTotal.setLayoutParams(params);


                txtPrecioFil.setText(String.format("$ %s",format.format(valorTotal)));
                txtPrecioFil.setGravity(Gravity.CENTER_HORIZONTAL);
                txtTotal.setTextSize(11);
                txtTotal.setTypeface(null, Typeface.BOLD);
                txtTotal.setPadding(8,9,8,8);
                txtPrecioFil.setLayoutParams(layoutPrecio);


                row.addView(txtTotal);
                row.addView(txtPrecioFil);


                tabla.addView(row);

                dialog.show();

            }
        });

        return convertView;
    }


    public void cancelPedido(final String pedido, final int idcompra, final int idSede){
        new MaterialDialog.Builder(actx)
                .title("Alerta!")
                .content("Quiere cancelar el pedido?")
                .positiveText("Aceptar")
                .negativeText("Cancelar")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //Acción de cancelado del pedido.
                        enviarCancelar(pedido, idcompra, idSede);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void enviarCancelar(final String idpedidocel, final int idcompra, final int idSede){

        String url = String.format("%1$s%2$s", actx.getString(R.string.url_base), "CancelarPedido");
        RequestQueue rq = Volley.newRequestQueue(actx);

        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response
                        actx.startActivity(new Intent(actx, ActEstadoPedido.class));
                        actx.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        actx.finish();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(actx, "Problemas al recuperar la información", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("idTelefono", idpedidocel);
                params.put("idcompra", String.valueOf(idcompra));
                params.put("idSede", String.valueOf(idSede));

                return params;
            }
        };

        rq.add(jsonRequest);

    }

    class ViewHolder {
        TextView txtEmpresa;
        TextView txtDireccion;
        TextView txtFecha;
        TextView txtCantidad;
        TextView txtEstado;
        Button cancelPedido;

        public ViewHolder(View view) {
            txtEmpresa = (TextView) view.findViewById(R.id.txtEmpresa);
            txtDireccion = (TextView) view.findViewById(R.id.txtDireccion);
            txtFecha = (TextView) view.findViewById(R.id.txtFecha);
            txtCantidad = (TextView) view.findViewById(R.id.txtCantidad);
            txtEstado = (TextView) view.findViewById(R.id.txtEstado);
            cancelPedido = (Button) view.findViewById(R.id.btnCancel);
            view.setTag(this);
        }
    }

}
