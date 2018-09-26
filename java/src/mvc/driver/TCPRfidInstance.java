package mvc.driver;

import mvc.devices.ScannerRFID;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This thread aim is to serve a request coming to the RFIDScanner,
 * it gets the message through a BufferedReader and then the message itself
 * will be managed by the ScannerRFID class.
 * When the requests is served, the Thread dies.
 * @author Dario Bertolino
 */
public class TCPRfidInstance implements Runnable {

    private Socket scannerSocket;
    private ScannerRFID scannerRFID;

    public TCPRfidInstance(Socket socket, ScannerRFID scanner) {
        this.scannerRFID = scanner;
        this.scannerSocket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(scannerSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(scannerSocket.getInputStream()));

            Boolean b = true;
            while(b){
                String readLine = in.readLine();
                if (readLine != null) {
                    scannerRFID.getMessage(readLine, out);
                    out.flush();
                    b = false;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
