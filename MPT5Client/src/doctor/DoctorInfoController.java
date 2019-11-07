package doctor;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import VO.DoctorVO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import login.GetAlert;
import login.LoginId;

public class DoctorInfoController {
	
	private IDoctorMainService doctorMainService;
	
	// These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
	
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
    private AnchorPane rootPane;

    @FXML
    private HBox header;

    @FXML
    private Label topLabel;

    @FXML
    private Label lblNum;

    @FXML
    private JFXTextField txtNum;

    @FXML
    private Label lblPw;

    @FXML
    private JFXTextField txtPw;

    @FXML
    private Label lblDep;

    @FXML
    private JFXTextField txtDep;

    @FXML
    private Label lblName;

    @FXML
    private JFXTextField txtName;

    @FXML
    private Label lblGen;

    @FXML
    private JFXTextField txtGen;

    @FXML
    private Label lblReg;

    @FXML
    private JFXTextField txtReg1;

    @FXML
    private JFXTextField txtReg2;

    @FXML
    private JFXTextField txtAddr;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton cancelButton;

    @FXML
    void cancel(MouseEvent event) {
    	//Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void exit(MouseEvent event) {
    	//Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(MouseEvent event) {
    	int doctor_num = Integer.parseInt(LoginId.login_Id);
    	if(btnUpdate.getText().equals("정보 변경")) {
    		btnUpdate.setText("변경 하기");
        	txtAddr.setDisable(false);
            txtEmail.setDisable(false);
    		
    	} else if(btnUpdate.getText().equals("변경 하기")) {
    		DoctorVO doctorVo = new DoctorVO();
            doctorVo.setDoctor_addr(txtAddr.getText());
            doctorVo.setDoctor_email(txtEmail.getText());
            doctorVo.setDoctor_num(doctor_num);
			try {	
				int result = doctorMainService.updateDoctorInfo(doctorVo);
				if(result >= 1) {
					btnUpdate.setText("정보 변경");
					getAlert.info("정보변경","정보변경에 성공하였습니다.");
					txtAddr.setDisable(true);
			        txtEmail.setDisable(true);
				} else {
					getAlert.info("정보변경","정보 변경에 실패하였습니다.");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	}

    }

    @FXML
    void initialize() {
        assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert header != null : "fx:id=\"header\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert topLabel != null : "fx:id=\"topLabel\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert lblNum != null : "fx:id=\"lblNum\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert txtNum != null : "fx:id=\"txtNum\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert lblPw != null : "fx:id=\"lblPw\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert txtPw != null : "fx:id=\"txtPw\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert lblDep != null : "fx:id=\"lblDep\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert txtDep != null : "fx:id=\"txtDep\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert lblName != null : "fx:id=\"lblName\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert lblGen != null : "fx:id=\"lblGen\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert txtGen != null : "fx:id=\"txtGen\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert lblReg != null : "fx:id=\"lblReg\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert txtReg1 != null : "fx:id=\"txtReg1\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert txtReg2 != null : "fx:id=\"txtReg2\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert txtAddr != null : "fx:id=\"txtAddr\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'DoctorInfo.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'DoctorInfo.fxml'.";

        try {
			Registry reg = LocateRegistry.getRegistry("localhost",9988);
			doctorMainService = (IDoctorMainService)reg.lookup("doctorMain");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
         int doctor_num = Integer.parseInt(LoginId.login_Id);
        
        try {
			DoctorVO doctorVo = doctorMainService.getDoctorInfo(doctor_num);
			txtNum.setText(doctorVo.getDoctor_num() + "");
			txtPw.setText(doctorVo.getDoctor_pw());
			txtDep.setText(doctorVo.getDept_num() + "");
			txtName.setText(doctorVo.getDoctor_name());
			txtGen.setText(doctorVo.getDoctor_gen());
			txtReg1.setText(doctorVo.getDoctor_reg1() + "");
			txtReg2.setText(doctorVo.getDoctor_reg2() + "");
			txtAddr.setText(doctorVo.getDoctor_addr());
			txtEmail.setText(doctorVo.getDoctor_email());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        
        //텍스트필드 수정 불가
        txtNum.setDisable(true);
        txtPw.setDisable(true);
        txtDep.setDisable(true);
        txtName.setDisable(true);
        txtGen.setDisable(true);
        txtReg1.setDisable(true);
        txtReg2.setDisable(true);
        txtAddr.setDisable(true);
        txtEmail.setDisable(true);
        
        
		// ******** Code below is for Draggable windows **********

		// Set up Mouse Dragging for the Event pop up window
		topLabel.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Stage stage = (Stage) rootPane.getScene().getWindow();
				xOffset = stage.getX() - event.getScreenX();
				yOffset = stage.getY() - event.getScreenY();
			}
		});
		// Set up Mouse Dragging for the Event pop up window
		topLabel.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Stage stage = (Stage) rootPane.getScene().getWindow();
				stage.setX(event.getScreenX() + xOffset);
				stage.setY(event.getScreenY() + yOffset);
			}
		});
		// Change cursor when hover over draggable area
		topLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Stage stage = (Stage) rootPane.getScene().getWindow();
				Scene scene = stage.getScene();
				scene.setCursor(Cursor.HAND); // Change cursor to hand
			}
		});

		// Change cursor when hover over draggable area
		topLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Stage stage = (Stage) rootPane.getScene().getWindow();
				Scene scene = stage.getScene();
				scene.setCursor(Cursor.DEFAULT); // Change cursor to hand
			}
		});

	}
        
    
}
