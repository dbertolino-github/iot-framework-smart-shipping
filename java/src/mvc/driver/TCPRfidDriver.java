package mvc.driver;

import mvc.devices.ScannerRFID;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This thread open a ServerSocket on port 9005
 * and it launches a TCPRfidInstance every time
 * a request is received, to serve it.
 * @author Dario Bertolino
 */
public class TCPRfidDriver implements Runnable {

    private ScannerRFID scannerRFID;
    private ServerSocket serverSocket;

    public TCPRfidDriver(ScannerRFID s) {
        scannerRFID = s;
    }

    @Override
    public void run() {
        startRfidScannerInstance();
    }


    private void startRfidScannerInstance() {
        try {
            serverSocket = new ServerSocket(9005);
            while(true){
                Socket scannerSocket = serverSocket.accept();
                TCPRfidInstance instance = new TCPRfidInstance(scannerSocket, scannerRFID);
                Thread thread = new Thread(instance);
                thread.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
