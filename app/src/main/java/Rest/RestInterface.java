package Rest;

import Rest.Model.Main;
import Rest.Model.WeatherReport;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by prudhvi on 1/29/18.
 */

public interface RestInterface {

    @GET("data/2.5/weather?appid=cc1a8df5b136eede6cfbcdb31d4274cc&units=imperial")
    Call<WeatherReport> getWeatherbyZip(@Query("zip") String zip);

    @GET("data/2.5/weather?appid=cc1a8df5b136eede6cfbcdb31d4274cc&units=imperial")
    Call<WeatherReport> getWeatherbyCityName(@Query("q") String name);
}
