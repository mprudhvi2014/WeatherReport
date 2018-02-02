package Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by prudhvi on 1/29/18.
 */

public class RestClient {

    public static final String API_KEY = "cc1a8df5b136eede6cfbcdb31d4274cc";
    public static final String Base_URL = "http://api.openweathermap.org/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
