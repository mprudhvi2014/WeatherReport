package Rest.Model;

/**
 * Created by prudhvi on 1/29/18.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Clouds implements Serializable{

    @SerializedName("all")
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

}