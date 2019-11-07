package admin.memList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import VO.DoctorVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DoctorDetailController {
	
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
    private JFXTextField deptFld;

    @FXML
    private JFXTextField emailFld;

    @FXML
    private JFXButton chkBtn;

    @FXML
    private JFXButton editBtn;
    
    private DoctorVO doc;
    private IMemListService service;

    public void setDoc(DoctorVO doc) {
		this.doc = doc;
		idFld.setText(doc.getDept_num()+"");
		nameFld.setText(doc.getDoctor_name());
		pwFld.setText(doc.getDoctor_pw());
		regFld.setText(doc.getDoctor_reg1()+"-"+doc.getDoctor_reg2());
		addrFld.setText(doc.getDoctor_addr());
		deptFld.setText(doc.getDept_num()+"");
		genFld.setText(doc.getDoctor_gen());
		emailFld.setText(doc.getDoctor_email());
	}

	@FXML
    void check(ActionEvent event) {
		doc.setDoctor_pw(pwFld.getText());
        doc.setDoctor_addr(addrFld.getText());
        doc.setDoctor_email(emailFld.getText());
        doc.setDept_num(Integer.parseInt(deptFld.getText()));
        
        int cnt = service.updateDoctor(doc);
        if(cnt >0) {
        	info("작업확인", "변경 성공하였습니다^-^");
        }else {
        	alert("변경 오류", "변경에 실패하였습니다...");
        }
    }

    @FXML
    void edit(ActionEvent event) {
    	chkBtn.setVisible(true);
    	
    	pwFld.setEditable(true);
    	addrFld.setEditable(true);
        deptFld.setEditable(true);
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
        if(deptFld.getText().isEmpty()) {
        	alert("편집 오류", "변경할 부서번호를 입력해주세요");
        	deptFld.requestFocus();
        	return;
        }
        if(emailFld.getText().isEmpty()) {
        	alert("편집 오류", "변경할 이메일을 입력해주세요");
        	emailFld.requestFocus();
        	return;
        }
        
        
    	
    	
    }

    @FXML
    void initialize() {
        assert idFld != null : "fx:id=\"idFld\" was not injected: check your FXML file 'DoctorDetail.fxml'.";
        assert nameFld != null : "fx:id=\"nameFld\" was not injected: check your FXML file 'DoctorDetail.fxml'.";
        assert pwFld != null : "fx:id=\"pwFld\" was not injected: check your FXML file 'DoctorDetail.fxml'.";
        assert genFld != null : "fx:id=\"genFld\" was not injected: check your FXML file 'DoctorDetail.fxml'.";
        assert regFld != null : "fx:id=\"regFld\" was not injected: check your FXML file 'DoctorDetail.fxml'.";
        assert addrFld != null : "fx:id=\"addrFld\" was not injected: check your FXML file 'DoctorDetail.fxml'.";
        assert deptFld != null : "fx:id=\"deptFld\" was not injected: check your FXML file 'DoctorDetail.fxml'.";
        assert emailFld != null : "fx:id=\"emailFld\" was not injected: check your FXML file 'DoctorDetail.fxml'.";
        assert chkBtn != null : "fx:id=\"chkBtn\" was not injected: check your FXML file 'DoctorDetail.fxml'.";
        assert editBtn != null : "fx:id=\"editBtn\" was not injected: check your FXML file 'DoctorDetail.fxml'.";
        //---------------------------------------------------------------------------------------------------------
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
        deptFld.setEditable(false);
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
}
