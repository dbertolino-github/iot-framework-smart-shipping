package mvc.devices;


public class DigitDevice extends IODevice<Boolean> {

    public DigitDevice(String name, String id) {
        super(name, id);
    }

    @Override
    public void parserAndSetValue(String valueString) {
        this.setValue(Boolean.parseBoolean(valueString));
    }

    @Override
    public void setActive(Boolean b){
        this.active = b;
        if(b == true){
            this.setValue(Boolean.TRUE);
        }else{
            this.setValue(Boolean.FALSE);
        }
    }
}
