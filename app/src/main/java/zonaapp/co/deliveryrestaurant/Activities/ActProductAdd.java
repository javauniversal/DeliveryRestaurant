package zonaapp.co.deliveryrestaurant.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.text.DecimalFormat;

import zonaapp.co.deliveryrestaurant.DataBase.DBHelper;
import zonaapp.co.deliveryrestaurant.R;

import static zonaapp.co.deliveryrestaurant.Entities.Producto.getProductoStatic;

public class ActProductAdd extends AppCompatActivity implements View.OnClickListener{

    private KenBurnsView displayImagen;
    private QuantityView cantidad;
    private TextView totalFinal;
    private DBHelper mydb;
    private EditText myComment;
    private ProgressBar progressBar2;
    private DecimalFormat format;
    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_add);

        mydb = new DBHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(getProductoStatic().getDescripcion());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        displayImagen = (KenBurnsView) findViewById(R.id.displayImagen);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        CargarImagen();

        ImageView imagenAgotado = (ImageView) findViewById(R.id.inside_imageview);
        if(getProductoStatic().getCantidad() == 0)
            imagenAgotado.setImageResource(R.drawable.agotado);

        TextView descripcion = (TextView) findViewById(R.id.descripcion);
        TextView ingredientes = (TextView) findViewById(R.id.ingredientes);
        TextView precio = (TextView) findViewById(R.id.precio);

        cantidad = (QuantityView) findViewById(R.id.quantityView_default);

        TextView preciodec = (TextView) findViewById(R.id.preciodesc);
        totalFinal = (TextView) findViewById(R.id.totalFinal);
        myComment = (EditText) findViewById(R.id.EditComment);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        descripcion.setText(getProductoStatic().getDescripcion());
        ingredientes.setText(getProductoStatic().getIngredientes());
        format = new DecimalFormat("#,###.##");

        precio.setText(String.format("Precio: %s", format.format(getProductoStatic().getPrecio())));

        preciodec.setText(String.format("Precio: %s", format.format(getProductoStatic().getPrecio())));

        totalFinal.setText(String.format("%s", getProductoStatic().getPrecio()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        CargarAdiciones();

        root = (LinearLayout) findViewById(R.id.llAdiciones); //or whatever your root control is
        cantidad.setValorTotal(getProductoStatic().getPrecio(), totalFinal, root);

    }

    public void CargarAdiciones(){

        LinearLayout pll = (LinearLayout) findViewById(R.id.paterAdiciones);

        if(getProductoStatic().getAdicionesList() != null){

            pll.setVisibility(View.VISIBLE);

            LinearLayout ll = (LinearLayout) findViewById(R.id.llAdiciones);

            int adiciones = getProductoStatic().getAdicionesList().size();
            for (int i = 0; i < adiciones; i++) {

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 70, 0, 0);

                CheckBox cb = new CheckBox(this);
                cb.setText(String.format("%1s $ %2s",getProductoStatic().getAdicionesList().get(i).getDescripcion(),format.format(getProductoStatic().getAdicionesList().get(i).getValor())));
                cb.setId(getProductoStatic().getAdicionesList().get(i).getIdadicionales());
                ll.addView(cb);
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Double totaltemporal = Double.valueOf(totalFinal.getText().toString());
                        Double totalfinal = null;
                        if (((CheckBox) v).isChecked()) {
                            for(int f = 0; f < getProductoStatic().getAdicionesList().size(); f++) {
                                if(v.getId() == getProductoStatic().getAdicionesList().get(f).getIdadicionales()){
                                    totalfinal = (totaltemporal + (getProductoStatic().getAdicionesList().get(f).getValor()*cantidad.getQuantity()));
                                    break;

                                }
                            }
                        }else{
                            for(int j = 0; j < getProductoStatic().getAdicionesList().size(); j++) {
                                if(v.getId() == getProductoStatic().getAdicionesList().get(j).getIdadicionales()){
                                    totalfinal = (totaltemporal - (getProductoStatic().getAdicionesList().get(j).getValor()*cantidad.getQuantity()));
                                    break;
                                }
                            }
                        }

                        totalFinal.setText(totalfinal+"");
                    }
                });
            }

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:

                if(cantidad.getQuantity() < 1){
                    Toast.makeText(this, "La cantidad debe ser mayor a 0", Toast.LENGTH_LONG).show();
                }else if(getProductoStatic().getCantidad() == 0) {
                    Toast.makeText(this, "El producto se encuentra agotado", Toast.LENGTH_LONG).show();
                }else if(cantidad.getQuantity() > getProductoStatic().getCantidad()) {
                    Toast.makeText(this, "La cantida que desea pedir supera la disponible", Toast.LENGTH_LONG).show();
                }else{
                    new MaterialDialog.Builder(this)
                            .title(R.string.titulo_confirmacion_pedido)
                            .content(R.string.contenido_confirmacion_pedido)
                            .positiveText(R.string.realizar_pedido)
                            .negativeText(R.string.add_producto)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    //Realizar pedido
                                    /*if (GuardarPedido()) {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("compania", getSedeStatic().getIdsedes());
                                        startActivity(new Intent(ActProductAdd.this, ActCar.class).putExtras(bundle));
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                        finish();
                                    }*/
                                }
                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    //Agregar mas productos
                                    if (GuardarPedido()) {
                                        finish();
                                    }
                                }
                            }).show();
                }

                break;
        }
    }

    private boolean GuardarPedido(){

        /*AddProductCar car = new AddProductCar();

        car.setCodeProcut(getProductoStatic().getIdproductos());
        car.setNameProduct(getProductoStatic().getDescripcion());
        car.setQuantity(cantidad.getQuantity());
        car.setValueunitary(getProductoStatic().getPrecio());

        Double temp = Double.valueOf(totalFinal.getText().toString());
        car.setValueoverall(temp);

        car.setComment(myComment.getText().toString());
        car.setUrlimagen(getProductoStatic().getFoto());
        car.setIdsede(getSedeStatic().getIdsedes());
        car.setIdcompany(getSedeStatic().getIdempresa());
        car.setNameSede(getSedeStatic().getDescripcion());

        if(getProductoStatic().getAdicionesList() != null && getProductoStatic().getAdicionesList().size() > 0){
            List<Adiciones> adicionesList = new ArrayList<>();
            for(int i = 0; i < root.getChildCount(); i++) {
                View child = root.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox)child;
                    int answer = cb.isChecked() ? 1 : 0;
                    if (answer == 1){
                        for(int f = 0; f < getProductoStatic().getAdicionesList().size(); f++) {
                            if(cb.getId() == getProductoStatic().getAdicionesList().get(f).getIdadicionales()){

                                adicionesList.add(
                                        new Adiciones(cb.getId(),
                                                getProductoStatic().getAdicionesList().get(f).getDescripcion(),
                                                getProductoStatic().getAdicionesList().get(f).getTipo(),
                                                getProductoStatic().getAdicionesList().get(f).getValor(),
                                                getProductoStatic().getAdicionesList().get(f).getEstado(),
                                                getProductoStatic().getAdicionesList().get(f).getIdproductos(),
                                                getSedeStatic().getIdsedes(),getEmpresastatic().getIdempresa()));
                                break;
                            }
                        }
                    }
                }
            }

            car.setAdicionesList(adicionesList);
        }

        if (mydb.insertProduct(car)){
            //Guardado
            return true;
        }else{
            //Problemas
            Toast.makeText(ActProductAdd.this, "Problemas al guardar en el carrito.", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;

    }

    private void CargarImagen() {
        //Setup the ImageLoader, we'll use this to display our images
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader imageLoader1 = ImageLoader.getInstance();
        imageLoader1.init(config);
        //Setup options for ImageLoader so it will handle caching for us.
        DisplayImageOptions options1 = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();

        ImageLoadingListener listener = new ImageLoadingListener(){
            @Override
            public void onLoadingStarted(String arg0, View arg1) {
                // TODO Auto-generated method stub
                progressBar2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {
                // TODO Auto-generated method stub
                progressBar2.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                progressBar2.setVisibility(View.GONE);
            }
            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                progressBar2.setVisibility(View.GONE);
            }
        };

        imageLoader1.displayImage(getProductoStatic().getFoto(), displayImagen, options1, listener);
    }

}
