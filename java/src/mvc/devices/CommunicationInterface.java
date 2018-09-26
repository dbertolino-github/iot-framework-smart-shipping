package mvc.devices;

import java.io.PrintWriter;

/**
 * @author Dario Bertolino
 */
public interface CommunicationInterface {

    //ScannerRFID
    String write_message = "WRITE";
    String read_message = "READ";
    String tag_not_found = "ERROR tag_not_found";

    //Vehicle
    String temperature_device_id = "TEMPERATURE";
    String humidity_device_id = "HUMIDITY";
    String vibration_device_id = "VIBRATION";
    String frigoMotor_device_id = "FRIGOMOTOR";
    String dehumidifier_device_id = "DEHUMIDIFIER";
    String standard_message = "STANDARD";
    String fragile_message = "FRAGILE";
    String frigo_message = "FRIGO";
    String end_message = "END";
    String sensors_message = "SENSORS";
    String set_message = "SET";
    String get_message = "GET";
    String set_simulator_message = "SETSIM";
    String get_simulator_message  ="GETSIM";

    //Both
    String command_not_found = "ERROR command_not_found ";
    void getMessage(String readLine, PrintWriter out);
}
