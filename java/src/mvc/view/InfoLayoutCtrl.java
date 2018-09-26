package mvc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import mvc.SimulatorCtrl;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoLayoutCtrl implements Initializable {

    @FXML
    private Label runningVehiclesLabel;

    @FXML
    private Label arrivedVehiclesLabel;

    @FXML
    private Label tagWritesLabel;

    @FXML
    private Label tagReadsLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runningVehiclesLabel.textProperty().bind(SimulatorCtrl.getRunningVehiclesInfo());
        arrivedVehiclesLabel.textProperty().bind(SimulatorCtrl.getArrivedVehiclesInfo());
        tagWritesLabel.textProperty().bind(SimulatorCtrl.getWrittenTagsInfo());
        tagReadsLabel.textProperty().bind(SimulatorCtrl.getTagsReadInfo());
    }
}
