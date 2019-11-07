package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import patient.PatternUtil;

public class MemberJoinController {
	
	private ILoginService loginService;
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
    
    private String randomNum = ""; 		//회원가입시 필요한 인증번호 저장하는 변수
    private boolean VCChk = false;		//인증번호가 맞는지 여부 확인
    
    GetAlert getAlert = new GetAlert();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField txtID;
    
    @FXML
    private Label lblChk;

    @FXML
    private JFXButton btnMemChk;
    
    @FXML
    private JFXPasswordField txtPw;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXRadioButton male;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton female;

    @FXML
    private JFXTextField txtRegno1;
    
    @FXML
    private JFXPasswordField txtRegno2;

    @FXML
    private JFXTextField txtTel;

    @FXML
    private JFXTextField txtAddr;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private Button btnVC;

    @FXML
    private JFXTextField txtVC;

    @FXML
    private JFXButton btnVCChk;

    @FXML
    private JFXButton btnMemJoin;

    //인증번호 버튼 클릭시 발생하는 이벤트
    @FXML
    void clickVC(ActionEvent event) {
    	String 	myMail 			= txtEmail.getText();		//메일받는 사람
    	String 	host 			= "smtp.naver.com";			//네이버 내에 설정
    	final String user 		= "seoyoungjun@naver.com";	//메일 보내는 사람의 메일주소
    	final String password 	= "dudwns12";				//메일 보내는 사람의 메일비번
    	
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
			message.setSubject(myMail+"님의 참다남병원 회원가입 인증번호 발급");
			Date date = new Date();
			date.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
			
			randomNum = RandomNum();
			
			//Text
			message.setText(sdf.format(date) + "\n\n" + "[" + randomNum + "]");
			
			//send the message
			Transport.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }//END clickVC(ActionEvent event)

    
    //아이디 중복체크
    @FXML
    void memberCheck(ActionEvent event) throws RemoteException {
    	lblChk.setText("*사용 가능한 아이디");
    	String pa_id = txtID.getText();
    	
    	int result = loginService.checkPatient(pa_id);
    	
    	if(result >= 1) {
    		lblChk.setText("*중복된 아이디");
    		lblChk.setTextFill(Color.web("red"));
    	} else {
    		lblChk.setText("*사용 가능한 아이디");
    		lblChk.setTextFill(Color.web("#3c5a91"));
    		txtPw.requestFocus();
    	}
    }//END memberCheck(ActionEvent event)

    
    //회원가입
    @FXML
    void memberJoin(ActionEvent event) throws RemoteException {    	
    	String pa_id = txtID.getText();
    	String pa_pw = txtPw.getText();
    	String pa_name = txtName.getText();
    	String pa_gen = "";
    	if(male.isSelected()) {
    		pa_gen = "남";
    	} else if (female.isSelected()) {
    		pa_gen = "여";
    	}
//    	if(txtRegno1.getText() == null || txtRegno2.getText() == null) {
//    		getAlert.info("회원가입여부", "양식에 맞게 입력해주세요.");
//    	}
    	
//    	if(CheckInfo()) {
        	int pa_reg1 = Integer.parseInt(txtRegno1.getText());
        	int pa_reg2 = Integer.parseInt(txtRegno2.getText());
        	
        	String pa_tel = txtTel.getText();
        	String pa_addr = txtAddr.getText();
        	String pa_email = txtEmail.getText();
        	
        	PatientVO patientVo = new PatientVO();
        	patientVo.setPa_id(pa_id);
        	patientVo.setPa_pw(pa_pw);
        	patientVo.setPa_gen(pa_gen);
        	patientVo.setPa_name(pa_name);
        	patientVo.setPa_reg1(pa_reg1);
        	patientVo.setPa_reg2(pa_reg2);
        	patientVo.setPa_tel(pa_tel);
        	patientVo.setPa_addr(pa_addr);
        	patientVo.setPa_email(pa_email);
    		
    		int addResult = loginService.addPatient(patientVo);
    		
    		if(addResult >= 1) {
    			getAlert.info("회원가입여부", "성공적으로 회원가입하였습니다.");
        		Stage stage = (Stage) btnMemJoin.getScene().getWindow();
            	stage.close();
        	} else {
        		getAlert.info("회원가입여부", "회원가입에 실패하였습니다.");
        	}
//    	}
    }//END memberJoin(ActionEvent event)

    
    //인증번호 일치 여부 확인
    @FXML
    void verificationCodeCheck(ActionEvent event) {
    	if(!txtVC.getText().equals(randomNum)) {
    		getAlert.info("인증번호확인", "잘못된 인증번호를 입력하였습니다.");
    		txtVC.requestFocus();
    		VCChk = false;
    	} else {
    		getAlert.info("인증번호확인", "정상적으로 처리되었습니다.");
    	}
    	VCChk = true;
    }//END verificationCodeCheck(ActionEvent event)

    
    @FXML
    void initialize() {
        assert txtID != null : "fx:id=\"txtID\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert lblChk != null : "fx:id=\"lblChk\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert txtPw != null : "fx:id=\"txtPw\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert btnMemChk != null : "fx:id=\"btnMemChk\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert male != null : "fx:id=\"male\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert gender != null : "fx:id=\"gender\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert female != null : "fx:id=\"female\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert txtRegno1 != null : "fx:id=\"txtRegno1\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert txtRegno2 != null : "fx:id=\"txtRegno2\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert txtTel != null : "fx:id=\"txtTel\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert txtAddr != null : "fx:id=\"txtAddr\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert btnVC != null : "fx:id=\"btnVC\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert txtVC != null : "fx:id=\"txtVC\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert btnVCChk != null : "fx:id=\"btnVCChk\" was not injected: check your FXML file 'MemberJoin.fxml'.";
        assert btnMemJoin != null : "fx:id=\"btnMemJoin\" was not injected: check your FXML file 'MemberJoin.fxml'.";

        txtID.requestFocus();
        
        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			loginService = (ILoginService)reg.lookup("Login");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
    }
    
  	//인증번호 발급을 위한 변수 생성메서드
  	public String RandomNum() {
  		StringBuffer buffer = new StringBuffer();
  		int virtualNumber = 0;
  		for (int i = 1; i <= 6; i++) {
  			virtualNumber = (int)(Math.random() * 10);
  			buffer.append(virtualNumber);
		}
  		return buffer.toString();
  	}
  	
  	//회원가입시 필요한 확인
//  	public boolean CheckInfo() {
//  		//성별 라디오체크버튼
//	    ToggleGroup genderGroup = new ToggleGroup();
//	    male.setToggleGroup(genderGroup);
//	    female.setToggleGroup(genderGroup);
//		
//  		boolean check = true;
//  		PatternUtil pattern = new PatternUtil();
//    	
//  		while(check) {
//  	    	if(!pattern.regId(txtID.getText())) {
//  	    		getAlert.info("회원가입", "ID가 양식에 맞지 않습니다.");
//  	    		txtID.requestFocus();
//  	    		check = false;
//  	    		return check;
//  	    	}
  	    	
//  	    	if(lblChk.getText().equals("중복된 아이디")) {
//  	    		getAlert.info("회원가입", "ID중복확인 체크하시기 바랍니다.");
//  	    		lblChk.setDisable(true);
//  	    		check = false;
//  	    		return check;
//  	    	}
//  	    	
//  	    	if(!pattern.regPw(txtPw.getText())) {
//  	    		getAlert.info("회원가입", "비밀번호가 양식에 맞지 않습니다.");
//  	    		txtPw.requestFocus();
//  	    		check = false;
//  	    		return check;
//  	    	}
  	    	
//  	    	if(!pattern.regName(txtName.getText())) {
//  	    		getAlert.info("회원가입", "이름이 양식에 맞지 않습니다.");
//  	    		txtName.requestFocus();
//  	    		check = false;
//  	    		return check;
//  	    	}
//  	    	if(genderGroup.getSelectedToggle()==null) {
//  	    		getAlert.info("회원가입", "성별을 선택하지 않았습니다.");
//  	    		check = false;
//  	    		return check;
//  	    	}
//  	    	
//  	    	if(!pattern.reg_RegNo1(txtRegno1.getText())) {
//  	    		getAlert.info("회원가입", "주민번호 첫째자리가 양식에 맞지 않습니다.");
//  	    		txtRegno1.requestFocus();
//  	    		check = false;  
//  	    		return check;
//  	    	}
//  	    	
//  	    	if(!pattern.reg_RegNo2(txtRegno2.getText())) {
//  	    		getAlert.info("회원가입", "주민번호 둘째자리가 양식에 맞지 않습니다.");
//  	    		txtRegno2.requestFocus();
//  	    		check = false;
//  	    		return check;
//  	    	}
//  	    	
//  	    	if(!pattern.regTel(txtTel.getText())) {
//  	    		getAlert.info("회원가입", "전화번호가 양식에 맞지 않습니다.");
//  	    		txtTel.requestFocus();
//  	    		check = false;  
//  	    		return check;
//  	    	}
//  	    	
//  	    	if(txtAddr.getText()==null) {
//  	    		getAlert.info("회원가입", "주소가 양식에 맞지 않습니다.");
//  	    		txtAddr.requestFocus();
//  	    		check = false;  
//  	    		return check;
//  	    	}
//
//  	    	if(VCChk==false) {
//  	    		getAlert.info("인증번호확인", "잘못된 인증번호를 입력하였습니다.");
//  	    		txtVC.requestFocus();
//  	    		lblChk.setDisable(true);
//  	    		check = false;
//  	    		return check;
//  	    	}  			
//  		}
//  		
//  		return check;
//  	}
  	
  	
    
    
    
}
