package patientClinic;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import VO.AppointmentVO;
import appointment.IAppointmentService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.ILoginService;
import login.LoginId;

public class C_PatientClinicMainController {

	// 부모 Stage Set
	private Stage primaryStage;
	private BorderPane borderPane;

	public void setPrimaryStage(Stage primaryStage, BorderPane borderPane) {
		this.primaryStage = primaryStage;
		this.borderPane=borderPane;
	}
	private AppointmentVO apvo = new AppointmentVO(); // 임시

	private IAppointmentService iappointmentService;
	
	private ILoginService iloginService;
	private String loginId = LoginId.login_Id;
	
	//환자 채팅창에서 바로 띄워줄수 있도록 겟 메서드를 만들어 놓은 것이다
	private static String ipAddr;
	private static String port;
	
	public static String getIpAddr() {
		return ipAddr;
	}

	public static String getPort() {
		return port;
	}
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton openClinicWebBtn;

    @FXML
    private JFXButton ClinicStartBtn;

    @FXML
    private JFXComboBox<Date> apptDateCbx;

    @FXML
    private JFXTextField docNameTf;

    @FXML
    private JFXTextField apptChIdTf;

    @FXML
    private JFXTextField apptPortTf;
    
    @FXML
    private JFXTextField apptIpTf;

    @FXML
    private JFXButton getApptInfoBtn;
    
	@FXML
	void ClinicStartBtnOnClick(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PatientChat.fxml"));
		AnchorPane root = loader.load();

		C_PatientChatController chatController = loader.getController();
		chatController.setPrimaryStage(primaryStage, borderPane);
		Scene scene = new Scene(root);
		Stage clinicChatStage = new Stage();

		clinicChatStage.initModality(Modality.APPLICATION_MODAL);
		clinicChatStage.setScene(scene);
		clinicChatStage.setTitle("채팅 진료창");
		clinicChatStage.show();
	}

	@FXML
	void openClinicWebBtnOnClick(ActionEvent event) {
		patientCmdTest pcmdt = new patientCmdTest();
		pcmdt.start();
	}
	List<AppointmentVO> appList = new ArrayList<AppointmentVO>();
    ObservableList<AppointmentVO> appObList;
	@FXML
	void initialize() throws RemoteException {
		assert openClinicWebBtn != null : "fx:id=\"openClinicWebBtn\" was not injected: check your FXML file 'PatientClinic.fxml'.";
		assert ClinicStartBtn != null : "fx:id=\"ClinicStartBtn\" was not injected: check your FXML file 'PatientClinic.fxml'.";
		assert apptDateCbx != null : "fx:id=\"apptDateCbx\" was not injected: check your FXML file 'PatientClinic.fxml'.";
		assert docNameTf != null : "fx:id=\"docNameTf\" was not injected: check your FXML file 'PatientClinic.fxml'.";
		assert apptChIdTf != null : "fx:id=\"apptChIdTf\" was not injected: check your FXML file 'PatientClinic.fxml'.";
		assert apptPortTf != null : "fx:id=\"apptPortTf\" was not injected: check your FXML file 'PatientClinic.fxml'.";
		assert apptIpTf != null : "fx:id=\"apptIpTf\" was not injected: check your FXML file 'PatientClinic.fxml'.";
		
		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			iappointmentService = (IAppointmentService)reg.lookup("appointment");
			iloginService = (ILoginService)reg.lookup("Login");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
			
		
		appList=iappointmentService.searchAppointmentAll(loginId);
		//FX아이템에 넣기위한 배열로 만들어 주자
		appObList=FXCollections.observableArrayList(appList);
		
		//콤보박스에 Date 삽입
		//콤보박스에 아이템을 삽입하려면 꼭 getItems를 한 후 하자 setValue랑은 다른 문제다 
		for (int i = 0; i < appObList.size(); i++) {
			apptDateCbx.getItems().addAll(appObList.get(i).getAppt_date());
		}
		
		apptDateCbx.valueProperty().addListener(
				new ChangeListener<Date>() {
					@Override
					public void changed(ObservableValue<? extends Date> observable, 
							Date oldValue, Date newValue) {
						//콤보박스에서 선택한 날짜와 동일한 정보를 세팅해줘야 하기 때문에 newValue와 일치하는 VO를 검색
						for (int i = 0; i < appObList.size(); i++) {
							if(appObList.get(i).getAppt_date().equals(newValue)) {
								apvo=appObList.get(i);
							}
						}
						try {
							docNameTf.setText(iloginService.getDoctorInfo(apvo.getDoctor_num()).getDoctor_name());
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ipAddr=apvo.getAppt_ip();
						port=apvo.getAppt_portn();
						apptChIdTf.setText(apvo.getAppt_chid());
						apptPortTf.setText(apvo.getAppt_portn());
						apptIpTf.setText(apvo.getAppt_ip());
					}
				}
			);
		
		
	}
}
