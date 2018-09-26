package mvc;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import mvc.devices.ScannerRFID;
import mvc.devices.Vehicle;
import mvc.driver.TCPRfidDriver;
import javafx.application.Application;
import javafx.stage.Stage;
import mvc.driver.TCPVehicleDriver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Dario Bertolino
 */
public class SimulatorCtrl extends Application {

    //Useful static data for the simulator
    private static HashMap<String, String> written_tags;
    private static ArrayList<String> tags_read;
    private static ScannerRFID scannerRFID;
    private static HashMap<String,Vehicle> vehicles;
    private static HashMap<String,Vehicle> running_vehicles;
    private static HashMap<String, String> vehicles_carrying;
    private static Integer arrived_vehicles;

    //GUI elements
    private TabPane structure_layout;
    private VBox info_layout;
    private VBox scanner_layout;
    private VBox vehicle0_layout;
    private VBox vehicle1_layout;
    private VBox vehicle2_layout;
    private VBox vehicle3_layout;
    private VBox vehicle4_layout;
    private static Tab vehicle0Tab;
    private static Tab vehicle1Tab;
    private static Tab vehicle2Tab;
    private static Tab vehicle3Tab;
    private static Tab vehicle4Tab;

    /*
    These fields are bound to elements in the GUI.
    PS: look for initialize method in controller classes
     */
    private static StringProperty scanner_log_info;
    private static StringProperty vehicle0_log_info;
    private static StringProperty vehicle0_type_info;
    private static StringProperty vehicle1_log_info;
    private static StringProperty vehicle1_type_info;
    private static StringProperty vehicle2_log_info;
    private static StringProperty vehicle2_type_info;
    private static StringProperty vehicle3_log_info;
    private static StringProperty vehicle3_type_info;
    private static StringProperty vehicle4_log_info;
    private static StringProperty vehicle4_type_info;
    private static StringProperty running_vehicles_info;
    private static StringProperty arrived_vehicles_info;
    private static StringProperty written_tags_info;
    private static StringProperty read_tags_info;

    /**
     * JavaFX method implemented to initialize the GUI.
     * @param primaryStage First stage of the GUI.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        loadScene();
        primaryStage.setTitle("IoT simulator | eShipping");
        primaryStage.setScene(new Scene(structure_layout));
        primaryStage.show();
    }

    /**
     * JavaFX method implemented to stop all threads with the GUI.
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    /**
     * Data initialization.
     * Threads for RFIDScanner and Vehicle drivers are launched.
     * @param args
     */
    public static void main(String[] args) {

        written_tags = new HashMap<>();
        tags_read = new ArrayList<>();
        scannerRFID = new ScannerRFID();
        vehicles = new HashMap<>();
        vehicles_carrying = new HashMap<>();
        running_vehicles = new HashMap<>();
        arrived_vehicles = 0;
        scanner_log_info = new SimpleStringProperty();
        vehicle0_log_info = new SimpleStringProperty();
        vehicle1_log_info = new SimpleStringProperty();
        vehicle2_log_info = new SimpleStringProperty();
        vehicle3_log_info = new SimpleStringProperty();
        vehicle4_log_info = new SimpleStringProperty();
        vehicle0_type_info = new SimpleStringProperty("NOT RUNNING");
        vehicle1_type_info = new SimpleStringProperty("NOT RUNNING");
        vehicle2_type_info = new SimpleStringProperty("NOT RUNNING");
        vehicle3_type_info = new SimpleStringProperty("NOT RUNNING");
        vehicle4_type_info = new SimpleStringProperty("NOT RUNNING");
        running_vehicles_info = new SimpleStringProperty("RUNNING VEHICLES: 0");
        arrived_vehicles_info = new SimpleStringProperty("DELIVERED PRODUCTS: 0");
        written_tags_info = new SimpleStringProperty("TAG WRITES: 0");
        read_tags_info = new SimpleStringProperty("TAG READS: 0");

        Vehicle vehicle000 = new Vehicle("000");
        Vehicle vehicle001 = new Vehicle("001");
        Vehicle vehicle002 = new Vehicle("002");
        Vehicle vehicle003 = new Vehicle("003");
        Vehicle vehicle004 = new Vehicle("004");

        vehicles.put(vehicle000.getIdVehicle(), vehicle000);
        vehicles.put(vehicle001.getIdVehicle(), vehicle001);
        vehicles.put(vehicle002.getIdVehicle(), vehicle002);
        vehicles.put(vehicle003.getIdVehicle(), vehicle003);
        vehicles.put(vehicle004.getIdVehicle(), vehicle004);

        TCPRfidDriver scannerDriver = new TCPRfidDriver(scannerRFID);
        TCPVehicleDriver vehicle000Driver = new TCPVehicleDriver(vehicle000, 9090);
        TCPVehicleDriver vehicle001Driver = new TCPVehicleDriver(vehicle001, 9091);
        TCPVehicleDriver vehicle002Driver = new TCPVehicleDriver(vehicle002, 9092);
        TCPVehicleDriver vehicle003Driver = new TCPVehicleDriver(vehicle003, 9093);
        TCPVehicleDriver vehicle004Driver = new TCPVehicleDriver(vehicle004, 9094);

        new Thread(scannerDriver).start();
        new Thread(vehicle000Driver).start();
        new Thread(vehicle001Driver).start();
        new Thread(vehicle002Driver).start();
        new Thread(vehicle003Driver).start();
        new Thread(vehicle004Driver).start();

        launch(args);
    }

    //GETTER METHODS
    public static StringProperty getScanner_log_info(){return  scanner_log_info;}
    public static StringProperty getVehicle0_log_info(){return vehicle0_log_info;}
    public static StringProperty getVehicle1_log_info(){return vehicle1_log_info;}
    public static StringProperty getVehicle2_log_info(){return vehicle2_log_info;}
    public static StringProperty getVehicle3_log_info(){return vehicle3_log_info;}
    public static StringProperty getVehicle4_log_info(){return vehicle4_log_info;}
    public static StringProperty getVehicle0_type_info(){return vehicle0_type_info;}
    public static StringProperty getVehicle1_type_info(){return vehicle1_type_info;}
    public static StringProperty getVehicle2_type_info(){return vehicle2_type_info;}
    public static StringProperty getVehicle3_type_info(){return vehicle3_type_info;}
    public static StringProperty getVehicle4_type_info(){return vehicle4_type_info;}
    public static StringProperty getTagsReadInfo(){
        return read_tags_info;
    }
    public static StringProperty getWrittenTagsInfo(){
        return written_tags_info;
    }
    public static StringProperty getRunningVehiclesInfo() {
        return running_vehicles_info;
    }
    public static StringProperty getArrivedVehiclesInfo() {
        return arrived_vehicles_info;
    }

    //METHODS TO MANAGE DATA

    /**
     * Return a Boolean that indicates if a vehicle is running
     * @param vehicleID
     */
    public static Boolean vehicleIsRunning(String vehicleID){
        if(running_vehicles.containsKey(vehicleID)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Return the tag ID carried by the Vehicle
     * @param vehicleID
     */
    public static String getTagCarried(String vehicleID){
        String productID = vehicles_carrying.get(vehicleID);
        vehicles_carrying.remove(vehicleID);
         return productID;
    }

    /**
     * Add a vehicle instance to the running_vehicles HashMap.
     * @param vehicleID
     */
    public static void addRunningVehicle(String vehicleID, String productID){
        running_vehicles.put(vehicleID, vehicles.get(vehicleID));
        vehicles_carrying.put(vehicleID, productID);
    }

    /**
     * Remove a running vehicle instance from the running_vehicles HashMap
     * and increment the arrived_vehicles variable.
     * @param vehicleID
     */
    public static void removeRunningVehicle(String vehicleID){
        running_vehicles.remove(vehicleID);
        arrived_vehicles = arrived_vehicles +1;
    }

    /**
     * It stores tag encrypted info with the relative tag ID
     * @param productID tag identifier
     * @param tagInfo tag encrypted info
     */
    public static void writeTag(String productID, String tagInfo){
        written_tags.put(productID, tagInfo);
    }

    /**
     * Method to get encrypted info from a tag if available.
     * @return null if no tag is found.
     */
    public static String readTag(String productID){
        String encryptedInfo = written_tags.get(productID);
        tags_read.add(productID);

        return encryptedInfo;
    }

    //METHODS TO MANAGE GUI
    /**
     * Method to publish scanner log messages on GUI.
     * @param message
     */
    public static void logScannerToGUI(String message){
        if(scanner_log_info.get() == null || scanner_log_info.get().equals("")){
            scanner_log_info.set("- " + message);
        }
        else{
            scanner_log_info.set("- " + message + "\n" + scanner_log_info.get());
        }
    }

    /**
     * Method to publish scanner log messages on GUI.
     * @param message
     */
    public static void logVehicleToGUI(String message, String vehicleID){

        switch(vehicleID){
            case "000":
                if(vehicle0_log_info.get() == null || vehicle0_log_info.get().equals("") || vehicle0_log_info.get().contains("DELIVERED PRODUCT") ){
                    vehicle0_log_info.set("- " + message);
                    vehicle0Tab.setText("Vehicle 000 Running");
                }
                else{
                    vehicle0_log_info.set("- " + message + "\n" + vehicle0_log_info.get());
                }
                break;
            case "001":
                if(vehicle1_log_info.get() == null || vehicle1_log_info.get().equals("") || vehicle1_log_info.get().contains("DELIVERED PRODUCT") ){
                    vehicle1_log_info.set("- " + message);
                    vehicle1Tab.setText("Vehicle 001 Running");
                }
                else{
                    vehicle1_log_info.set("- " + message + "\n" + vehicle1_log_info.get());
                }
                break;
            case "002":
                if(vehicle2_log_info.get() == null || vehicle2_log_info.get().equals("") || vehicle2_log_info.get().contains("DELIVERED PRODUCT") ){
                    vehicle2_log_info.set("- " + message);
                    vehicle2Tab.setText("Vehicle 002 Running");
                }
                else{
                    vehicle2_log_info.set("- " + message + "\n" + vehicle2_log_info.get());
                }
                break;
            case "003":
                if(vehicle3_log_info.get() == null || vehicle3_log_info.get().equals("") || vehicle3_log_info.get().contains("DELIVERED PRODUCT") ){
                    vehicle3_log_info.set("- " + message);
                    vehicle3Tab.setText("Vehicle 003 Running");
                }
                else{
                    vehicle3_log_info.set("- " + message + "\n" + vehicle3_log_info.get());
                }
                break;
            case "004":
                if(vehicle4_log_info.get() == null || vehicle4_log_info.get().equals("") || vehicle4_log_info.get().contains("DELIVERED PRODUCT") ){
                    vehicle4_log_info.set("- " + message);
                    vehicle4Tab.setText("Vehicle 004 Running");
                }
                else{
                    vehicle4_log_info.set("- " + message + "\n" + vehicle4_log_info.get());
                }
                break;
        }
    }

    /**
     * This method is used to update the delivery type of a Vehicle in the GUI.
     * @param vehicleType
     * @param vehicleID
     */
    public static void logVehicleTypeToGUI(String vehicleType, String vehicleID){
        switch (vehicleID){
            case "000":
                vehicle0_type_info.set(vehicleType + " DELIVERY");
                break;
            case "001":
                vehicle1_type_info.set(vehicleType + " DELIVERY");
                break;
            case "002":
                vehicle2_type_info.set(vehicleType + " DELIVERY");
                break;
            case "003":
                vehicle3_type_info.set(vehicleType + " DELIVERY");
                break;
            case "004":
                vehicle4_type_info.set(vehicleType + " DELIVERY");
                break;
        }
    }

    /**
     * This method is used to update a Vehicle GUI when it delivers a product.
     * @param productID
     * @param vehicleID
     */
    public static void logDeliveredToGUI(String productID, String vehicleID){
        switch (vehicleID){
            case "000":
                vehicle0_type_info.set("NOT RUNNING");
                vehicle0_log_info.set("DELIVERED PRODUCT: " + productID);
                vehicle0Tab.setText("Vehicle 000");
                break;
            case "001":
                vehicle1_type_info.set("NOT RUNNING");
                vehicle1_log_info.set("DELIVERED PRODUCT: " + productID);
                vehicle1Tab.setText("Vehicle 001");
                break;
            case "002":
                vehicle2_type_info.set("NOT RUNNING");
                vehicle2_log_info.set("DELIVERED PRODUCT: " + productID);
                vehicle2Tab.setText("Vehicle 002");
                break;
            case "003":
                vehicle3_type_info.set("NOT RUNNING");
                vehicle3_log_info.set("DELIVERED PRODUCT: " + productID);
                vehicle3Tab.setText("Vehicle 003");
                break;
            case "004":
                vehicle4_type_info.set("NOT RUNNING");
                vehicle4_log_info.set("DELIVERED PRODUCT: " + productID);
                vehicle4Tab.setText("Vehicle 004");
                break;
        }
    }

    /**
     * Method to update written tags number in GUI
     */
    public static void writtenTagsNumberToGUI(){
        written_tags_info.set("TAG WRITES: " + written_tags.size());
    }

    /**
     * Method to update tags read number in GUI.
     */
    public static void tagsReadNumberToGUI(){
        read_tags_info.set("TAG READS: " + tags_read.size());
    }

    /**
     * Method to update running running_vehicles number in GUI
     */
    public static void runningVehiclesNumberToGUI(){
        running_vehicles_info.set("VEHICLES RUNNING: " + running_vehicles.size());
    }

    /**
     * Method to update running running_vehicles number in GUI
     */
    public static void arrivedVehiclesNumberToGUI(){
        arrived_vehicles_info.set("PRODUCTS DELIVERED: " + arrived_vehicles);
    }

    private void loadScene(){
        try {
            FXMLLoader loader0 = new FXMLLoader();
            loader0.setLocation(SimulatorCtrl.class.getClassLoader().getResource("structure_layout.fxml"));
            structure_layout = loader0.load();

            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(SimulatorCtrl.class.getClassLoader().getResource("info_layout.fxml"));
            info_layout = loader1.load();

            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(SimulatorCtrl.class.getClassLoader().getResource("scanner_layout.fxml"));
            scanner_layout = loader2.load();

            FXMLLoader loader3 = new FXMLLoader();
            loader3.setLocation(SimulatorCtrl.class.getClassLoader().getResource("vehicle_layout_000.fxml"));
            vehicle0_layout = loader3.load();

            FXMLLoader loader4 = new FXMLLoader();
            loader4.setLocation(SimulatorCtrl.class.getClassLoader().getResource("vehicle_layout_001.fxml"));
            vehicle1_layout = loader4.load();

            FXMLLoader loader5 = new FXMLLoader();
            loader5.setLocation(SimulatorCtrl.class.getClassLoader().getResource("vehicle_layout_002.fxml"));
            vehicle2_layout = loader5.load();

            FXMLLoader loader6 = new FXMLLoader();
            loader6.setLocation(SimulatorCtrl.class.getClassLoader().getResource("vehicle_layout_003.fxml"));
            vehicle3_layout = loader6.load();

            FXMLLoader loader7 = new FXMLLoader();
            loader7.setLocation(SimulatorCtrl.class.getClassLoader().getResource("vehicle_layout_004.fxml"));
            vehicle4_layout = loader7.load();

            Tab infoTab = new Tab();
            infoTab.setText("INFO");
            infoTab.setContent(info_layout);
            structure_layout.getTabs().add(infoTab);

            Tab scannerTab = new Tab();
            scannerTab.setText("Scanner RFID");
            scannerTab.setContent(scanner_layout);
            structure_layout.getTabs().add(scannerTab);

            vehicle0Tab = new Tab();
            vehicle0Tab.setText("Vehicle 000");
            vehicle0Tab.setContent(vehicle0_layout);
            structure_layout.getTabs().add(vehicle0Tab);

            vehicle1Tab = new Tab();
            vehicle1Tab.setText("Vehicle 001");
            vehicle1Tab.setContent(vehicle1_layout);
            structure_layout.getTabs().add(vehicle1Tab);

            vehicle2Tab = new Tab();
            vehicle2Tab.setText("Vehicle 002");
            vehicle2Tab.setContent(vehicle2_layout);
            structure_layout.getTabs().add(vehicle2Tab);

            vehicle3Tab = new Tab();
            vehicle3Tab.setText("Vehicle 003");
            vehicle3Tab.setContent(vehicle3_layout);
            structure_layout.getTabs().add(vehicle3Tab);

            vehicle4Tab = new Tab();
            vehicle4Tab.setText("Vehicle 004");
            vehicle4Tab.setContent(vehicle4_layout);
            structure_layout.getTabs().add(vehicle4Tab);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

