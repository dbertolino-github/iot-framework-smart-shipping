package mvc.driver;

import mvc.devices.Vehicle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This thread aim is to serve a request coming to the Vehicle,
 * it gets the message through a BufferedReader and then the message itself
 * will be managed by the Vehicle class.
 * When the requests is served, the Thread dies.
 * @author Dario Bertolino
 */
public class TCPVehicleInstance implements Runnable {

    private Socket vehicleSocket;
    private Vehicle vehicle;

    public TCPVehicleInstance(Socket s, Vehicle v) {
        this.vehicleSocket = s;
        this.vehicle = v;

    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(vehicleSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(vehicleSocket.getInputStream()));

            Boolean b = true;
            while(b){
                String readLine = in.readLine();
                if (readLine != null) {
                    this.vehicle.getMessage(readLine, out);
                    b = false;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
