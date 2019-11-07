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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import login.GetAlert;
import login.LoginId;
import patient.PatternUtil;

public class UpdateScheduleController {
	
	IScheduleService scheduleService;
	ObservableList<ScheduleVO> data = FXCollections.observableArrayList();
	
	// These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
    
	private ScheduleVO scheduleVo;
	
    public ScheduleVO getScheduleVo() {
		return scheduleVo;
	}

	public void setScheduleVo(ScheduleVO scheduleVo) {
		this.scheduleVo = scheduleVo;
	}
	
	public void setTableView(TableView<ScheduleVO> tableView) {
		this.tableView = tableView;
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
    private JFXButton completeButton;

    @FXML
    private JFXButton cancelButton;
    
    private TableView<ScheduleVO> tableView;
    
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
    
    //일정 변경 저장
    @FXML
    void save(MouseEvent event) throws RemoteException, ParseException {
    	if(scheKind.getSelectionModel().isEmpty()) {
    		getAlert.info("일정 변경", "일정 종류를 선택하지 않았습니다.");
    		return;
    	} 
    	
//    	boolean regResult = checkInfo();
//    	if(regResult) {
        	ScheduleVO scheVo = new ScheduleVO();
        	int doctor_num = Integer.parseInt(LoginId.login_Id);
        	scheVo.setDoctor_num(doctor_num);
        	scheVo.setSche_name(scheName.getText());
        	
        	scheVo.setSche_kind(scheKind.getSelectionModel().getSelectedItem());
        	String date = daysFromStart.getText();
        	Date scheDate = new SimpleDateFormat("yy/MM/dd").parse(date);
    		String dateString = new SimpleDateFormat("yy/MM/dd").format(scheDate);
    		
        	scheVo.setSche_startdate(dateString);
        	scheVo.setSche_cont(scheCont.getText());
        	scheVo.setSche_num(scheduleVo.getSche_num());
        	scheVo.setDoctor_num(doctor_num);
        	
        	int updateResult = scheduleService.updateSchedule(scheVo);
        	
        	if(updateResult >= 1) {
        		getAlert.info("일정 변경", "선택한 일정이 변경되었습니다.");
        		
                ArrayList<ScheduleVO> scheduleList = null;
                try {
        			scheduleList = scheduleService.getAllSchedule(doctor_num);
        		} catch (RemoteException e) {
        			e.printStackTrace();
        		}
        		data = FXCollections.observableArrayList(scheduleList);
        		tableView.setItems(data);
        		
        	} else {
        		getAlert.info("일정 변경", "선택한 일정변경에 실패하였습니다.");
        	}
//    	}
    }//END save(MouseEvent event)
    
    public void content() {
    	scheName.setText(scheduleVo.getSche_name());
    	scheKind.setPromptText(scheduleVo.getSche_kind());
    	daysFromStart.setText(scheduleVo.getSche_startdate().substring(0, 8));
    	scheCont.setText(scheduleVo.getSche_cont());
    }
    

    @FXML
    void initialize() {
    	assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'updateSchedule.fxml'.";
        assert header != null : "fx:id=\"header\" was not injected: check your FXML file 'updateSchedule.fxml'.";
        assert topLabel != null : "fx:id=\"topLabel\" was not injected: check your FXML file 'updateSchedule.fxml'.";
        assert scheName != null : "fx:id=\"scheName\" was not injected: check your FXML file 'updateSchedule.fxml'.";
        assert scheKind != null : "fx:id=\"scheKind\" was not injected: check your FXML file 'updateSchedule.fxml'.";
        assert daysFromStart != null : "fx:id=\"daysFromStart\" was not injected: check your FXML file 'updateSchedule.fxml'.";
        assert scheCont != null : "fx:id=\"scheCont\" was not injected: check your FXML file 'updateSchedule.fxml'.";
        assert completeButton != null : "fx:id=\"completeButton\" was not injected: check your FXML file 'updateSchedule.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'updateSchedule.fxml'.";

        ObservableList<String> list = FXCollections.observableArrayList("방문 진료", "원격 진료", "세미나", "휴가", "개인 일정");
        scheKind.setItems(list);
        
        try {
 			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
 			scheduleService = (IScheduleService)reg.lookup("schedule");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        
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
//    		getAlert.info("일정 변경", "일정명이 양식에 맞지 않습니다.");
//    		scheName.requestFocus();
//    		result = false;
//    		return result;
//    	}
    	
//    	if(!pattern.daysFromStart(daysFromStart.getText())) {
//    		getAlert.info("일정 변경", "일정 날짜가 양식에 맞지 않습니다.");
//    		daysFromStart.requestFocus();
//    		result = false;
//    		return result;
//    	}
    	
//    	if(!pattern.sche_cont(scheCont.getText())) {
//    		getAlert.info("일정 변경", "일정내용이 양식에 맞지 않습니다.");
//    		scheCont.requestFocus();
//    		result = false;
//    		return result;
//    	}
//    	return result;
//    }
    
    
    
    
    
    
    
    
    
}
