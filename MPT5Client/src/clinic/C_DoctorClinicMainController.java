package clinic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import VO.AppointmentVO;
import VO.PatientVO;
import appointment.IAppointmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.ILoginService;
import patient.IPatientInfoService;

public class C_DoctorClinicMainController {
	// 부모 Stage Set
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	//////////////////////////////////////////////////////////////////
	private IPatientInfoService ipatientServcie; 
	private IAppointmentService iappointmentService;
	ObservableList<PatientVO> pateintList = FXCollections.observableArrayList();
	
	//////////////////////////////////////////////////////////////////
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXButton openClinicWebBtn;

	@FXML
	private JFXButton ClinicStartBtn;

	@FXML
	private JFXTextField patientTf;

	@FXML
	private JFXButton paSerchbtn;

	@FXML
	private TableView<PatientVO> resultTable;

	@FXML
	private TableColumn<?, ?> paIdCol;
	
	@FXML
	private TableColumn<?, ?> paNameCol;

	@FXML
	private TableColumn<?, ?> paPNCol;

	@FXML
	private JFXTextField chidTF;

	@FXML
	private JFXTextField ipAddrTF;

	@FXML
	private JFXTextField PortNumTF;

	@FXML
	private JFXButton sendInfoBtn;

	@FXML
	private Label serverLb;

	@FXML
	void ClinicStartBtnOnClick(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/DoctorChat.fxml"));
		AnchorPane root = loader.load();

		C_DoctorChatController chatController = loader.getController();
		chatController.setPrimaryStage(primaryStage);

		Scene scene = new Scene(root);
		Stage clinicChatStage = new Stage();

		clinicChatStage.initModality(Modality.APPLICATION_MODAL);
		clinicChatStage.setScene(scene);
		clinicChatStage.setTitle("채팅 진료창");
		clinicChatStage.show();

	}

	@FXML
	void openClinicWebBtnOnClick(ActionEvent event) {
		cmdTest cmdt = new cmdTest();
		cmdt.start();
	}

	@FXML
	void paSerchbtnOnClick(ActionEvent event) {
		if(patientTf.getText().isEmpty()) {
			alert("검색할 값을 입력하세요.");
			return;
		}
		List<PatientVO> patientList1 = new ArrayList<>();
		try {
			patientList1= ipatientServcie.serchPatientInfoName(patientTf.getText());
		} catch (RemoteException e) {
			e.printStackTrace();
			alert("리모트 익셉션 오류.");
		}
		if(patientList1.isEmpty()) {
			alert("검색 하신 회원이 없습니다.");
			return;
		}
		pateintList.clear();
		pateintList.addAll(patientList1);
	}
	
	@FXML
	void sendInfoBtnOnClick(ActionEvent event) {
		AppointmentVO apvo = new AppointmentVO();
		apvo.setPa_id(pv.getPa_id()); //환자 ID로 비교할 값
		apvo.setAppt_kind("원격 진료"); //원격인 환자 만 바꿀 값
		apvo.setAppt_chid(chidTF.getText()); // 변경할 값
		apvo.setAppt_ip(ipAddrTF.getText()); // 변경할 값
		apvo.setAppt_portn(PortNumTF.getText()); // 변경할 값
		try {
			if(iappointmentService.updateAppointment(apvo)>0) {
				info("정보 전송이 성공하였습니다.");
			}else {
				alert("정보 전송이 실패 하였습니다.");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private PatientVO pv = new PatientVO();
	@FXML
	void initialize() {
		assert openClinicWebBtn != null : "fx:id=\"openClinicWebBtn\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert ClinicStartBtn != null : "fx:id=\"ClinicStartBtn\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert patientTf != null : "fx:id=\"patientTf\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert paSerchbtn != null : "fx:id=\"paSerchbtn\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert resultTable != null : "fx:id=\"resultTable\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert paIdCol != null : "fx:id=\"paIdCol\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert paNameCol != null : "fx:id=\"paNameCol\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert paPNCol != null : "fx:id=\"paPNCol\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert chidTF != null : "fx:id=\"chidTF\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert ipAddrTF != null : "fx:id=\"ipAddrTF\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert PortNumTF != null : "fx:id=\"PortNumTF\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		assert sendInfoBtn != null : "fx:id=\"sendInfoBtn\" was not injected: check your FXML file 'DoctorClinic.fxml'.";
		
		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			ipatientServcie = (IPatientInfoService)reg.lookup("patient");
			iappointmentService = (IAppointmentService)reg.lookup("appointment");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			String s = ip.getHostAddress();
			ipAddrTF.setText(s);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		paIdCol.setCellValueFactory(new PropertyValueFactory<>("pa_id"));
		paNameCol.setCellValueFactory(new PropertyValueFactory<>("pa_name"));
		paPNCol.setCellValueFactory(new PropertyValueFactory<>("pa_tel"));
	
		resultTable.setItems(pateintList);
		
		resultTable.setOnMouseClicked(e->{
			pv = resultTable.getSelectionModel().getSelectedItem();
			if(pv==null) {
				alert("잘못누르셨습니다.");
				return;
			}
		});
	}
	// alert창
    public void alert(String msg) {
    	Alert alertWarning = new Alert(AlertType.WARNING);
    	alertWarning.setTitle("경고");
    	alertWarning.setContentText(msg);
    	alertWarning.showAndWait();
    }
    
    // info창
    public void info(String msg) {
    	Alert alertInfo = new Alert(AlertType.INFORMATION);
    	alertInfo.setTitle("확인");
    	alertInfo.setContentText(msg);
    	alertInfo.showAndWait();
    }
}
