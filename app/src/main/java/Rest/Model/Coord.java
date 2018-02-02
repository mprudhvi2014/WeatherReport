package Rest.Model;

/**
 * Created by prudhvi on 1/29/18.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coord implements Serializable{

    @SerializedName("lon")
    private Double lon;
    @SerializedName("lat")
    private Double lat;

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

}
