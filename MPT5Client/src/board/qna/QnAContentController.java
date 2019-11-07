package board.qna;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import VO.AnswerVO;
import VO.QuestionVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import login.LoginController;
import login.LoginId;

public class QnAContentController {

	private QnAServiceInf qnaService;

	private Stage primaryStage;
	private BorderPane borderPane;
	public void setPrimaryStage(Stage primaryStage, BorderPane borderPane) {
		this.primaryStage = primaryStage;
		this.borderPane = borderPane;
	}
	
	private QuestionVO questionVo;
	public void setQuestionVo(QuestionVO questionVo) {
		this.questionVo = questionVo;
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
	private Label QuestionTitleLabel;

	@FXML
	private Label QuestionContLabel;

	@FXML
	private Label dateLabel;

	@FXML
	private TextArea answerContText;

	@FXML
	private Button answerSaveButton;

	AnswerVO answerVo = new AnswerVO();

	@FXML
	void answerSaveButtonAct(ActionEvent event) {
		if(answerContText.getText().isEmpty()) {
			alert("info","답변을 입력해주세요");
			return;
		}

		String id = LoginId.login_Id;
		int doctorNum = Integer.parseInt(id);

		answerVo.setDoctor_num(doctorNum);
		answerVo.setAnswer_num(questionVo.getQue_num());

		if(answerContText.getText().isEmpty()) {
			answerVo.setAnswer_cont(answerContText.getText());
			try {
				int cnt = qnaService.insertAnswer(answerVo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}else {
			answerVo.setAnswer_cont(answerContText.getText());
			try {
				int cnt = qnaService.updateAnswer(answerVo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void backButtonAct(ActionEvent event) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/QnAMain.fxml"));
			Parent root = loader.load();

			QnAMainController qnaMainController = loader.getController();
			qnaMainController.setPrimaryStage(primaryStage, borderPane);

			borderPane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void deleteButtonAct(ActionEvent event) throws RemoteException {
		QuestionVO qvo = new QuestionVO();
		String num = numLabel.getText();
		int inum = Integer.parseInt(num);
		int cnt = 0;
		cnt = qnaService.deleteQuestion(inum);

		if(cnt > 0) {
			alert("info","글이 삭제 되었습니다.");
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/QnAMain.fxml"));
				Parent root = loader.load();

				QnAMainController QnAMainController = loader.getController();
				QnAMainController.setPrimaryStage(primaryStage, borderPane);
				
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

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/QnAUpdate.fxml"));
			Parent root = loader.load();

			QnAUpdateController qnaUpdateController = loader.getController();
			qnaUpdateController.setPrimaryStage(primaryStage, borderPane);
			
			borderPane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void content() {
		QuestionTitleLabel.setText(questionVo.getQue_title());
		QuestionContLabel.setText(questionVo.getQue_cont());
		writerLabel.setText(questionVo.getPa_id());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		dateLabel.setText(sdf.format(questionVo.getQue_date()));
		numLabel.setText(questionVo.getQue_num()+"");
		
		String loginId = LoginId.login_Id;
		String paId = questionVo.getPa_id();
		
		if(!(loginId.equals(paId))) {
			updateButton.setDisable(true);
			deleteButton.setDisable(true);
		}
		
		List<AnswerVO> anum=new ArrayList<AnswerVO>();
		try {
			anum = qnaService.getAnswerCont(questionVo.getQue_num());
			for (AnswerVO answerVO : anum) {
				answerContText.setText(answerVO.getAnswer_cont());
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
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
		assert updateButton != null : "fx:id=\"updateButton\" was not injected: check your FXML file 'QnAContent.fxml'.";
		assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'QnAContent.fxml'.";
		assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'QnAContent.fxml'.";
		assert numLabel != null : "fx:id=\"numLabel\" was not injected: check your FXML file 'QnAContent.fxml'.";
		assert writerLabel != null : "fx:id=\"writerLabel\" was not injected: check your FXML file 'QnAContent.fxml'.";
		assert QuestionTitleLabel != null : "fx:id=\"QuestionTitleLabel\" was not injected: check your FXML file 'QnAContent.fxml'.";
		assert QuestionContLabel != null : "fx:id=\"QuestionContLabel\" was not injected: check your FXML file 'QnAContent.fxml'.";
		assert dateLabel != null : "fx:id=\"dateLabel\" was not injected: check your FXML file 'QnAContent.fxml'.";
		assert answerContText != null : "fx:id=\"answerContText\" was not injected: check your FXML file 'QnAContent.fxml'.";
		assert answerSaveButton != null : "fx:id=\"answerSaveButton\" was not injected: check your FXML file 'QnAContent.fxml'.";
		
		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			qnaService = (QnAServiceInf)reg.lookup("Question");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		FXMLLoader loader = new FXMLLoader();

		LoginController lc = loader.getController();

		String id = LoginId.login_Id;//.substring(0, 5);
		int length = id.length();
		String subId="";
		if(length > 8) {
			subId = id.substring(0, 4);
			if(Integer.parseInt(subId) == 2014) {
				answerSaveButton.setDisable(false);
				answerContText.setEditable(true);
				updateButton.setDisable(true);
				deleteButton.setDisable(true);
			}
		}else if(length < 8){
			answerSaveButton.setDisable(true);
			answerContText.setEditable(false);
		}
		
		

	}
}
