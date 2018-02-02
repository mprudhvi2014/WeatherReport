package personal.prudhvi.com.weatherreport;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Rest.Model.WeatherReport;
import Rest.RestClient;
import Rest.RestInterface;
import personal.prudhvi.com.weatherreport.utils.CityBean;
import personal.prudhvi.com.weatherreport.utils.DataUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prudhvi on 1/29/18.
 */

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static RestInterface restInterface;
    private WeatherAdapter adapter;
    public static List<CityBean> cityBeanArrayList = new ArrayList<>() ;

    private DataUtil dataUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        dataUtil = new DataUtil();

        restInterface = RestClient.getClient().create(RestInterface.class);

        final View recyclerView = findViewById(R.id.item_list);
        adapter = new WeatherAdapter(this, cityBeanArrayList, mTwoPane, restInterface, dataUtil);
        ((RecyclerView) recyclerView).setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ItemListActivity.this);
                LayoutInflater li = LayoutInflater.from(ItemListActivity.this);
                View promptsView = li.inflate(R.layout.search_zip, null);
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.edtzipcode);

                final Button btnGo = (Button)promptsView.findViewById(R.id.btngo);
                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();

                btnGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Call<WeatherReport> call = restInterface.getWeatherbyZip(userInput.getText().toString()+",us");
                        call.enqueue(new Callback<WeatherReport>() {
                            @Override
                            public void onResponse(Call<WeatherReport> call, Response<WeatherReport> response) {
                                if(response.isSuccessful() && response.body() != null) {
                                    alertDialog.dismiss();
                                    CityBean cityBean = new CityBean();
                                    cityBean.setTitle(response.body().getName());
                                    cityBean.setTempInfo(String.valueOf(response.body().getMain().getTemp()));
                                    dataUtil.addCity(ItemListActivity.this,cityBean);
                                    adapter.updateCities();
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<WeatherReport> call, Throwable t) {
                                Log.e("Weather", t.toString());


                            }
                        });
                    }
                });

                // show it
                alertDialog.show();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(cityBeanArrayList == null){
            cityBeanArrayList = new ArrayList<>();
        }
        if(dataUtil != null && adapter != null && dataUtil.getCities(this)!= null && dataUtil.getCities(this).size() >0 ) {
            cityBeanArrayList.clear();
            cityBeanArrayList.addAll(dataUtil.getCities(this));
            adapter.notifyDataSetChanged();

        }
    }
}
