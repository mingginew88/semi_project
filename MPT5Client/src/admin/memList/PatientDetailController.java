package admin.memList;

import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import VO.DoctorVO;
import VO.PatientVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class PatientDetailController {
	
	private Stage parentStage;

	public void setParentStage(Stage parentStage) {
		this.parentStage = parentStage;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField idFld;

    @FXML
    private JFXTextField nameFld;

    @FXML
    private JFXPasswordField pwFld;

    @FXML
    private JFXTextField genFld;

    @FXML
    private JFXTextField regFld;

    @FXML
    private JFXTextField addrFld;

    @FXML
    private JFXTextField telFld;

    @FXML
    private JFXTextField emailFld;

    @FXML
    private JFXButton chkBtn;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton delBtn;

    @FXML
    void delete(ActionEvent event) {
    	boolean confirm = confirm("확인", "정말로 삭제하시겠습니까??");
    	if(confirm == true) {
    		int cnt = service.deleteMember(idFld.getText());
    		if(cnt >0) {
    			info("작업 확인", "해당 정보를 삭제하였습니다");
    		}else {
    			alert("알림", "해당 정보 삭제 실패하였습니다");
    		}
    	}
    		
    }
    
    @FXML
    void check(ActionEvent event) {
    	pa.setPa_pw(pwFld.getText());
        pa.setPa_addr(addrFld.getText());
        pa.setPa_email(emailFld.getText());
        pa.setPa_tel(telFld.getText());
        
        int cnt = service.updatePatient(pa);
        if(cnt >0) {
        	info("작업확인", "변경 성공하였습니다^-^");
        }else {
        	alert("변경 오류", "변경에 실패하였습니다...");
        }
    }

    @FXML
    void edit(ActionEvent event) {
    	chkBtn.setVisible(true);
    	delBtn.setDisable(false);
    	
    	pwFld.setEditable(true);
        addrFld.setEditable(true);
        telFld.setEditable(true);
        emailFld.setEditable(true);
        
        if(pwFld.getText().isEmpty()) {
        	alert("편집 오류", "변경할 비밀번호를 입력해주세요");
        	pwFld.requestFocus();
        	return;
        }
        if(addrFld.getText().isEmpty()) {
        	alert("편집 오류", "변경할 주소를 입력해주세요");
        	addrFld.requestFocus();
        	return;
        }
        if(telFld.getText().isEmpty()) {
        	alert("편집 오류", "변경할 전화번호를 입력해주세요");
        	telFld.requestFocus();
        	return;
        }
        if(emailFld.getText().isEmpty()) {
        	alert("편집 오류", "변경할 이메일을 입력해주세요");
        	emailFld.requestFocus();
        	return;
        }
        
    	
    }// edit end
    
    
    private MemListController mlc;
    private IMemListService service;
    private ObservableList<PatientVO> paList;
    private PatientVO pa;
    
    public void setPa(PatientVO pa) {
		this.pa = pa;
		idFld.setText(pa.getPa_id());
        nameFld.setText(pa.getPa_name());
        pwFld.setText(pa.getPa_pw());
        genFld.setText(pa.getPa_gen());
        regFld.setText(pa.getPa_reg1()+"-"+pa.getPa_reg2());
        addrFld.setText(pa.getPa_addr());
        telFld.setText(pa.getPa_tel());
	}

	@FXML
    void initialize() {
        assert idFld != null : "fx:id=\"idFld\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        assert nameFld != null : "fx:id=\"nameFld\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        assert pwFld != null : "fx:id=\"pwFld\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        assert genFld != null : "fx:id=\"genFld\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        assert regFld != null : "fx:id=\"regFld\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        assert addrFld != null : "fx:id=\"addrFld\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        assert telFld != null : "fx:id=\"telFld\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        assert emailFld != null : "fx:id=\"emailFld\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        assert chkBtn != null : "fx:id=\"chkBtn\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        assert editBtn != null : "fx:id=\"editBtn\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        assert delBtn != null : "fx:id=\"delBtn\" was not injected: check your FXML file 'PatientInfo.fxml'.";
        
        //--------------------------------------------------------------------------------------------------
        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			service = (IMemListService)reg.lookup("memberList");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
       chkBtn.setVisible(false); 
       idFld.setEditable(false);
       nameFld.setEditable(false);
       pwFld.setEditable(false);
       genFld.setEditable(false);
       regFld.setEditable(false);
       addrFld.setEditable(false);
       telFld.setEditable(false);
       emailFld.setEditable(false);

    }
	public void alert(String head, String msg) {
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("알림");
    	alert.setHeaderText(head);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
	public void info(String head, String msg) {
    	Alert info = new Alert(AlertType.INFORMATION);
    	info.setTitle("정보");
    	info.setHeaderText(head);
    	info.setContentText(msg);
    	info.showAndWait();
    }
	public boolean confirm(String head, String msg) {
    	Alert confirm = new Alert(AlertType.CONFIRMATION);
    	confirm.setTitle("확인");
    	confirm.setHeaderText(head);
    	confirm.setContentText(msg);
    	
    	ButtonType result = confirm.showAndWait().get();
    	
    	if(result==ButtonType.OK) {
    		return true;
    	}else {
    		return false;
    	}
    	
    	
    }
}
