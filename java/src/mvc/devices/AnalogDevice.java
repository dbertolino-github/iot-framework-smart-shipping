package mvc.devices;


public class AnalogDevice extends IODevice<Integer> {

    public AnalogDevice(String name, String id) {
        super(name, id);
        this.setValue(new Integer(0));
    }

    @Override
    public void parserAndSetValue(String valueString) {
        Integer parseInteger = Integer.parseInt(valueString);
        this.setValue(parseInteger);
    }

}