package board.notice;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import VO.AdminisratorVO;
import VO.NoticeVO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.LoginId;

public class NoticeNewController {

	private NoticeServiceInf noticeService;

	private Stage primaryStage;
	private BorderPane mainPane ; 
	public void setPrimaryStage(Stage primaryStage, BorderPane mainPane) {
		this.primaryStage = primaryStage;
		this.mainPane=mainPane;
	}

	private AdminisratorVO avo;
	public void setAdminisratorVO (AdminisratorVO avo) {
		this.avo = avo;
	}
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button cancelButton;

	@FXML
	private Button saveButton;

	@FXML
	private TextField titleText;

	@FXML
	private TextArea contArea;

	@FXML
	void cancelButtonAct(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/NoticeMain.fxml"));
			Parent noticeMain = loader.load();
			
//			StackPane root = (StackPane) cancelButton.getScene().getRoot();
//			root.getChildren().add(noticeMain);
			
			NoticeMainController noticeMainController = loader.getController();
			noticeMainController.setPrimaryStage(primaryStage, mainPane);
			mainPane.setCenter(noticeMain);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@FXML
	void saveButtonAct(ActionEvent event) throws RemoteException {

		if(titleText.getText().isEmpty()) {
			alert("warning","제목을 입력해주세요.");
			return;

		}else if(contArea.getText().isEmpty()) {
			alert("warning","내용을 입력해주세요.");
			return;
		}

		NoticeVO noticeVo = new NoticeVO();
		noticeVo.setAdmin_id(LoginId.login_Id);
		noticeVo.setNotice_title(titleText.getText());
		AdminisratorVO admin = noticeService.getAdmin(LoginId.login_Id);
		noticeVo.setNotice_writer(admin.getAdmin_name());
		noticeVo.setNotice_cont(contArea.getText());

		int cnt = noticeService.insertNotice(noticeVo);

		if(cnt > 0) {

			alert("info","게시물 작성완료");
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/NoticeMain.fxml"));
				Parent root = loader.load();

				NoticeMainController noticeMainController = loader.getController();
				noticeMainController.setPrimaryStage(primaryStage, mainPane);
				mainPane.setCenter(root);

			}catch (IOException e) {
				e.printStackTrace();
			}
		}else if(cnt ==0){
			alert("warning","게시물 작성실패");
		}
	}

	int alert(String msgType, String msg){
		int result = 0;
		if(msgType.equals("warning")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("경고");
			alert.setHeaderText("경고");
			alert.setContentText(msg);
			alert.showAndWait();
		}else if(msgType.equals("info")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("알림");
			alert.setHeaderText("알림");
			alert.setContentText(msg);
			alert.showAndWait();
		}
		return result;
	}

	List<AdminisratorVO> adminList = null;
	String adminId = LoginId.login_Id;
	
	@FXML
	void initialize() {
		assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'NoticeNew.fxml'.";
		assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'NoticeNew.fxml'.";
		assert titleText != null : "fx:id=\"titleText\" was not injected: check your FXML file 'NoticeNew.fxml'.";
		assert contArea != null : "fx:id=\"contArea\" was not injected: check your FXML file 'NoticeNew.fxml'.";


		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			noticeService = (NoticeServiceInf)reg.lookup("Notice");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
