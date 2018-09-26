package mvc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mvc.SimulatorCtrl;

import java.net.URL;
import java.util.ResourceBundle;

public class ScannerLayoutCtrl implements Initializable {

    @FXML
    private TextArea logAreaScanner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logAreaScanner.textProperty().bind(SimulatorCtrl.getScanner_log_info());
    }
}
