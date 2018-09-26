package mvc.devices;

import javafx.application.Platform;
import mvc.SimulatorCtrl;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author Dario Bertolino
 */
public class Vehicle implements CommunicationInterface {

    private String idVehicle;
    private HashMap<String,IODevice> devices;
    private GPSDevice gps;

    public Vehicle(String id){
        this.devices = new HashMap<>();
        this.idVehicle = id;
        this.gps = new GPSDevice();

        IODevice tempDev =  new AnalogDevice("TemperatureSensor", temperature_device_id);
        IODevice humDev = new AnalogDevice("HumiditySensor", humidity_device_id);
        IODevice vibDev = new AnalogDevice("VibrationSensor", vibration_device_id);
        IODevice frigoDev = new DigitDevice("FrigoMotor", frigoMotor_device_id);
        IODevice dehumDev = new DigitDevice("Dehumidifier", dehumidifier_device_id);

        tempDev.setActive(false);
        humDev.setActive(false);
        vibDev.setActive(false);
        frigoDev.setActive(false);
        dehumDev.setActive(true);

        devices.put(tempDev.getDeviceID(),tempDev);
        devices.put(humDev.getDeviceID(),humDev);
        devices.put(vibDev.getDeviceID(),vibDev);
        devices.put(frigoDev.getDeviceID(),frigoDev);
        devices.put(dehumDev.getDeviceID(),dehumDev);

    }

    /**
     * The method define the Vehicle behaviour.
     * @param readLine If it starts with WRITE then a new tag will be created, else if it starts with a READ
     *                 then the first tag stored will be read.
     * @param out OK or ERROR message is returned.
     */
    @Override
    public void getMessage(String readLine, PrintWriter out){

        String[] split = readLine.split(" ");
        String commandType = split[0];

       if(SimulatorCtrl.vehicleIsRunning(getIdVehicle()) || split[1].equals(sensors_message)){
           if(commandType.equals(standard_message)){
               manageStandardMessage(split, out);
           }
           else if(commandType.equals(fragile_message)){
               manageFragileMessage(split, out);
           }
           else if(commandType.equals(frigo_message)){
               manageFrigoMessage(split, out);
           }
           else if(commandType.equals(end_message)) {
               manageEndSimulationMessage(split, out);
           }
           else{
               commandNotFound(out);
           }
       }else{
           String productID = SimulatorCtrl.getTagCarried(getIdVehicle());
           out.write("DELIVERED " + getIdVehicle() + "_" + productID + "_not_running \n");
           out.flush();
       }

    }

    private void manageStandardMessage(String[] split, PrintWriter out ){

        if(split[1].equals(sensors_message)){
            String productID = split[2];
            setSensors(1);
            SimulatorCtrl.addRunningVehicle(getIdVehicle(), productID);

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.runningVehiclesNumberToGUI();
                    SimulatorCtrl.logVehicleTypeToGUI(standard_message, getIdVehicle());

                }
            });

            System.out.println("STANDARD SENSORS");
            out.write("OK standard_sensors \n");
            out.flush();
        }
        else if(split[1].equals(get_message)){
            String latitude = getGps().getLatitude();
            String longitude = getGps().getLongitude();


            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.logVehicleToGUI("STANDARD GET" , getIdVehicle());
                }
            });

            System.out.println("STANDARD GET: " + getIdVehicle());
            out.write("OK " + latitude + "_" + longitude + " \n");
            out.flush();

        }
        else if(split[1].equals(set_message)){
            String latitude = split[2];
            String longitude = split[3];
            getGps().setPosition(latitude,longitude);

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.logVehicleToGUI("STANDARD SET: " + latitude + ", " + longitude, getIdVehicle());
                }
            });

            System.out.println("STANDARD SET: " + getIdVehicle());
            out.write("OK standard_set_correct \n");
            out.flush();
        }
        else{
            commandNotFound(out);
        }

    }

    private void manageFragileMessage(String[] split, PrintWriter out){

        if(split[1].equals(sensors_message)){
            String productID = split[2];
            setSensors(2);
            SimulatorCtrl.addRunningVehicle(getIdVehicle(), productID);

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.runningVehiclesNumberToGUI();
                    SimulatorCtrl.logVehicleTypeToGUI(fragile_message, getIdVehicle());

                }
            });

            System.out.println("FRAGILE SENSORS");
            out.write("OK fragile_sensors \n");
            out.flush();
        }
        else if(split[1].equals(get_message)){
            String latitude = getGps().getLatitude();
            String longitude = getGps().getLongitude();
            String vibrationValue = getDeviceValue(vibration_device_id);

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.logVehicleToGUI("FRAGILE GET" , getIdVehicle());
                }
            });

            System.out.println("FRAGILE GET: " + getIdVehicle());
            out.write("OK " + latitude + "_" + longitude + "_" + vibrationValue + " \n");
            out.flush();

        }
        else if(split[1].equals(set_message)){
            String latitude = split[2];
            String longitude = split[3];
            String vibrationValue = split[4];
            getGps().setPosition(latitude,longitude);
            setDeviceValue(vibration_device_id, vibrationValue);

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.logVehicleToGUI("FRAGILE SET: " + latitude + ", " + longitude + " " + vibrationValue, getIdVehicle());
                }
            });

            System.out.println("FRAGILE SET: " + latitude + " " + longitude+ " " + vibrationValue + " " + getIdVehicle());
            out.write("OK fragile_set_correct \n");
            out.flush();
        }
        else{
            commandNotFound(out);
        }
    }

    private void manageFrigoMessage(String[] split, PrintWriter out){

        if(split[1].equals(sensors_message)){
            String productID = split[2];
            setSensors(3);
            SimulatorCtrl.addRunningVehicle(getIdVehicle(), productID);

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.addRunningVehicle(getIdVehicle(), productID);
                    SimulatorCtrl.runningVehiclesNumberToGUI();
                    SimulatorCtrl.logVehicleTypeToGUI(frigo_message, getIdVehicle());
                }
            });

            System.out.println("FRIGO SENSORS");
            out.write("OK frigo_sensors \n");
            out.flush();
        }
        else if(split[1].equals(get_simulator_message)){

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.logVehicleToGUI("FRIGO GETSIM" , getIdVehicle());
                }
            });

            System.out.println("FRIGO GETSIM");
            out.write("OK " +
                    getDeviceValue(frigoMotor_device_id) + "_" +
                    getDeviceValue(dehumidifier_device_id) + "_" +
                    getDeviceValue(temperature_device_id) + "_" +
                    getDeviceValue(humidity_device_id) + " \n");
            out.flush();
        }
        else if(split[1].equals(set_simulator_message)){
            String latitude = split[2];
            String longitude = split[3];
            String temperature = split[4];
            String humidity = split[5];
            getGps().setPosition(latitude,longitude);
            setDeviceValue(temperature_device_id, temperature);
            setDeviceValue(humidity_device_id, humidity);

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.logVehicleToGUI("FRIGO SETSIM: " + temperature + " " + humidity, getIdVehicle());
                }
            });

            System.out.println("FRIGO SETSIM: " + temperature + " " + humidity);
            out.write("OK frigo_simulator_set_correct \n");
            out.flush();

        }
        else if(split[1].equals(get_message)){

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.logVehicleToGUI("FRIGO GET", getIdVehicle());
                }
            });

            System.out.println("FRIGO GET");
            out.write("OK " +
                    getGps().getLatitude() + "_" +
                    getGps().getLongitude() + "_" +
                    getDeviceValue(frigoMotor_device_id) + "_" +
                    getDeviceValue(dehumidifier_device_id) + "_" +
                    getDeviceValue(temperature_device_id) + "_" +
                    getDeviceValue(humidity_device_id) + " \n");
            out.flush();

        }
        else if(split[1].equals(set_message)){
            String frigoMotorValue = split[2];
            String dehumidifierValue = split[3];
            setDeviceValue(frigoMotor_device_id, frigoMotorValue);
            setDeviceValue(dehumidifier_device_id, dehumidifierValue);

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.logVehicleToGUI("FRIGO SET: " + frigoMotorValue + " " + dehumidifierValue, getIdVehicle());
                }
            });

            System.out.println("FRIGO SET: " + frigoMotorValue + " " + dehumidifierValue);
            out.write("OK frigo_set_correct \n");
            out.flush();
        }
        else{
            commandNotFound(out);
        }
    }

    private void manageEndSimulationMessage(String[] split,PrintWriter out ){
        String productID = split[1];
        SimulatorCtrl.removeRunningVehicle(getIdVehicle());

        Platform.runLater(new Runnable() {
            @Override public void run() {

                SimulatorCtrl.runningVehiclesNumberToGUI();
                SimulatorCtrl.arrivedVehiclesNumberToGUI();
                SimulatorCtrl.logDeliveredToGUI(productID, getIdVehicle());
            }
        });

        System.out.println("VEHICLE " + getIdVehicle() + " PRODUCT DELIVERED: " + productID);
        out.write("DELIVERED product_" + productID +"_delivered \n");
        out.flush();
    }

    private void commandNotFound(PrintWriter out){

        Platform.runLater(new Runnable() {
            @Override public void run() {
                SimulatorCtrl.logVehicleToGUI(command_not_found,getIdVehicle());
            }
        });

        System.out.println(command_not_found + " " + getIdVehicle());
        out.write(command_not_found + " \n");
        out.flush();
    }

    private void setSensors(Integer n){
        switch(n){
            case 1:
                devices.get(temperature_device_id).setActive(false);
                devices.get(humidity_device_id).setActive(false);
                devices.get(vibration_device_id).setActive(false);
                devices.get(frigoMotor_device_id).setActive(false);
                devices.get(dehumidifier_device_id).setActive(false);
                break;
            case 2:
                devices.get(temperature_device_id).setActive(false);
                devices.get(humidity_device_id).setActive(false);
                devices.get(vibration_device_id).setActive(true);
                devices.get(frigoMotor_device_id).setActive(false);
                devices.get(dehumidifier_device_id).setActive(false);
                break;
            case 3:
                devices.get(temperature_device_id).setActive(true);
                setDeviceValue(temperature_device_id, "0");
                devices.get(humidity_device_id).setActive(true);
                setDeviceValue(humidity_device_id, "75");
                devices.get(vibration_device_id).setActive(false);
                devices.get(frigoMotor_device_id).setActive(true);
                setDeviceValue(frigoMotor_device_id, "false");
                devices.get(dehumidifier_device_id).setActive(true);
                setDeviceValue(dehumidifier_device_id,"false");
                break;
        }
    }

    /**
     * Getter method.
     * @return vehicle identificator.
     */
    public String getIdVehicle() {
        return idVehicle;
    }

    /**
     * This method convert a string value to the correct value of the device.
     * @param deviceID device identificator.
     * @param newValue new value for the device.
     */
    public void setDeviceValue(String deviceID, String newValue){

        if(deviceID.equals(frigoMotor_device_id) || deviceID.equals(dehumidifier_device_id)){
            Boolean booleanValue = Boolean.valueOf(newValue);
            devices.get(deviceID).setValue(booleanValue);
        }else{
            Integer integerValue = Integer.valueOf(newValue);
            devices.get(deviceID).setValue(integerValue);
        }
    }

    /**
     * This method get the device value as a string.
     * @param deviceID device identificator.
     * @return A string that represent the device value.
     */
    public String getDeviceValue(String deviceID) {
        return devices.get(deviceID).getValueString();
    }

    /**
     * This method grant access to the GPS device of the Vehicle.
     * @return a GPSDevice.
     */
    public GPSDevice getGps(){
        return this.gps;
    }

}
