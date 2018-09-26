package mvc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mvc.SimulatorCtrl;

import java.net.URL;
import java.util.ResourceBundle;


public class VehicleLayout001Ctrl implements Initializable {

    @FXML
    private TextArea logArea1;

    @FXML
    private Label deliveryTypeLabel1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logArea1.textProperty().bind(SimulatorCtrl.getVehicle1_log_info());
        deliveryTypeLabel1.textProperty().bind(SimulatorCtrl.getVehicle1_type_info());
    }
}
