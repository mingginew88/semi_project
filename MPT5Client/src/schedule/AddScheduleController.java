package schedule;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import VO.ScheduleVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import login.GetAlert;
import login.LoginId;
import patient.PatternUtil;

public class AddScheduleController {
	
	IScheduleService scheduleService;
	
	// These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
    
    private ScheduleController mainController ;
    
	public void setMainController(ScheduleController mainController) {
		this.mainController = mainController ;
	}
	
	GetAlert getAlert = new GetAlert();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private HBox header;

	@FXML
	private Label topLabel;

	@FXML
	private JFXTextField scheName;

	@FXML
	private JFXComboBox<String> scheKind;

	@FXML
	private JFXTextField daysFromStart;

	@FXML
	private JFXTextField scheCont;

	@FXML
	private JFXButton addButton;

	@FXML
	private JFXButton cancelButton;

    @FXML
    void cancel(MouseEvent event) {
    	//Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void exit(MouseEvent event) {
    	//Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(MouseEvent event) throws RemoteException {
    	if(scheKind.getSelectionModel().isEmpty()) {
    		getAlert.info("일정 생성", "일정 종류를 선택하지 않았습니다.");
    		return;
    	} 
    	
//    	boolean regResult = checkInfo();
//    	if(regResult) {
    		ScheduleVO scheduleVo = new ScheduleVO();
    		int doctor_num = Integer.parseInt(LoginId.login_Id);
    		scheduleVo.setDoctor_num(doctor_num);
    		scheduleVo.setSche_name(scheName.getText());
    		scheduleVo.setSche_kind(scheKind.getSelectionModel().getSelectedItem());
    		scheduleVo.setSche_startdate(daysFromStart.getText());
    		scheduleVo.setSche_cont(scheCont.getText());
    		
        	String scheKindValue= scheKind.getSelectionModel().getSelectedItem();    	
        	scheduleVo.setSche_color("FFFFFF");
        	if(scheKindValue.equals("방문 진료")) {
        		scheduleVo.setSche_color("80DEEA");
        	} else if(scheKindValue.equals("원격 진료")) {
        		scheduleVo.setSche_color("BA68C8");
        	} else if(scheKindValue.equals("세미나")) {
        		scheduleVo.setSche_color("FF8A65");
        	} else if(scheKindValue.equals("휴가")) {
        		scheduleVo.setSche_color("FFCDD2");
        	} else if(scheKindValue.equals("개인 일정")) {
        		scheduleVo.setSche_color("A5D6A7");
        	}
        	System.out.println(scheduleVo.getSche_color());
        	int addResult = scheduleService.addSchedule(scheduleVo);
        	
        	//TODO : 성공했을시 발생하는 조건문작성하기. 실패시 에러창 띄워주기
        	if(addResult >= 1) {
        		getAlert.info("일정 생성", "새로운 일정이 추가되었습니다.");
        		//Close the window
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.close();
        	} else {
        		getAlert.info("일정 생성", "일정생성에 실패하였습니다.");
        	}
//    	} 
    }//END save(MouseEvent event)
    

    @FXML
    void initialize() {
    	assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'AddSchedule.fxml'.";
        assert header != null : "fx:id=\"header\" was not injected: check your FXML file 'AddSchedule.fxml'.";
        assert topLabel != null : "fx:id=\"topLabel\" was not injected: check your FXML file 'AddSchedule.fxml'.";
        assert scheName != null : "fx:id=\"scheName\" was not injected: check your FXML file 'AddSchedule.fxml'.";
        assert scheKind != null : "fx:id=\"scheKind\" was not injected: check your FXML file 'AddSchedule.fxml'.";
        assert daysFromStart != null : "fx:id=\"daysFromStart\" was not injected: check your FXML file 'AddSchedule.fxml'.";
        assert scheCont != null : "fx:id=\"scheCont\" was not injected: check your FXML file 'AddSchedule.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'AddSchedule.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'AddSchedule.fxml'.";

        try {
 			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
 			scheduleService = (IScheduleService)reg.lookup("schedule");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        ObservableList<String> list = FXCollections.observableArrayList("방문 진료", "원격 진료", "세미나", "휴가", "개인 일정");
        scheKind.setItems(list);
        
        
        // ******** Code below is for Draggable windows **********    
        
        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        // Change cursor when hover over draggable area
        topLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        
        // Change cursor when hover over draggable area
        topLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
    }
    
//    public boolean checkInfo() {
//    	PatternUtil pattern = new PatternUtil();
//    	boolean result = true;
//    	
//		if(!pattern.sche_name(scheName.getText())) {
//    		getAlert.info("일정 생성", "일정명이 양식에 맞지 않습니다.");
//    		scheName.requestFocus();
//    		result = false;
//    		return result;
//    	}
//    	
//    	if(!pattern.daysFromStart(daysFromStart.getText())) {
//    		getAlert.info("일정 생성", "일정 날짜가 양식에 맞지 않습니다.");
//    		daysFromStart.requestFocus();
//    		result = false;
//    		return result;
//    	}
//    	
//    	if(!pattern.sche_cont(scheCont.getText())) {
//    		getAlert.info("일정 생성", "일정내용이 양식에 맞지 않습니다.");
//    		scheCont.requestFocus();
//    		result = false;
//    		return result;
//    	}
//    	
//    	return result;
//    }
    
    
    
}
