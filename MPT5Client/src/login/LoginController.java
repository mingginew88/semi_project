package login;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import VO.AdminisratorVO;
import VO.DoctorVO;
import VO.PatientVO;
import admin.AdminMainController;
import doctor.DoctorMainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import patient.PatientMainController;

public class LoginController {
	
	private ILoginService loginService;
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
    private String selID;

	public void setSelID(String selID) {
		this.selID = selID;
	}
	//TODO : 환자 로그인 이후 재접속 할때 에러 터짐.. 
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cbLogin;

    @FXML
    private Label lblLogin;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtPW;

    @FXML
    private CheckBox chkLogin;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnJoin;

    @FXML
    private Button btnFindIDPW;

    @FXML
    void join(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MemberJoin.fxml"));		 
			Parent root = loader.load();
			
			MemberJoinController memberJoinController = loader.getController();
			memberJoinController.setPrimaryStage(primaryStage);
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void login(ActionEvent event) throws RemoteException {
    	String logIn_id = txtID.getText();
    	String logIn_pw = txtPW.getText();
    	GetAlert getAlert = new GetAlert();
    	
    	if(cbLogin.getSelectionModel().isEmpty()) {
    		getAlert.info("로그인 정보", "로그인 할 대상을 선택해주세요.");
    		return;
    	}
    	if(logIn_id == null) {
    		getAlert.info("로그인 정보", "ID를 입력해주세요.");
    		txtID.requestFocus();
    		return;
    	}
    	if(logIn_pw == null) {
    		getAlert.info("로그인 정보", "비밀번호를 입력해주세요.");
    		txtPW.requestFocus();    		
    		return;
    	}
    
    	//라벨에 적힌 정보가  환자, 의사, 관리자에 따라 로그인 여부 결정
    	if(lblLogin.getText().equals("환자")) {
        	PatientVO pv = new PatientVO();
        	pv.setPa_id(logIn_id);
        	pv.setPa_pw(logIn_pw);
        	
        	int result = loginService.loginChkPatient(pv);
        	
        	if(result >= 1) {
        		PatientVO patientInfo = loginService.getPatientInfo(logIn_id);
        		LoginId.login_Id = logIn_id;
        		
    	        if(chkLogin.isSelected()) {
    	        	LoginId.selectLogin = true;        	
    	    	}else {
    	    		LoginId.selectLogin = false;
    	    	}
        		
        		try {
        			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PatientMain.fxml"));
    				Parent root = loader.load();
    				
    				PatientMainController patientMC = loader.getController();
    				patientMC.setPrimaryStage(primaryStage);
    				patientMC.setlblName(patientInfo.getPa_name());
    				
    				Scene scene = new Scene(root);
    				primaryStage.setScene(scene);
    				primaryStage.show();
    				
    			} catch (IOException e) {
    				e.printStackTrace();
    			}

        	} else {
        		getAlert.info("로그인 정보", "가입된 ID와 비밀번호를 다시 확인해주세요.");
        		return;
        	}
        	if(chkLogin.isSelected()) {
        		chkLogin.setSelected(true);
        	}
        	
    	} else if(lblLogin.getText().equals("의사")) {
    		int doctor_num = Integer.parseInt(logIn_id);
    		
    		DoctorVO dv = new DoctorVO();
    		dv.setDoctor_num(doctor_num);
    		dv.setDoctor_pw(logIn_pw);
    		
    		int result = loginService.loginChkDoctor(dv);
    		
    		if(result >= 1) {
        		DoctorVO doctorInfo = loginService.getDoctorInfo(doctor_num);
        		LoginId.login_Id = logIn_id;
        		
    	        if(chkLogin.isSelected()) {
    	        	LoginId.selectLogin = true;        	
    	    	}
    	        
        		try {
        			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/DoctorMain.fxml"));
    				Parent root = loader.load();
    				
    				DoctorMainController doctorMC = loader.getController();
    				doctorMC.setlblName(doctorInfo.getDoctor_name() + "");
    				
    				Scene scene = new Scene(root);
    				primaryStage.setScene(scene);
    				primaryStage.show();
    				
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	} else {
        		getAlert.info("로그인 정보", "가입된 ID와 비밀번호를 다시 확인해주세요.");
        		return;
        	}
    		if(chkLogin.isSelected()) {
        		chkLogin.setSelected(true);
        	}
    		
    	} else if(lblLogin.getText().equals("관리자")) {  		
    		AdminisratorVO adminVo = new AdminisratorVO();
    		adminVo.setAdmin_id(logIn_id);
    		adminVo.setAdmin_pw(logIn_pw);
    		
			int result = loginService.loginChkAdmin(adminVo);		
    		
    		if(result >= 1) {
    			AdminisratorVO adminInfo = loginService.getAdminInfo(logIn_id);
    			LoginId.login_Id = logIn_id;
    			
    	        if(chkLogin.isSelected()) {
    	        	LoginId.selectLogin = true;        	
    	    	}
    	        
        		try {
        			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AdminMain.fxml"));
    				Parent root = loader.load();
    				
    				AdminMainController adminMC = loader.getController();    				
    				adminMC.setlblName(adminInfo.getAdmin_id());
    				
    				Scene scene = new Scene(root);
    				primaryStage.setScene(scene);
    				primaryStage.show();
    				
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	} else {
        		getAlert.info("로그인 정보", "가입된 ID와 비밀번호를 다시 확인해주세요.");
        		return;
        	}
    	} 
    }

    @FXML
    void searchIDPW(ActionEvent event) {
    	try {
    		Stage stage = new Stage();
    		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/FindId.fxml"));		 
			Parent root = loader.load();
			
			FindIdController findIdController = loader.getController();
			findIdController.setPrimaryStage(stage);
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(primaryStage);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }//END searchIDPW(ActionEvent event)
    

    @FXML
    void initialize() {
        assert cbLogin != null : "fx:id=\"cbLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert lblLogin != null : "fx:id=\"lblLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtID != null : "fx:id=\"txtID\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtPW != null : "fx:id=\"txtPW\" was not injected: check your FXML file 'Login.fxml'.";
        assert chkLogin != null : "fx:id=\"chkLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert btnJoin != null : "fx:id=\"btnJoin\" was not injected: check your FXML file 'Login.fxml'.";
        assert btnFindIDPW != null : "fx:id=\"btnFindIDPW\" was not injected: check your FXML file 'Login.fxml'.";
       
        txtID.setText(selID);
        if(LoginId.selectLogin) {
        	txtID.setText(LoginId.login_Id);
        	chkLogin.setSelected(true);
        }
        
		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			loginService = (ILoginService)reg.lookup("Login");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        cbLogin.getItems().addAll("환자", "의사", "관리자");
        lblLogin.setText("");
        
        cbLogin.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                lblLogin.setText(newValue);
            }
        });
        
        cbLogin.setCellFactory(listview -> new StringImageCell());
        cbLogin.setButtonCell(new StringImageCell());
    }//END initialize()
    
    static class StringImageCell extends ListCell<String> {

        Label label;

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setItem(null);
                setGraphic(null);
            } else {
//                setText(item);
                ImageView image = getImageView(item);
                label = new Label("",image);
                setGraphic(label);
            }
        }

    }

    private static ImageView getImageView(String imageName) {
        ImageView imageView = null;
        switch (imageName) {
            case "환자":
                imageView = new ImageView(new Image("view/img/loginIcon.png"));
                break;
            case "의사":
                imageView = new ImageView(new Image("view/img/loginDoctor.png"));
                break;
            case "관리자":
                imageView = new ImageView(new Image("view/img/loginAdminicon.png"));
                break;
            default:
                imageName = null;
        }
        return imageView;
    }

    
}
