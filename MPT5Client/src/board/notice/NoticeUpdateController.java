package board.notice;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import VO.NoticeVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NoticeUpdateController {

	private NoticeVO noticeVo;
	private NoticeServiceInf noticeService;

	public void setNoticeVo(NoticeVO noticeVo) {
		this.noticeVo = noticeVo;
		titleText.setText(noticeVo.getNotice_title());
		contArea.setText(noticeVo.getNotice_cont());
	}
	private BorderPane borderPane;
	private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage, BorderPane borderPane) {
		this.primaryStage = primaryStage;
		this.borderPane=borderPane;
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
			Parent root = loader.load();
			NoticeMainController noticeContentController = loader.getController();
			noticeContentController.setPrimaryStage(primaryStage, borderPane);
			borderPane.setCenter(root);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void saveButtonAct(ActionEvent event) {
		if(titleText.getText().isEmpty()) {
			alert("warning","제목을 입력해주세요.");

		}else if(contArea.getText().isEmpty()) {
			alert("warning","내용을 입력해주세요.");
		}

		//TODO : notice.xml => insert 추가구문 쿼리문 작성 덜됨.

		NoticeVO noticeVo = new NoticeVO();
		noticeVo.setNotice_title(titleText.getText());
		noticeVo.setNotice_cont(contArea.getText());
		noticeVo.setNotice_num(this.noticeVo.getNotice_num());

		int cnt = 0;
		try {
			cnt = noticeService.updateNotice(noticeVo);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		if(cnt > 0) {
			alert("info","수정이 완료되였습니다.");
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/NoticeMain.fxml"));
				Parent root = loader.load();

				NoticeMainController noticeContentController = loader.getController();
				noticeContentController.setPrimaryStage(primaryStage, borderPane);
				borderPane.setCenter(root);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(cnt == 0){
			alert("warning","실패하였습니다.");
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

	@FXML
	void initialize() {
		assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'NoticeUpdate.fxml'.";
		assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'NoticeUpdate.fxml'.";
		assert titleText != null : "fx:id=\"titleText\" was not injected: check your FXML file 'NoticeUpdate.fxml'.";
		assert contArea != null : "fx:id=\"contArea\" was not injected: check your FXML file 'NoticeUpdate.fxml'.";

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
