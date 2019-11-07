package doctor;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import VO.AppointmentVO;
import VO.DoctorVO;
import VO.MessageVO;
import VO.NoticeVO;
import VO.ScheduleVO;
import board.notice.NoticeMainController;
import board.qna.QnAMainController;
import controller.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import login.LoginController;
import login.LoginId;
import msg.IMsgService;
import msg.MsgController;
import msg.MsgWriteController;

public class DoctorMainController {
	
	private DoctorVO doctorVo;
	
    public DoctorVO getDoctorVo() {
		return doctorVo;
	}

	public void setDoctorVo(DoctorVO doctorVo) {
		this.doctorVo = doctorVo;
	}
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
    
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
    private ImageView imgMsg;

    @FXML
    private Tab TabSche;

    @FXML
    private Tab TabClinic;

    @FXML
    private BorderPane tabClinic;
    
    @FXML
    private BorderPane tabSchedule;

    @FXML
    private Label DoctorVisitClinic;

    @FXML
    private Label DoctorVisitClinic1;

    @FXML
    private Tab TabBoard;
    
    @FXML
    private TabPane MainTabPane;
    
    @FXML
    private TableView<ScheduleVO> tableVIewSche;

    @FXML
    private TableColumn<ScheduleVO, String> scheKindCol;

    @FXML
    private TableColumn<ScheduleVO, String> scheDateCol;

    @FXML
    private TableColumn<ScheduleVO, String> scheNameCol;

    @FXML
    private TableColumn<ScheduleVO, String> scheContentCol;

    @FXML
    private TableView<AppointmentVO> tableViewAppt;

    @FXML
    private TableColumn<AppointmentVO, Date> apptDateCol;

    @FXML
    private TableColumn<AppointmentVO, String> apptPaNameCol;

    @FXML
    private TableColumn<AppointmentVO, String> apptKindCol;

    @FXML
    private TableView<NoticeVO> tableViewNotice;

    @FXML
    private TableColumn<?, ?> noticeNumCol;

    @FXML
    private TableColumn<?, ?> noticeTitleCol;

    @FXML
    private TableColumn<?, ?> noticeWirterCol;

    @FXML
    private TableColumn<NoticeVO, Date> noticeDateCol;
    

    @FXML
    void clickExit(MouseEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));

		try {
			thboolean = false;
			Parent root = loader.load();
			Stage child = new Stage(StageStyle.DECORATED);
			child.initModality(Modality.APPLICATION_MODAL);
			child.initOwner(primaryStage);

			Scene scene = new Scene(root);
			child.setScene(scene);
			child.show();
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Stage stage = (Stage) imgExit.getScene().getWindow();
		stage.close();
    }

    @FXML
    void clickMsg(MouseEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MsgMain.fxml"));		 
			Parent root = loader.load();
			
			MsgController msgController = loader.getController();
			msgController.setPrimaryStage(primaryStage);
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void viewDoctorVisitClinic(MouseEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("../view/DoctorVisitClinic.fxml"));
		tabClinic.setCenter(loader);
    }
    
    @FXML
    void viewDoctorClinic(MouseEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("../view/DoctorClinic.fxml"));
		tabClinic.setCenter(loader);
    }
    
    @FXML
    void viewDataSearchClinic(MouseEvent event) throws IOException {
		Parent loader = FXMLLoader.load(getClass().getResource("../view/SearchData.fxml"));
		tabClinic.setCenter(loader);
    }
    
    @FXML
    void ivbboard(MouseEvent event) {
    	MainTabPane.getSelectionModel().select(TabBoard);
    }

    @FXML
    void ivbclinic(MouseEvent event) {
    	MainTabPane.getSelectionModel().select(TabClinic);
    }

    @FXML
    void ivbmypage(MouseEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/DoctorInfo.fxml"));		 
			Parent root = loader.load();
			
			DoctorInfoController doctorInfoController = loader.getController();
			doctorInfoController.setPrimaryStage(primaryStage);
			
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
    void ivbsche(MouseEvent event) {
    	MainTabPane.getSelectionModel().select(TabSche);
    }
    
    @FXML
    private BorderPane BorderPaneBoard;
    
    @FXML
    void clickNotice(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NoticeMain.fxml"));
		Parent root = loader.load();
		NoticeMainController noticecontroller = loader.getController();
		noticecontroller.setPrimaryStage(primaryStage, BorderPaneBoard);
		BorderPaneBoard.setCenter(root);
    }

    @FXML
    void clickQandA(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/QnAMain.fxml"));
		Parent root = loader.load();
		QnAMainController qnacontroller = loader.getController();
		qnacontroller.setPrimaryStage(primaryStage, BorderPaneBoard);
		BorderPaneBoard.setCenter(root);
    }

    private IDoctorMainService dmService;
    private boolean thboolean = true;
    
    @FXML
    void initialize() throws NotBoundException, IOException {
    	assert lblName != null : "fx:id=\"lblName\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert lblMember != null : "fx:id=\"lblMember\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert imgExit != null : "fx:id=\"imgExit\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert imgMsg != null : "fx:id=\"imgMsg\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert MainTabPane != null : "fx:id=\"MainTabPane\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert tableVIewSche != null : "fx:id=\"tableVIewSche\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert scheKindCol != null : "fx:id=\"scheKindCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert scheDateCol != null : "fx:id=\"scheDateCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert scheNameCol != null : "fx:id=\"scheNameCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert scheContentCol != null : "fx:id=\"scheContentCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert tableViewAppt != null : "fx:id=\"tableViewAppt\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert apptDateCol != null : "fx:id=\"apptDateCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert apptPaNameCol != null : "fx:id=\"apptPaNameCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert apptKindCol != null : "fx:id=\"apptKindCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert tableViewNotice != null : "fx:id=\"tableViewNotice\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert noticeNumCol != null : "fx:id=\"noticeNumCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert noticeTitleCol != null : "fx:id=\"noticeTitleCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert noticeWirterCol != null : "fx:id=\"noticeWirterCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert noticeDateCol != null : "fx:id=\"noticeDateCol\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert TabSche != null : "fx:id=\"TabSche\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert tabSchedule != null : "fx:id=\"tabSchedule\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert TabClinic != null : "fx:id=\"TabClinic\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert tabClinic != null : "fx:id=\"tabClinic\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert DoctorVisitClinic != null : "fx:id=\"DoctorVisitClinic\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert DoctorVisitClinic1 != null : "fx:id=\"DoctorVisitClinic1\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert TabBoard != null : "fx:id=\"TabBoard\" was not injected: check your FXML file 'DoctorMain.fxml'.";
        assert BorderPaneBoard != null : "fx:id=\"BorderPaneBoard\" was not injected: check your FXML file 'DoctorMain.fxml'.";
 
		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
			dmService = (IDoctorMainService) reg.lookup("doctorMain");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		setSche();
		setAppt();
		setNotice();
         
		Parent loader = FXMLLoader.load(getClass().getResource("../view/Schedule.fxml"));
		tabSchedule.setCenter(loader);
		thboolean=true;
		
//		Parent loader2 = FXMLLoader.load(getClass().getResource("../view/QnAMain.fxml"));
//		TabBoard.setCenter(loader2);
		
         Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				while(thboolean) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					List<MessageVO> msgList;
					try {
						msgList = dmService.getMsg(Integer.parseInt(LoginId.login_Id));
						Platform.runLater(()->{
//							if(msgList==null) return;
							for (int i = 0; i < msgList.size(); i++) {
								Stage stage = new Stage();
								FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MsgWrite.fxml"));
								try {
									Parent addRoot = loader.load();
									MsgWriteController msgWriteController = loader.getController();
									msgWriteController.setMsgVO(msgList.get(i));
									Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
									IMsgService service = (IMsgService) reg.lookup("msg");
									DoctorVO doctor = (DoctorVO) service.searchDoctor(msgList.get(i).getMsg_sd());
									msgWriteController.setMsg(doctor, msgList.get(i));
									Scene scene = new Scene(addRoot);
									stage.setScene(scene);
									stage.show();
								} catch (IOException | NotBoundException e) {
									e.printStackTrace();
								}
								
							}
						});
					} catch (NumberFormatException | RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
        
        th.setDaemon(true);
        th.start();
        
        ///임현이 만진 부분
        imgExit.setOnMouseClicked(e->{
        	thboolean=false;
        	Stage newPrimaryStage = new Stage();
        	Main main = new Main();
        	main.start(newPrimaryStage);
    		
    		Stage stage = (Stage) imgExit.getScene().getWindow();
    		stage.close();
        
        });
        
    }
    
    private int doctor_num = Integer.parseInt(LoginId.login_Id);
    
    private void setSche() throws RemoteException {
    	List<ScheduleVO> scheList = null;
    	if(dmService.getAllSche(doctor_num)!=null) {
    		scheList = dmService.getAllSche(doctor_num);
    	}else return;
//    	List<ScheduleVO> scheList = dmService.getAllSche(doctor_num);
    	ObservableList<ScheduleVO> scheData = FXCollections.observableArrayList(scheList);
    	tableVIewSche.setItems(scheData);
    	scheKindCol.setCellValueFactory(new PropertyValueFactory<>("sche_kind"));
    	scheNameCol.setCellValueFactory(new PropertyValueFactory<>("sche_name"));
    	scheDateCol.setCellValueFactory(new PropertyValueFactory<>("sche_startdate"));
    	scheContentCol.setCellValueFactory(new PropertyValueFactory<>("sche_cont"));
    }
    
    private void setAppt() throws RemoteException{
    	List<AppointmentVO> apptList = new ArrayList<AppointmentVO>();
    	if(dmService.getAllAppt(doctor_num)!=null) {
    		apptList = dmService.getAllAppt(doctor_num);
    	}else return;
    	ObservableList<AppointmentVO> apptData = FXCollections.observableArrayList(apptList);
    	tableViewAppt.setItems(apptData);
    	apptDateCol.setCellValueFactory(new PropertyValueFactory<>("appt_date"));
    	apptDateCol.setCellFactory(column -> {
    	    TableCell<AppointmentVO, Date> cell = new TableCell<AppointmentVO, Date>() {
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
    	apptPaNameCol.setCellValueFactory(new PropertyValueFactory<>("pa_id"));
    	apptPaNameCol.setCellFactory(column -> {
    	    TableCell<AppointmentVO, String> cell = new TableCell<AppointmentVO, String>() {
    	        @Override
    	        protected void updateItem(String item, boolean empty) {
    	            super.updateItem(item, empty);
    	            if(item == null || empty) {
    	                setText(null);
    	            }
    	            else {
    	                try {
							setText(dmService.searchPaName(item));
						} catch (RemoteException e) {
							e.printStackTrace();
						}
    	            }
    	        }
    	    };
    	    return cell;
    	});
    	apptKindCol.setCellValueFactory(new PropertyValueFactory<>("appt_kind"));
    }
    
    private void setNotice() throws RemoteException{
    	List<NoticeVO> noticeList = dmService.getAllNotice();
    	ObservableList<NoticeVO> noticeData = FXCollections.observableArrayList(noticeList);
    	tableViewNotice.setItems(noticeData);
    	noticeNumCol.setCellValueFactory(new PropertyValueFactory<>("notice_num"));
    	noticeTitleCol.setCellValueFactory(new PropertyValueFactory<>("notice_title"));
    	noticeWirterCol.setCellValueFactory(new PropertyValueFactory<>("notice_writer"));
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
