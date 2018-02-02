package personal.prudhvi.com.weatherreport.utils;

/**
 * Created by prudhvi on 1/29/18.
 */
public class CityBean {

    private int id;
    private String title;
    private String tempInfo;

    public CityBean() {
        super();
    }

    public CityBean(String title) {
        super();
        this.title = title;
    }


    public String getTempInfo() {
        return tempInfo;
    }

    public void setTempInfo(String tempInfo) {
        this.tempInfo = tempInfo;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CityBean other = (CityBean) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
