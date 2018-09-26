package mvc.devices;


public abstract class IODevice<typeOfValue>  {

    protected String deviceID;
    protected String name;
    protected Boolean active;
    protected typeOfValue value;

    public IODevice(String name, String id) {
        this.deviceID = id;
        this.name = name;
    }

    public Boolean isActive(){
        return this.active;
    }

    public void setActive(Boolean b){ this.active = b;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public typeOfValue getValue() {
        return value;
    }

    public void setValue(typeOfValue value) {
        this.value = value;
    }

    public String getValueString() {
        return String.valueOf(value);
    }

    public abstract void parserAndSetValue(String valueString);

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

}
