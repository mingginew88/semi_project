package patient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class PatientMyPageListController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label lblMyPage;

    @FXML
    private Label lblMyInfo;

    @FXML
    private Label lblMyChart;

    @FXML
    private Label lblMySche;

    @FXML
    void clickMyChart(MouseEvent event) {
    	
    }

    @FXML
    void clickMyInfo(MouseEvent event) {
    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PatientInfo.fxml"));			
			Parent root = loader.load();
			((BorderPane) lblMyInfo.getScene().getRoot()).setCenter(root);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    @FXML
    void clickMySche(MouseEvent event) {
    	
    }
    
    @FXML
    void clickDiseaseResult(MouseEvent event) {

    }

    @FXML
    void clickExaminResult(MouseEvent event) {

    }

    @FXML
    void clickPrescription(MouseEvent event) {

    }
    

    @FXML
    void initialize() {
        assert borderPane != null : "fx:id=\"borderPane\" was not injected: check your FXML file 'PatientMyPageList.fxml'.";
        assert lblMyPage != null : "fx:id=\"lblMyPage\" was not injected: check your FXML file 'PatientMyPageList.fxml'.";
        assert lblMyInfo != null : "fx:id=\"lblMyInfo\" was not injected: check your FXML file 'PatientMyPageList.fxml'.";
        assert lblMyChart != null : "fx:id=\"lblMyChart\" was not injected: check your FXML file 'PatientMyPageList.fxml'.";
        assert lblMySche != null : "fx:id=\"lblMySche\" was not injected: check your FXML file 'PatientMyPageList.fxml'.";

    }
    
    
    
    
    
}
