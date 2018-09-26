package mvc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mvc.SimulatorCtrl;

import java.net.URL;
import java.util.ResourceBundle;


public class VehicleLayout004Ctrl implements Initializable {

    @FXML
    private TextArea logArea4;

    @FXML
    private Label deliveryTypeLabel4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logArea4.textProperty().bind(SimulatorCtrl.getVehicle4_log_info());
        deliveryTypeLabel4.textProperty().bind(SimulatorCtrl.getVehicle4_type_info());
    }
}
