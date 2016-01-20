package zonaapp.co.deliveryrestaurant.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.List;

import zonaapp.co.deliveryrestaurant.DataBase.DBHelper;
import zonaapp.co.deliveryrestaurant.Entities.AddProductCar;
import zonaapp.co.deliveryrestaurant.R;


public class ActCar extends AppCompatActivity implements View.OnClickListener{

    //private AppAdapter mAdapter;
    private DBHelper mydb;
    private TextView total;
    private Button pedirService;
    private SwipeMenuListView mListView;
    private List<AddProductCar> mAppListPublico;
    private TextView domicilioAdd;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_car);
        mydb = new DBHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        //toolbar.setTitle(getEmpresastatic().getDescripcion());
        toolbar.setNavigationIcon(R.mipmap.ic_action_cartw);
        setSupportActionBar(toolbar);

        total = (TextView) findViewById(R.id.totaltexto);
        domicilioAdd = (TextView) findViewById(R.id.domicilioAdd);
        pedirService = (Button) findViewById(R.id.pedirServices);
        pedirService.setOnClickListener(this);

        //dialog = new SpotsDialog(ActCar.this);
        //dialog.show();

        mListView = (SwipeMenuListView) findViewById(R.id.listView);

        //domicilioAdd.setText(String.format("Domicilio: $ %s", getSedeStatic().getCosenvio()));

        llenarData();

        // step 1. create a MenuCreator
        /*SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Editar");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
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
        };*/

        // set creator
        //mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                //AddProductCar item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
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
        /*List<AddProductCar> mAppList = mydb.getProductCar(getSedeStatic().getIdempresa(), getSedeStatic().getIdsedes());
        mAdapter = new AppAdapter(ActCar.this, mAppList);
        mListView.setAdapter(mAdapter);
        sumarValoresFinales(mAppList);
        mAppListPublico = mAppList;
        dialog.dismiss();*/
    }

    private void sumarValoresFinales(List<AddProductCar> data){

        /*if(data.size() > 0){
            double dValor = 0;

            for (int i = 0; i < data.size(); i++) {
                dValor = dValor + data.get(i).getValueoverall();
                //dValor = dValor * data.get(i).getQuantity();
            }

            dValor = dValor + getSedeStatic().getCosenvio();

            if (dValor > getEmpresastatic().getValorMin()) {
                pedirService.setVisibility(View.VISIBLE);
            }else{
                pedirService.setVisibility(View.GONE);
            }

            total.setText( "Total: $"+dValor);
        }else{
            pedirService.setVisibility(View.GONE);
        }*/

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
                            //mAdapter.notifyDataSetChanged();
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
                /*startActivity(new Intent(ActCar.this, ActFinalizarPedido.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);*/
                break;
        }
    }
}
