package personal.prudhvi.com.weatherreport;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Rest.Model.Main;
import Rest.Model.WeatherReport;
import Rest.RestInterface;
import personal.prudhvi.com.weatherreport.utils.CityBean;
import personal.prudhvi.com.weatherreport.utils.DataUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by prudhvi on 1/29/18.
 */

public  class WeatherAdapter
        extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private final ItemListActivity mParentActivity;
    private List<CityBean> mValues;
    private final boolean mTwoPane;
    private RestInterface mRestInterface;
    private DataUtil mDataUtil;
    private static final String TAG = ItemListActivity.class.getSimpleName();
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CityBean cityName = (CityBean) view.getTag();
            Call<WeatherReport> call = mRestInterface.getWeatherbyCityName(cityName.getTitle());
            call.enqueue(new Callback<WeatherReport>() {
                @Override
                public void onResponse(Call<WeatherReport> call, Response<WeatherReport> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        navigateToDetails(response.body().getMain(), response.body().getName());
                    }
                }

                @Override
                public void onFailure(Call<WeatherReport> call, Throwable t) {

                    Log.e("Weather", t.toString());

                }
            });

        }
    };

    private void navigateToDetails(Main main, String city){
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            ItemDetailFragment fragment = new ItemDetailFragment();
            arguments.putSerializable("KEY_OBJECT", main);
            arguments.putString("CITY_NAME", city);
            fragment.setArguments(arguments);
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(mParentActivity, ItemDetailActivity.class);
            intent.putExtra("KEY_OBJECT", main);
            intent.putExtra("CITY_NAME", city);
            mParentActivity.startActivity(intent);
        }
    }

    public WeatherAdapter(ItemListActivity parent,
                                  List<CityBean>items,
                                  boolean twoPane, RestInterface restInterface, DataUtil dataUtil) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
        mRestInterface = restInterface;
        mDataUtil = dataUtil;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mCityTempView.setText(mValues.get(position).getTempInfo());
        holder.mCityNameView.setText(mValues.get(position).getTitle());

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
        holder.itemView.setTag(mValues.get(position));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(mParentActivity).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Are you sure to Delete?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mDataUtil.removeCity(mParentActivity, position);
                                dialog.dismiss();
                                updateCities();
                                notifyDataSetChanged();
                            }
                        });
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateCities() {
        mValues = mDataUtil.getCities(mParentActivity);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mCityNameView;
        final TextView mCityTempView;

        ViewHolder(View view) {
            super(view);
            mCityNameView = (TextView) view.findViewById(R.id.id_text);
            mCityTempView = (TextView) view.findViewById(R.id.content);
        }
    }
}
