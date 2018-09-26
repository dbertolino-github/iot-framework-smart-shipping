package mvc.devices;

/**
 * @author Dario Bertolino
 */
public class GPSDevice  {

    private String[] position;

    public GPSDevice() {
        this.position = new String[2];
    }


    public String getLatitude(){
        return this.position[0];
    }

    public String getLongitude(){
        return this.position[1];
    }

    public String[] getPosition(){
        return this.position;
    }

    public void setPosition(String latitude, String longitude){
        this.position[0] = latitude;
        this.position[1] = longitude;
    }
}
