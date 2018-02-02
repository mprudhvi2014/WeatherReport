package Rest.Model;

/**
 * Created by prudhvi on 1/29/18.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wind implements Serializable{

    @SerializedName("speed")
    private double speed;
    @SerializedName("deg")
    private float deg;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public float getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

}
