package personal.prudhvi.com.weatherreport;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Rest.Model.Main;
import personal.prudhvi.com.weatherreport.dummy.DummyContent;

/**
 * Created by prudhvi on 1/29/18.
 */

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    private String temp;
    private String pressure;
    private String humidity;
    private String min_temp;
    private String max_temp;
    private Main main;
    private String cityName;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (Main)getArguments().getSerializable("KEY_OBJECT");
        cityName = getArguments().getString("CITY_NAME");
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(cityName);
            }
        }
    //}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        ((TextView) rootView.findViewById(R.id.tempvalue)).setText(String.valueOf(main.getTemp()+"°F"));
        ((TextView) rootView.findViewById(R.id.pressurevalue)).setText(String.valueOf(main.getPressure()+"hpa"));
        ((TextView) rootView.findViewById(R.id.humidityvalue)).setText(String.valueOf(main.getHumidity()+"%"));
        ((TextView) rootView.findViewById(R.id.mintempvalue)).setText(String.valueOf(main.getTempMin()+"°F"));
        ((TextView) rootView.findViewById(R.id.maxtempvalue)).setText(String.valueOf(main.getTempMax()+"°F"));

        return rootView;
    }
}
