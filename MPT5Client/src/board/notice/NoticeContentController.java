package board.notice;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.LoginId;

public class NoticeContentController {

	private NoticeServiceInf noticeService;

	private Stage primaryStage;
	private BorderPane borderPane;
	public void setPrimaryStage(Stage primaryStage, BorderPane borderPane) {
		this.primaryStage = primaryStage;
		this.borderPane=borderPane;
	}

	private NoticeVO noticeVo;
	public void setNoticeVo(NoticeVO noticeVo) {
		this.noticeVo = noticeVo;
		titleLabel.setText(noticeVo.getNotice_title());
		contLabel.setText(noticeVo.getNotice_cont());
		writerLabel.setText(noticeVo.getNotice_writer());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		dateLabel.setText(sdf.format(noticeVo.getNotice_date()));
		numLabel.setText(noticeVo.getNotice_num()+"");
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button updateButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button backButton;

	@FXML
	private Label numLabel;

	@FXML
	private Label writerLabel;

	@FXML
	private Label titleLabel;

	@FXML
	private Label contLabel;

	@FXML
	private Label dateLabel;

	@FXML
	void backButtonAct(ActionEvent event) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/NoticeMain.fxml"));
			Parent root = loader.load();
			NoticeMainController noticeContentController = loader.getController();
			noticeContentController.setPrimaryStage(primaryStage, borderPane);
			borderPane.setCenter(root);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void deleteButtonAct(ActionEvent event) {

		NoticeVO noticeVo = new NoticeVO();
		String num = numLabel.getText();
		int inum = Integer.parseInt(num);
		int cnt = 0;
		try {
			cnt = noticeService.deleteNotice(inum);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		if(cnt > 0) {
			alert("info","글이 삭제 되었습니다.");
			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/NoticeMain.fxml"));
				Parent root = loader.load();
				NoticeMainController noticeContentController = loader.getController();
				noticeContentController.setPrimaryStage(primaryStage, borderPane);
				borderPane.setCenter(root);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(cnt == 0) {
			alert("warning","삭제 실패 하였습니다.");
		}
	}

	@FXML
	void updateButtonAct(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/NoticeUpdate.fxml"));
			Parent root = loader.load();

			NoticeUpdateController noticeUpdateController = loader.getController();
			noticeUpdateController.setNoticeVo(noticeVo);
			noticeUpdateController.setPrimaryStage(primaryStage, borderPane);
			borderPane.setCenter(root);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void content() {
	
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


	@FXML
	void initialize() throws RemoteException {
		assert updateButton != null : "fx:id=\"updateButton\" was not injected: check your FXML file 'NoticeContent.fxml'.";
		assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'NoticeContent.fxml'.";
		assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'NoticeContent.fxml'.";
		assert numLabel != null : "fx:id=\"numLabel\" was not injected: check your FXML file 'NoticeContent.fxml'.";
		assert writerLabel != null : "fx:id=\"writerLabel\" was not injected: check your FXML file 'NoticeContent.fxml'.";
		assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'NoticeContent.fxml'.";
		assert contLabel != null : "fx:id=\"contLabel\" was not injected: check your FXML file 'NoticeContent.fxml'.";
		assert dateLabel != null : "fx:id=\"dateLabel\" was not injected: check your FXML file 'NoticeContent.fxml'.";

		String id = LoginId.login_Id;
		if(id.equals("admin")) {
			updateButton.setDisable(false);
			deleteButton.setDisable(false);
		}else if(id.equals("master")) {
			updateButton.setDisable(false);
			deleteButton.setDisable(false);
		}else {
			updateButton.setDisable(true);
			deleteButton.setDisable(true);
		}
		
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
