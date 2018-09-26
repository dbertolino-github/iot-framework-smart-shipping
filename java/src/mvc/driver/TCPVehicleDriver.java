package mvc.driver;

import mvc.devices.Vehicle;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This thread open a ServerSocket on a port decided by constructor
 * and it launches a TCPVehicleInstance every time
 * a request is received, to serve it.
 * @author Dario Bertolino
 */
public class TCPVehicleDriver implements Runnable{


    private ServerSocket serverSocket;
    private Vehicle vehicle;
    private Integer portNumber;


    public TCPVehicleDriver(Vehicle v, Integer port){
        this.vehicle = v;
        this.portNumber = port;
    }

    @Override
    public void run() {
        startVehiclesInstance();
    }

    private void startVehiclesInstance() {

        try {
            serverSocket = new ServerSocket(this.portNumber);
            while(true){
                Socket vehicleSocket = serverSocket.accept();
                TCPVehicleInstance instance = new TCPVehicleInstance(vehicleSocket, this.vehicle);
                Thread thread = new Thread(instance);
                thread.start();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
