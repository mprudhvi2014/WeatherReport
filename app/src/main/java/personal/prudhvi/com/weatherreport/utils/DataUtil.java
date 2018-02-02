package personal.prudhvi.com.weatherreport.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by prudhvi on 1/29/18.
 */

public class DataUtil {

    public static final String PREFS_NAME = "WEATHER_APP";
    public static final String CITIES = "cities";

    public DataUtil() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveCities(Context context, List<CityBean> cities) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonCities = gson.toJson(cities);

        editor.putString(CITIES, jsonCities);

        editor.commit();
    }

    public void addCity(Context context, CityBean product) {
        List<CityBean> cities = getCities(context);
        if (cities == null)
            cities = new ArrayList<CityBean>();
        cities.add(product);
        saveCities(context, cities);
    }

    public void removeCity(Context context, int index) {
        ArrayList<CityBean> cities = getCities(context);
        if (cities != null) {
            cities.remove(index);
            saveCities(context, cities);
        }
    }

    public ArrayList<CityBean> getCities(Context context) {
        SharedPreferences settings;
        List<CityBean> cities;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(CITIES)) {
            String jsonFavorites = settings.getString(CITIES, null);
            Gson gson = new Gson();
            CityBean[] cityItems = gson.fromJson(jsonFavorites,
                    CityBean[].class);

            cities = Arrays.asList(cityItems);
            cities = new ArrayList<CityBean>(cities);
        } else
            return null;

        return (ArrayList<CityBean>) cities;
    }
}
