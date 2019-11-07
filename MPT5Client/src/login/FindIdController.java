package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import VO.PatientVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import patient.PatternUtil;

public class FindIdController {
	
	private ILoginService loginService;

	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
    
    GetAlert getAlert = new GetAlert();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtRegno1;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXButton btnFindPw;
    
    //비밀번호 찾는 화면으로 전환
    @FXML
    void FindPw(ActionEvent event) {
    	try {
    		Stage stage = new Stage();
    		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/FindPw.fxml"));		 
			Parent root = loader.load();
			
			FindPwController findPwController = loader.getController();
			findPwController.setFindIdStage(primaryStage);
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(primaryStage);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }//END FindPw(ActionEvent event)

    
    //이메일로 ID 보내기
    @FXML
    void sendId(ActionEvent event) {
    	String 	myMail 			= txtEmail.getText();		//메일받는 사람
    	String 	host 			= "smtp.naver.com";			//네이버 내에 설정
    	final String user 		= "seoyoungjun@naver.com";	//메일 보내는 사람의 메일주소
    	final String password 	= "dudwns12";				//메일 보내는 사람의 메일비번
    	String	pa_id			= "";						//전송받을 아이디
    	
    	PatientVO patientVo = new PatientVO();
//		boolean checkResult = checkFindId();
		
//		if(checkResult) {
			int result = 0;
			patientVo.setPa_name(txtName.getText());
			patientVo.setPa_reg1(Integer.parseInt(txtRegno1.getText()));
			patientVo.setPa_email(txtEmail.getText());
			
			try {
				result = loginService.checkPatientForId(patientVo);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			
	    	if(result >= 1) {
	    		if(!(myMail == null || myMail.equals(""))) {
	    			getAlert.info("메일전송", "메일전송이 완료되었습니다.");
	        	}
	        	
	        	//Get the Session Object
	        	Properties props = new Properties();
	        	props.put("mail.smtp.host", host);
	        	props.put("mail.smtp.auth", "true");
	        	
	        	Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	        		protected PasswordAuthentication getPasswordAuthentication() {
	        			return new PasswordAuthentication(user, password);
	        		}
	        	});
	        	
	        	//compose the message
	        	try {
	    			MimeMessage message = new MimeMessage(session);
	    			message.setFrom(new InternetAddress(user));
	    			message.addRecipient(Message.RecipientType.TO, new InternetAddress(myMail));
	    			
	    			//Subject
	    			message.setSubject(myMail+"님의 참다남병원 ID 발급");
	    			Date date = new Date();
	    			date.getTime();
	    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
	    			
	    			pa_id = loginService.getFindId(patientVo);
	    			
	    			//Text
	    			message.setText(sdf.format(date) + "\n\n" + "[" + pa_id + "] 입니다.");
	    			
	    			//send the message
	    			Transport.send(message);
	    			
	    		} catch (MessagingException e) {
	    			e.printStackTrace();
	    		} catch (RemoteException e) {
	    			e.printStackTrace();
	    		}    		
	    	} else {
	    		getAlert.info("ID찾기","입력된 정보가 일치하지 않습니다.");
	    	}
//		}    
    }

	@FXML
	void initialize() {
		assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'FindId.fxml'.";
		assert txtRegno1 != null : "fx:id=\"txtRegno1\" was not injected: check your FXML file 'FindId.fxml'.";
		assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'FindId.fxml'.";
		assert btnSend != null : "fx:id=\"btnSend\" was not injected: check your FXML file 'FindId.fxml'.";
		assert btnFindPw != null : "fx:id=\"btnFindPw\" was not injected: check your FXML file 'FindId.fxml'.";

		txtName.requestFocus();

		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
			loginService = (ILoginService) reg.lookup("Login");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
//	public boolean checkFindId() {
//    	PatternUtil pattern = new PatternUtil();
//    	boolean check = true;
//    	
//		if(!pattern.regName(txtName.getText())) {
//			getAlert.info("ID찾기","이름이 일치하지 않습니다.");
//    		txtName.requestFocus();
//    		check = false;
//    		return check;
//    	}
//    	if(!pattern.reg_RegNo1(txtRegno1.getText())) {
//    		getAlert.info("ID찾기","주민번호가 일치하지 않습니다.");
//    		txtRegno1.requestFocus();
//    		check = false;
//    		return check;
//    	}
//    	if(!pattern.reg_email(txtEmail.getText())) {
//    		getAlert.info("ID찾기","이메일이 일치하지 않습니다.");
//    		txtEmail.requestFocus();
//    		check = false;
//    		return check;
//    	}
//    	
//		return check;
//	}
}
