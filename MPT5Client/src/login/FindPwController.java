package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import VO.PatientVO;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import patient.PatternUtil;

public class FindPwController {
	
	private ILoginService loginService;
	
	private Stage findIdStage;
	
	public void setFindIdStage(Stage findIdStage) {
		this.findIdStage = findIdStage;
	}
	
	GetAlert getAlert = new GetAlert();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName1;

    @FXML
    private JFXTextField txtRegno1;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXButton btnSendPw;

    @FXML
    private JFXButton btnGoLogin;

    @FXML
    void SendPw(ActionEvent event) {
    	String 	myMail 			= txtEmail.getText();		//메일받는 사람
    	String 	host 			= "smtp.naver.com";			//네이버 내에 설정
    	final String user 		= "seoyoungjun@naver.com";	//메일 보내는 사람의 메일주소
    	final String password 	= "dudwns12";				//메일 보내는 사람의 메일비번
    	String	pa_pw			= "";						//전송받을 아이디
    	
    	PatientVO patientVo = new PatientVO();
//    	boolean checkResult = checkFindPw();
		
//		if(checkResult) {
			int result = 0;
			
			patientVo.setPa_id(txtId.getText());
			patientVo.setPa_name(txtName1.getText());
			patientVo.setPa_reg1(Integer.parseInt(txtRegno1.getText()));
			patientVo.setPa_email(txtEmail.getText());
			
			try {
				result = loginService.checkPatientForPw(patientVo);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}


			if (result >= 1) {
				if (!(myMail == null && myMail.equals(""))) {
					getAlert.info("메일전송", "메일전송이 완료되었습니다.");
				}

				// Get the Session Object
				Properties props = new Properties();
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.auth", "true");

				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});

				// compose the message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(user));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(myMail));

					// Subject
					message.setSubject(myMail + "님의 참다남병원 비밀번호 발급");
					Date date = new Date();
					date.getTime();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");

					pa_pw = loginService.getFindId(patientVo);

					// Text
					message.setText(sdf.format(date) + "\n\n" + "[" + pa_pw + "] 입니다.");

					// send the message
					Transport.send(message);

				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			} else {
				getAlert.info("비밀번호 찾기","입력된 정보가 일치하지 않습니다.");
			}
//		}
    }

    @FXML
    void goLogin(ActionEvent event) {
    	findIdStage.close();
    	Stage stage = (Stage) btnGoLogin.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void initialize() {
        assert txtId != null : "fx:id=\"txtId\" was not injected: check your FXML file 'FindPw.fxml'.";
        assert txtName1 != null : "fx:id=\"txtName1\" was not injected: check your FXML file 'FindPw.fxml'.";
        assert txtRegno1 != null : "fx:id=\"txtRegno1\" was not injected: check your FXML file 'FindPw.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'FindPw.fxml'.";
        assert btnSendPw != null : "fx:id=\"btnSendPw\" was not injected: check your FXML file 'FindPw.fxml'.";
        assert btnGoLogin != null : "fx:id=\"btnGoLogin\" was not injected: check your FXML file 'FindPw.fxml'.";

        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			loginService = (ILoginService)reg.lookup("Login");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
    }
    
//    public boolean checkFindPw() {
//    	PatternUtil pattern = new PatternUtil();
//    	boolean check = true;
//    	
//		if(!pattern.regId(txtId.getText())) {
//			getAlert.info("ID찾기","ID가 일치하지 않습니다.");
//			txtId.requestFocus();
//    		check = false;
//    		return check;
//		}
//		if(!pattern.regName(txtName1.getText())) {
//			getAlert.info("ID찾기","이름이 일치하지 않습니다.");
//    		txtName1.requestFocus();
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
//    	return check;
//	}
//    
    
    
}
