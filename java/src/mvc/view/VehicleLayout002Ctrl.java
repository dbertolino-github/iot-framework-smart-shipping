package mvc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mvc.SimulatorCtrl;

import java.net.URL;
import java.util.ResourceBundle;


public class VehicleLayout002Ctrl implements Initializable {

    @FXML
    private TextArea logArea2;

    @FXML
    private Label deliveryTypeLabel2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logArea2.textProperty().bind(SimulatorCtrl.getVehicle2_log_info());
        deliveryTypeLabel2.textProperty().bind(SimulatorCtrl.getVehicle2_type_info());
    }
}
