package mvc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mvc.SimulatorCtrl;

import java.net.URL;
import java.util.ResourceBundle;


public class VehicleLayout003Ctrl implements Initializable {

    @FXML
    private TextArea logArea3;

    @FXML
    private Label deliveryTypeLabel3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logArea3.textProperty().bind(SimulatorCtrl.getVehicle3_log_info());
        deliveryTypeLabel3.textProperty().bind(SimulatorCtrl.getVehicle3_type_info());
    }
}
