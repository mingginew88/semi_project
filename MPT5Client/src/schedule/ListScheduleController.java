package schedule;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;

import VO.ScheduleVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.GetAlert;
import login.LoginId;

public class ListScheduleController {
	
	IScheduleService scheduleService;
	ObservableList<ScheduleVO> data = FXCollections.observableArrayList();
	
	int doctor_num = Integer.parseInt(LoginId.login_Id);
	
	// These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
    
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
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
    private Label topLabel;

    @FXML
    private TableView<ScheduleVO> tableView;

    @FXML
    private TableColumn<?, ?> scheNameCol;

    @FXML
    private TableColumn<?, ?> scheKindCol;

    @FXML
    private TableColumn<?, ?> colorCol;

    @FXML
    private TableColumn<?, ?> startCol;
    
    @FXML
    private TextArea txtAreaContent;


    @FXML
    void deleteSche(MouseEvent event) throws RemoteException {
    	ScheduleVO scheduleVo = tableView.getSelectionModel().getSelectedItem();
    	int result = scheduleService.deleteSchedule(scheduleVo.getSche_num());
    	if(result >= 1) {
    		getAlert.info("일정 삭제 시도", "선택한 일정이 삭제되었습니다.");
    		ArrayList<ScheduleVO> scheduleList = scheduleService.getAllSchedule(doctor_num);
    		 ObservableList<ScheduleVO> data = FXCollections.observableArrayList(scheduleList);
    		tableView.setItems(data);
    	} else {
    		getAlert.info("일정 삭제 시도", "선택한 일정삭제에 실패하였습니다.");
    	}
    }

    // 수정
    @FXML
    void editSche(MouseEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UpdateSchedule.fxml"));		 
			Parent root = loader.load();
			
			ScheduleVO scheduleVo = tableView.getSelectionModel().getSelectedItem();
			
			UpdateScheduleController updateScheduleController = loader.getController();
			updateScheduleController.setPrimaryStage(primaryStage);
			
			updateScheduleController.setScheduleVo(scheduleVo);
			updateScheduleController.content();
			updateScheduleController.setTableView(tableView);
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void exit(MouseEvent event) {
    	//Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

   
    @FXML
    void initialize() {
        assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'ListRules.fxml'.";
        assert topLabel != null : "fx:id=\"topLabel\" was not injected: check your FXML file 'ListRules.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'ListRules.fxml'.";
        assert scheNameCol != null : "fx:id=\"scheNameCol\" was not injected: check your FXML file 'ListRules.fxml'.";
        assert scheKindCol != null : "fx:id=\"scheKindCol\" was not injected: check your FXML file 'ListRules.fxml'.";
        assert colorCol != null : "fx:id=\"colorCol\" was not injected: check your FXML file 'ListRules.fxml'.";
        assert startCol != null : "fx:id=\"startCol\" was not injected: check your FXML file 'ListRules.fxml'.";
        assert txtAreaContent != null : "fx:id=\"txtAreaContent\" was not injected: check your FXML file 'ListSchedule.fxml'.";
        
        try {
 			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
 			scheduleService = (IScheduleService)reg.lookup("schedule");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        ArrayList<ScheduleVO> scheduleList = null;
        try {
			scheduleList = scheduleService.getAllSchedule(doctor_num);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        ObservableList<ScheduleVO> data = FXCollections.observableArrayList(scheduleList);
        
        if(!tableView.getItems().isEmpty()) {
        	tableView.getItems().clear();
        	tableView.getColumns().clear();
    	}
        
        //테이블뷰의 각 컬럼 설정
        scheNameCol.setCellValueFactory(new PropertyValueFactory<>("sche_name"));
        scheKindCol.setCellValueFactory(new PropertyValueFactory<>("sche_kind"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("sche_color"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("sche_startdate"));
       
        tableView.setItems(data);
        
        tableView.setOnMouseClicked(e->{
        	String selectedCont =tableView.getSelectionModel().getSelectedItem().getSche_cont();
        	txtAreaContent.setText(selectedCont);
        });

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
    
    
    
    
}
