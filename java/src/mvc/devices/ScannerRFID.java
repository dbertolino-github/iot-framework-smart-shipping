package mvc.devices;

import javafx.application.Platform;
import mvc.SimulatorCtrl;
import java.io.PrintWriter;

/**
 * @author Dario Bertolino
 */
public class ScannerRFID implements CommunicationInterface {

    /**
     * The method define the ScannerRFID behaviour.
     * @param readLine If it starts with WRITE then a new tag will be created, else if it starts with a READ
     *                 then the first tag stored will be read.
     * @param out OK or ERROR message is returned.
     */
    @Override
    public void getMessage(String readLine, PrintWriter out) {

        String[] split = readLine.split(" ");
        String command = split[0];

        if(write_message.equals(command)){
            manageWriteMessage(split, out);
        }
        else if(read_message.equals(command)){
            manageReadMessage(split, out);
        }
        else {
            commandNotFound(out);
        }
    }

    private void manageWriteMessage(String[] split, PrintWriter out){
        String productID = split[1];
        String encryptedInfo = split[2];

        SimulatorCtrl.writeTag(productID, encryptedInfo);

        Platform.runLater(new Runnable() {
            @Override public void run() {
                SimulatorCtrl.writtenTagsNumberToGUI();
            }
        });

        System.out.println("TAG WRITE: " + productID);
        SimulatorCtrl.logScannerToGUI("TAG WRITE: " + productID);
        out.write( "OK tag_write_correct \n");
        out.flush();
    }

    private void manageReadMessage(String[] split, PrintWriter out){
        String productID = split[1];
        String encryptedInfo = SimulatorCtrl.readTag(productID);
        if(encryptedInfo != null){

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    SimulatorCtrl.tagsReadNumberToGUI();
                }
            });

            System.out.println("TAG READ: " + productID);
            SimulatorCtrl.logScannerToGUI("TAG READ: " + productID);
            out.write("OK " + encryptedInfo + " \n");
            out.flush();
        }
        else {
            System.out.println(tag_not_found);
            SimulatorCtrl.logScannerToGUI(tag_not_found);
            out.write(tag_not_found + " \n");
            out.flush();
        }
    }

    private void commandNotFound(PrintWriter out){
        System.out.println(command_not_found);
        out.write(command_not_found + " \n");
        out.flush();
    }




}
