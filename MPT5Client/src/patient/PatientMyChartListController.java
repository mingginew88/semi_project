package patient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class PatientMyChartListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label lblMyInfo;

    @FXML
    private Label lblResult;

    @FXML
    private Label lblDisease;

    @FXML
    private Label lblMyPrescription;

    @FXML
    private Label lblMySche;

    @FXML
    void clickDisease(MouseEvent event) {

    }

    @FXML
    void clickMyPrescription(MouseEvent event) {

    }

    @FXML
    void clickMySche(MouseEvent event) {

    }

    @FXML
    void clickResult(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert borderPane != null : "fx:id=\"borderPane\" was not injected: check your FXML file 'PatientMyChartList.fxml'.";
        assert lblMyInfo != null : "fx:id=\"lblMyInfo\" was not injected: check your FXML file 'PatientMyChartList.fxml'.";
        assert lblResult != null : "fx:id=\"lblResult\" was not injected: check your FXML file 'PatientMyChartList.fxml'.";
        assert lblDisease != null : "fx:id=\"lblDisease\" was not injected: check your FXML file 'PatientMyChartList.fxml'.";
        assert lblMyPrescription != null : "fx:id=\"lblMyPrescription\" was not injected: check your FXML file 'PatientMyChartList.fxml'.";
        assert lblMySche != null : "fx:id=\"lblMySche\" was not injected: check your FXML file 'PatientMyChartList.fxml'.";

    }
}
