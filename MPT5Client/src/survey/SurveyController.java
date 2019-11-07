package survey;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import com.jfoenix.controls.JFXTextArea;

import VO.SurveyVO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import login.GetAlert;

public class SurveyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Rating ratingStar;

    @FXML
    private JFXTextArea cont;

    @FXML
    private Label grade;

    @FXML
    void btnsurvey(MouseEvent event) throws RemoteException {
    	SurveyVO survey = new SurveyVO();
    	survey.setGrade(surveyGrade);
    	survey.setSurvey_cont(cont.getText()==null?" ":cont.getText());
    	int result = svService.insertsurvey(survey);
    	System.out.println(result);
    	if(result>0) {
    		new GetAlert().info("complete", "평가에 응해주셔서 감사합니다!");
    		cont.clear();
    	}else {
    		new GetAlert().alert("warning", "평가 저장 실패");
    	}
    }
    
    private ISurveyService svService;
    private double surveyGrade = 2;
    
    @FXML
    void initialize() {
        assert ratingStar != null : "fx:id=\"ratingStar\" was not injected: check your FXML file 'Survey.fxml'.";
        assert cont != null : "fx:id=\"cont\" was not injected: check your FXML file 'Survey.fxml'.";
        assert grade != null : "fx:id=\"grade\" was not injected: check your FXML file 'Survey.fxml'.";
        
        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
			svService = (ISurveyService) reg.lookup("survey");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        ratingStar.ratingProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				grade.setText(newValue.toString());
				surveyGrade = (double) newValue;
			}
        	
		});
        
    }
}
