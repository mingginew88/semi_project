package board.qna;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import VO.QuestionVO;
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

public class QnAUpdateController {
	
	private QuestionVO questionVo;
	private QnAServiceInf qnaService;

	public void setQuestionVo(QuestionVO questionVo) {
		this.questionVo = questionVo;
		titleText.setText(questionVo.getQue_title());
		contText.setText(questionVo.getQue_cont());
	}

	private Stage primaryStage;
	private BorderPane borderPane;
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
    private TextArea contText;

    @FXML
    void cancelButtonAct(ActionEvent event) {
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
    void saveButtonAct(ActionEvent event) {
    	if(titleText.getText().isEmpty()) {
    		alert("warning","제목을 입력해주세요.");
    	}else if (contText.getText().isEmpty()) {
    		alert("info"," 내용을 입력해주세요.");
    		
    	}
    	
    	QuestionVO questVo = new QuestionVO();
    	questVo.setQue_title(titleText.getText());
    	questVo.setQue_num(this.questionVo.getQue_num());
    	questVo.setQue_cont(contText.getText());
    	
    	int cnt = 0;
    	try {
			cnt = qnaService.updateQuestion(questVo);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	if(cnt > 0) {
    		alert("info","수정이 완료되었습니다.");
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/QnAMain.fxml"));
    			Parent root = loader.load();

    			QnAMainController qnaMainController = loader.getController();
    			qnaMainController.setPrimaryStage(primaryStage, borderPane);

    			borderPane.setCenter(root);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}else if(cnt==0) {
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
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'QnAUpdate.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'QnAUpdate.fxml'.";
        assert titleText != null : "fx:id=\"titleText\" was not injected: check your FXML file 'QnAUpdate.fxml'.";
        assert contText != null : "fx:id=\"contText\" was not injected: check your FXML file 'QnAUpdate.fxml'.";
        
        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			qnaService = (QnAServiceInf)reg.lookup("Question");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
    }
}
