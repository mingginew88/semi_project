package patient;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import VO.NoticeVO;
import board.notice.NoticeMainController;
import board.qna.QnAMainController;
import controller.Main;
import doctor.IDoctorMainService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.ILoginService;
import login.LoginId;
import patientClinic.C_PatientClinicMainController;

public class PatientMainController {
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private ILoginService loginService;
	private IDoctorMainService dmService;
	
	public void setlblName(String name) {
    	lblName.setText(name);
    }
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblName;

    @FXML
    private Label lblMember;

    @FXML
    private ImageView imgExit;

    @FXML
    private TabPane MainTabPane;

    @FXML
    private TableView<NoticeVO> tableViewNotice;

    @FXML
    private TableColumn<?, ?> noticeNumCol;

    @FXML
    private TableColumn<?, ?> noticeTitleCol;

    @FXML
    private TableColumn<?, ?> noticeWriterCol;

    @FXML
    private TableColumn<NoticeVO, Date> noticeDateCol;

    @FXML
    private Tab TabAppt;

    @FXML
    private BorderPane tabSchedule;

    @FXML
    private Tab TabClinic;

    @FXML
    private BorderPane tabClinic;

    @FXML
    private Tab TabBoard;

    @FXML
    private Tab TabChart;
    
    @FXML
    private BorderPane tabChart;
    
    @FXML
    private BorderPane BorderPaneBoard;
    
    @FXML
    private JFXButton visitBtn;

    @FXML
    private JFXButton RMTBtn;

    @FXML
    void clickAppt(MouseEvent event) {
    	MainTabPane.getSelectionModel().select(TabAppt);
    }

    @FXML
    void clickClinic(MouseEvent event) {
    	MainTabPane.getSelectionModel().select(TabClinic);
    }

    @FXML
    void clickExit(MouseEvent event) {
        	Stage newPrimaryStage = new Stage();
        	Main main = new Main();
        	main.start(newPrimaryStage);
    		
    		Stage stage = (Stage) imgExit.getScene().getWindow();
    		stage.close();
    }

    @FXML
    void clickPaAppt(MouseEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PatientMainSearchAppt.fxml"));		 
			Parent root = loader.load();
			
			PatientMainSearchApptController psacontroller = loader.getController();
			psacontroller.setPaName(lblName.getText());
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    //설문 조사
    @FXML
    void clickSurvey(MouseEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Survey.fxml"));		 
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //게시판 - 공지사항
    @FXML
    void clickNotice(MouseEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NoticeMain.fxml"));
    		Parent root = loader.load();
    		NoticeMainController noticecontroller = loader.getController();
    		noticecontroller.setPrimaryStage(primaryStage, BorderPaneBoard);
    		BorderPaneBoard.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //게시판 - 질문 답변
    @FXML
    void clickQandA(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/QnAMain.fxml"));
			Parent root = loader.load();
			QnAMainController qnacontroller = loader.getController();
			qnacontroller.setPrimaryStage(primaryStage, BorderPaneBoard);
			BorderPaneBoard.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void rmtAppoint(ActionEvent event) throws IOException {
       Parent loaderrr = FXMLLoader.load(getClass().getResource("../view/PatientRMTAppointment.fxml"));
       tabSchedule.setCenter(loaderrr);
    }

    //나의 정보 보기
    @FXML
    void clickImgMyInfo(MouseEvent event) {
    	try {
    		Stage stage = new Stage();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PatientInfo2.fxml"));
			Parent root = loader.load();
			
			PatientInfo2Controller pi2Controller = loader.getController();
			pi2Controller.setPrimaryStage(stage);
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initOwner(primaryStage);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    

    @FXML
    void visitAppoint(ActionEvent event) throws IOException {
       Parent loaderr = FXMLLoader.load(getClass().getResource("../view/PatientVisitAppointment.fxml"));
       tabSchedule.setCenter(loaderr);
    }
    

    @FXML
    void initialize() throws IOException {
    	assert lblName != null : "fx:id=\"lblName\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert lblMember != null : "fx:id=\"lblMember\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert imgExit != null : "fx:id=\"imgExit\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert MainTabPane != null : "fx:id=\"MainTabPane\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert tableViewNotice != null : "fx:id=\"tableViewNotice\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert noticeNumCol != null : "fx:id=\"noticeNumCol\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert noticeTitleCol != null : "fx:id=\"noticeTitleCol\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert noticeWriterCol != null : "fx:id=\"noticeWriterCol\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert noticeDateCol != null : "fx:id=\"noticeDateCol\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert TabAppt != null : "fx:id=\"TabAppt\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert tabSchedule != null : "fx:id=\"tabSchedule\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert visitBtn != null : "fx:id=\"visitBtn\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert RMTBtn != null : "fx:id=\"RMTBtn\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert TabClinic != null : "fx:id=\"TabClinic\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert tabClinic != null : "fx:id=\"tabClinic\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert TabBoard != null : "fx:id=\"TabBoard\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert BorderPaneBoard != null : "fx:id=\"BorderPaneBoard\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert TabChart != null : "fx:id=\"TabChart\" was not injected: check your FXML file 'PatientMain.fxml'.";
        assert tabChart != null : "fx:id=\"tabChart\" was not injected: check your FXML file 'PatientMain.fxml'.";

        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			loginService = (ILoginService)reg.lookup("Login");
			dmService = (IDoctorMainService) reg.lookup("doctorMain");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        String pa_id = LoginId.login_Id;
        
        setNotice();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PatientClinic.fxml"));
        Parent root = loader.load();
        C_PatientClinicMainController pccontroller = loader.getController();
        pccontroller.setPrimaryStage(primaryStage, tabClinic);
        tabClinic.setCenter(root);
        
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../view/PatientChart.fxml"));
        Parent root2 = loader2.load();
        tabChart.setCenter(root2);
        
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("../view/PatientVisitAppointment.fxml"));
        Parent root3 = loader3.load();
        tabSchedule.setCenter(root3);
    }
    
    private void setNotice() throws RemoteException {
    	List<NoticeVO> noticeList = dmService.getAllNotice();
    	ObservableList<NoticeVO> noticeData = FXCollections.observableArrayList(noticeList);
    	tableViewNotice.setItems(noticeData);
    	noticeNumCol.setCellValueFactory(new PropertyValueFactory<>("notice_num"));
    	noticeTitleCol.setCellValueFactory(new PropertyValueFactory<>("notice_title"));
    	noticeWriterCol.setCellValueFactory(new PropertyValueFactory<>("notice_writer"));
    	noticeDateCol.setCellValueFactory(new PropertyValueFactory<>("notice_date"));
    	noticeDateCol.setCellFactory(column -> {
    	    TableCell<NoticeVO, Date> cell = new TableCell<NoticeVO, Date>() {
    	        private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    	        @Override
    	        protected void updateItem(Date item, boolean empty) {
    	            super.updateItem(item, empty);
    	            if(item == null || empty) {
    	                setText(null);
    	            }
    	            else {
    	                setText(format.format(item));
    	            }
    	        }
    	    };
    	    return cell;
    	});
    }
}
