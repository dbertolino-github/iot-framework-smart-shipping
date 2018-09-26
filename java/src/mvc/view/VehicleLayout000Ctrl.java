package mvc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mvc.SimulatorCtrl;

import java.net.URL;
import java.util.ResourceBundle;


public class VehicleLayout000Ctrl implements Initializable {

    @FXML
    private TextArea logArea0;

    @FXML
    private Label deliveryTypeLabel0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logArea0.textProperty().bind(SimulatorCtrl.getVehicle0_log_info());
        deliveryTypeLabel0.textProperty().bind(SimulatorCtrl.getVehicle0_type_info());
    }
}
