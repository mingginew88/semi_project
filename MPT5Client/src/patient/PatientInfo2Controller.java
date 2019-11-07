package patient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import VO.PatientVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.GetAlert;
import login.ILoginService;
import login.LoginId;

public class PatientInfo2Controller {
	
	// These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
	
	private IPatientInfoService patientService;
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
    private AnchorPane rootPane;

    @FXML
    private HBox header;

    @FXML
    private Label topLabel;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtPw;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtGen;

    @FXML
    private JFXTextField txtReg1;

    @FXML
    private JFXTextField txtReg2;

    @FXML
    private JFXTextField txtAddr;

    @FXML
    private JFXTextField txtTel;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    void cancel(MouseEvent event) {
    	//Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

	@FXML
	void clickDelete(ActionEvent event) throws RemoteException {
		Alert alertConfirm = new Alert(AlertType.CONFIRMATION);

		// alertConfirm.setTitle("Info");
		alertConfirm.setHeaderText("나의 정보 삭제");
		alertConfirm.setContentText("정보를 삭제 하시겠습니까?");

		// alert 창을 보여주고 선택한 값을 읽어온다.
		ButtonType deleteResult = alertConfirm.showAndWait().get();

		if (deleteResult == ButtonType.OK) {
			int result = patientService.deletePatientInfo(txtId.getText());
			getAlert.info("회원 탈퇴", "성공적으로 탈퇴되었습니다.");
			if (result >= 1) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));

				try {
					Parent root = loader.load();
					Stage child = new Stage(StageStyle.DECORATED);
					child.initModality(Modality.APPLICATION_MODAL);
					child.initOwner(primaryStage);

					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.show();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				getAlert.info("회원 탈퇴", "회원탈퇴에 실패하였습니다.");
			}
		} else if (deleteResult == ButtonType.CANCEL) {
			// TODO : 이 else if else 가 존재해야하는지 모르겠다.
		} else {
		}

	}

    @FXML
    void clickUpdate(ActionEvent event) {
    	//텍스트필드 정보변경 가능
    	boolean changeFalse = false;
        content(changeFalse);
        
        btnUpdate.setText("변경완료");
        btnDelete.setDisable(true);
        btnUpdate.setDisable(false);
        
        btnUpdate.setOnAction(e->{
        	PatientVO patientVo = new PatientVO();
        	patientVo.setPa_id(txtId.getText());
            patientVo.setPa_pw(txtPw.getText());
            patientVo.setPa_addr(txtAddr.getText());
            patientVo.setPa_tel(txtTel.getText());
            patientVo.setPa_email(txtEmail.getText());
            
            boolean chkResult = CheckInfo();
            
            try {
            	//정규식표현 통과 시 수정가능
            	if(chkResult) {
            		int result = patientService.updatePatientInfo(patientVo);
            		
        			if(result >= 1) {
                    	getAlert.info("정보변경", "정보변경 성공");
                    	//정보창 띄우기
                    	btnUpdate.setText("변경");
                    	btnDelete.setDisable(false);
                    	
                    	boolean changeTrue = true;
                        content(changeTrue);
                    } 
                } else {
                	getAlert.info("정보변경", "정보변경 실패");
                }
			} catch (RemoteException e2) {
				e2.printStackTrace();
			}
        });
    }

    @FXML
    void exit(MouseEvent event) {
    	//Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }


	@FXML
	void initialize() {
		assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert header != null : "fx:id=\"header\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert topLabel != null : "fx:id=\"topLabel\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert txtId != null : "fx:id=\"txtId\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert txtPw != null : "fx:id=\"txtPw\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert txtGen != null : "fx:id=\"txtGen\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert txtReg1 != null : "fx:id=\"txtReg1\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert txtReg2 != null : "fx:id=\"txtReg2\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert txtAddr != null : "fx:id=\"txtAddr\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert txtTel != null : "fx:id=\"txtTel\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'PatientInfo2.fxml'.";
		assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'PatientInfo2.fxml'.";

		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
			patientService = (IPatientInfoService) reg.lookup("patient");
			loginService = (ILoginService) reg.lookup("Login");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		String pa_id = LoginId.login_Id;
		try {
			PatientVO patientVo = loginService.getPatientInfo(pa_id);

			// 정보 보기
			txtId.setText(patientVo.getPa_id());
			txtPw.setText(patientVo.getPa_pw());
			txtName.setText(patientVo.getPa_name());
			txtGen.setText(patientVo.getPa_gen());
			txtReg1.setText(patientVo.getPa_reg1() + "");
			txtReg2.setText(patientVo.getPa_reg2() + "");
			txtAddr.setText(patientVo.getPa_addr());
			txtTel.setText(patientVo.getPa_tel());
			txtEmail.setText(patientVo.getPa_email());

			// 나의정보 변경 불가사항
			txtId.setDisable(true);
			txtName.setDisable(true);
			txtGen.setDisable(true);
			txtReg1.setDisable(true);
			txtReg2.setDisable(true);
			// 변경 버튼 클릭 전 텍스트필드 정보변경 불가능
			boolean change = true;
			content(change);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		

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
    
	// 나의정보 수정 가능여부
	public void content(boolean changePosCont) {
		txtPw.setDisable(changePosCont);
		txtAddr.setDisable(changePosCont);
		txtTel.setDisable(changePosCont);
		txtEmail.setDisable(changePosCont);
	}
	
	//정보 수정 가능한 목록들 정규식
  	public boolean CheckInfo() {
  		boolean check = true;
  		PatternUtil pattern = new PatternUtil();
    	
//    	if(!pattern.regPw(txtPw.getText())) {
//    		txtPw.requestFocus();
//    		check = false;
//    	}
    	
    	if(!pattern.regTel(txtTel.getText())) {
    		txtTel.requestFocus();
    		check = false;
    	}
  		return check;
  	}
  	
}
