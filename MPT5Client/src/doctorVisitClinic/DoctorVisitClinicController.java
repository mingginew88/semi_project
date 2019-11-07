package doctorVisitClinic;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import VO.AppointmentVO;
import VO.DiseaseVO;
import VO.ExaminationVO;
import VO.MedicineVO;
import VO.PatientVO;
import VO.PrescriptionVO;
import VO.VisitclinicVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import login.GetAlert;
import login.ILoginService;
import login.LoginId;
import searchData.ISearchDataService;
import searchData.SearchDataController.PaData;

public class DoctorVisitClinicController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<ApptWait> tableViewAppointment;

    @FXML
    private TableColumn<ApptWait, Date> appointDateCol;

    @FXML
    private TableColumn<ApptWait, String> appointNameCol;

    @FXML
    private TableColumn<ApptWait, Integer> appointAgeCol;

    @FXML
    private TableColumn<ApptWait, String> appointGenCol;

    @FXML
    private TableView<DiseaseVO> tableViewDisease;

    @FXML
    private TableColumn<DiseaseVO, Integer> disNumCol;

    @FXML
    private TableColumn<DiseaseVO, String> disNameCol;

    @FXML
    private TableColumn<DiseaseVO, String> disSYMPCol;

    @FXML
    private TextArea tfvVisitClinic;

    @FXML
    private TextArea tfprescription;

    @FXML
    private JFXTextField tfMedi;

    @FXML
    private JFXComboBox<String> cbMedi;

    @FXML
    private TableView<MedicineVO> tableViewMedi;

    @FXML
    private TableColumn<MedicineVO, Integer> mediCodeCol;

    @FXML
    private TableColumn<MedicineVO, String> mediNameCol;

    @FXML
    private TableColumn<MedicineVO, String> medicateCol;

    @FXML
    private TableColumn<MedicineVO, String> mediPMSCol;

    @FXML
    private TableView<ExaminationVO> tableViewExamination;

    @FXML
    private TableColumn<ExaminationVO, Date> exDateCol;

    @FXML
    private TableColumn<ExaminationVO, Integer> exFeverCol;

    @FXML
    private TableColumn<ExaminationVO, Integer> exRbcCol;
    
    @FXML
    private TableColumn<ExaminationVO, Integer> exWbcCol;

    @FXML
    private TableColumn<ExaminationVO, Integer> exPlateletCol;
    
    @FXML
    private JFXTextField tfDisName;

    @FXML
    private JFXTextField tfDisSYMP;
    
    @FXML
    private JFXTextField tfFever;

    @FXML
    private JFXTextField tfRBC;

    @FXML
    private JFXTextField tfWBC;

    @FXML
    private JFXTextField tfPlatelet;

    @FXML
    private JFXTextField tfALT;

    @FXML
    private JFXTextField tfGlucose;

    @FXML
    private JFXTextField tfCholesterol;

    @FXML
    void cancel(MouseEvent event) {
    	tfvVisitClinic.clear();
    	tfprescription.clear();
    	tfDisName.clear();
    	tfDisSYMP.clear();
    	tfFever.clear();
    	tfRBC.clear();
    	tfWBC.clear();
    	tfPlatelet.clear();
    	tfALT.clear();
    	tfGlucose.clear();
    	tfCholesterol.clear();
    }
    
    private boolean vcChk = false;
    @FXML
    void changePatient(MouseEvent event) {
    	try {
    		tfvVisitClinic.clear();
        	tfprescription.clear();
        	tfDisName.clear();
        	tfDisSYMP.clear();
        	tfFever.clear();
        	tfRBC.clear();
        	tfWBC.clear();
        	tfPlatelet.clear();
        	tfALT.clear();
        	tfGlucose.clear();
        	tfCholesterol.clear();
			PatientVO pa = DVCService.getPaInfo(tableViewAppointment.getSelectionModel().getSelectedItem().getPa_id());
			getPaExam(pa.getPa_id());
			getPaDis(pa.getPa_id());
			int appt_num = tableViewAppointment.getSelectionModel().getSelectedItem().getAppt_num();
			if(DVCService.searchApptVC(appt_num)) {
				vcChk = true;
				VisitclinicVO vc = DVCService.getPaVC(appt_num);
				clinicNum = vc.getClinic_num();
				tfvVisitClinic.setText(vc.getVstclinic_cont());
				DiseaseVO dis = DVCService.getPaDisease(vc.getClinic_num());
				if(dis==null)return;
				tfDisName.setText(dis.getDis_name());
				tfDisSYMP.setText(dis.getDis_symp());
				PrescriptionVO presc = DVCService.getPaPresc(vc.getClinic_num());
				tfprescription.setText(presc.getPresc_cont());
				ExaminationVO exam = DVCService.getPaExam(vc.getClinic_num());
				if(exam==null)return;
				tfFever.setText(Integer.toString(exam.getFever()));
				tfRBC.setText(Integer.toString(exam.getRed_blood_cell()));
				tfWBC.setText(Integer.toString(exam.getWhite_blood_cell()));
				tfPlatelet.setText(Integer.toString(exam.getPlatelet()));
				tfALT.setText(Integer.toString(exam.getExam_bad1()));
				tfGlucose.setText(Integer.toString(exam.getExam_bad2()));
				tfCholesterol.setText(Integer.toString(exam.getExam_bad3()));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
    }
    
    private void getPaExam(String pa_id) {
    	List<ExaminationVO> exList = null;
    	try {
			exList = DVCService.getExInfo(pa_id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        ObservableList<ExaminationVO> exData = FXCollections.observableArrayList(exList);
        tableViewExamination.setItems(exData);
    	exDateCol.setCellValueFactory(new PropertyValueFactory<>("exam_rec_date"));
        exDateCol.setCellFactory(column -> {
    	    TableCell<ExaminationVO, Date> cell = new TableCell<ExaminationVO, Date>() {
    	        private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    	        @Override
    	        protected void updateItem(Date item, boolean empty) {
    	            super.updateItem(item, empty);
    	            if(item == null || empty) {
    	                setText(null);
    	            }
    	            else {
    	                setText(format.format(item));
    	            }
    	        }
    	    };
    	    return cell;
    	});
        exFeverCol.setCellValueFactory(new PropertyValueFactory<>("fever"));
        exRbcCol.setCellValueFactory(new PropertyValueFactory<>("red_blood_cell"));
        exWbcCol.setCellValueFactory(new PropertyValueFactory<>("white_blood_cell"));
        exPlateletCol.setCellValueFactory(new PropertyValueFactory<>("platelet"));
        
    }
    
    private void getPaDis(String pa_id) {
    	List<DiseaseVO> disList = null;
    	try {
			disList = DVCService.getDis(pa_id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	ObservableList<DiseaseVO> disData = FXCollections.observableArrayList(disList);
    	tableViewDisease.setItems(disData);
    	disNumCol.setCellValueFactory(new PropertyValueFactory<>("dis_num"));
    	disNameCol.setCellValueFactory(new PropertyValueFactory<>("dis_name"));
    	disSYMPCol.setCellValueFactory(new PropertyValueFactory<>("dis_symp"));
    }

    int clinicNum = 0;
    @FXML
    void insertVisitClinicPrescription(MouseEvent event) throws RemoteException {
    	
    	//tfvVisitClinic.clear();
		if(tfprescription.getText().isEmpty()||tfDisName.getText().isEmpty()||tfDisSYMP.getText().isEmpty()||
				tfFever.getText().isEmpty()||tfRBC.getText().isEmpty()||tfWBC.getText().isEmpty()||tfPlatelet.getText().isEmpty()||
				tfALT.getText().isEmpty()||tfGlucose.getText().isEmpty()||tfCholesterol.getText().isEmpty()) {
			new GetAlert().alert("...", "vc 수정 실패");
			return;
		}
    	if(!vcChk) {
    		String paId = tableViewAppointment.getSelectionModel().getSelectedItem().getPa_id();
    		clinicNum = insertvc(paId);
    		int DisNum = insertDis(paId, clinicNum);
    		insertpresc(paId, clinicNum, DisNum);
    		insertExam(paId, clinicNum);
    	}else {
    		VisitclinicVO vc = new VisitclinicVO();
    		vc.setVstclinic_cont(tfvVisitClinic.getText());
    		vc.setClinic_num(clinicNum);
    		vc.setPa_id(tableViewAppointment.getSelectionModel().getSelectedItem().getPa_id());
    		if(DVCService.updateVC(vc)<=0) {
    			new GetAlert().alert("...", "vc 수정 실패");
    			return;
    		}
    		PrescriptionVO presc = new PrescriptionVO();
    		presc.setPresc_cont(tfprescription.getText());
    		presc.setClinic_num(clinicNum);
    		if(DVCService.updatePresc(presc)<=0) {
    			new GetAlert().alert("...", "presc 수정 실패");
    			return;
    		}
    		DiseaseVO dis = new DiseaseVO();
    		dis.setDis_name(tfDisName.getText());
    		dis.setDis_symp(tfDisSYMP.getText());
    		dis.setClinic_num(clinicNum);
    		if(DVCService.updateDisease(dis)<=0) {
    			new GetAlert().alert("...", "disease 수정 실패");
    			return;
    		}
    		ExaminationVO exam = new ExaminationVO();
    		exam.setExam_cont(tfvVisitClinic.getText());
    		exam.setFever(Integer.parseInt(tfFever.getText()));
        	exam.setRed_blood_cell(Integer.parseInt(tfRBC.getText()));
        	exam.setWhite_blood_cell(Integer.parseInt(tfWBC.getText()));
        	exam.setPlatelet(Integer.parseInt(tfPlatelet.getText()));
        	exam.setExam_bad1(Integer.parseInt(tfALT.getText()));
        	exam.setExam_bad2(Integer.parseInt(tfGlucose.getText()));
        	exam.setExam_bad3(Integer.parseInt(tfCholesterol.getText()));
        	exam.setDoctor_num(Integer.parseInt(LoginId.login_Id));
        	exam.setClinic_num(clinicNum);
        	exam.setPa_id(tableViewAppointment.getSelectionModel().getSelectedItem().getPa_id());
        	if(DVCService.updateExam(exam)<=0) {
        		new GetAlert().alert(",,,", "Examination 수정 실패");
        		return;
        	}else {
        		new GetAlert().info("complete", "수정하였습니다.");
        		vcChk = false;
        	}
    	}
    	tfvVisitClinic.clear();
    	tfprescription.clear();
    	tfDisName.clear();
    	tfDisSYMP.clear();
    	tfFever.clear();
    	tfRBC.clear();
    	tfWBC.clear();
    	tfPlatelet.clear();
    	tfALT.clear();
    	tfGlucose.clear();
    	tfCholesterol.clear();
    }
    
    private int insertvc(String paId) throws RemoteException {
    	VisitclinicVO vc = new VisitclinicVO();
    	int clinicNum = DVCService.maxVCNum() + 1;
    	vc.setClinic_num(clinicNum);
    	System.out.println("---"+tfvVisitClinic.getText());
    	vc.setVstclinic_cont(tfvVisitClinic.getText());
    	vc.setPa_id(paId);
    	vc.setDoctor_num(Integer.parseInt(LoginId.login_Id));
    	vc.setAppt_num(tableViewAppointment.getSelectionModel().getSelectedItem().getAppt_num());
    	int ivc = DVCService.insertVisitClinic(vc);
    	if(ivc<0) {
    		new GetAlert().alert("경고", "visitclinic insert 실패");
    	}
    	return clinicNum;
    }
    
    private int insertDis(String paId, int clinicNum) throws RemoteException {
    	DiseaseVO dis = new DiseaseVO();
    	int DisNum = DVCService.maxDisNum() + 1;
    	dis.setDis_num(DisNum);
    	dis.setDis_name(tfDisName.getText());
    	dis.setDis_symp(tfDisSYMP.getText());
    	dis.setPa_id(paId);
    	dis.setClinic_num(clinicNum);
    	int idis = DVCService.insertDisease(dis);
    	if(idis<0) {
    		new GetAlert().alert("경고","Disease insert 실패");
    	}
    	return DisNum;
    }
    
    private void insertpresc(String paId, int clinicNum, int DisNum) throws RemoteException {
    	PrescriptionVO presc = new PrescriptionVO();
    	presc.setPresc_cont(tfprescription.getText());
    	presc.setPa_id(paId);
    	presc.setDoctor_num(doctor_num);
    	presc.setClinic_num(clinicNum);
    	presc.setDis_num(DisNum);
    	int ips = DVCService.insertPrescription(presc); 
    	if(ips<0) {
    		new GetAlert().alert("경고", "Examination insert 실패");
    	}
    }
    
    private void insertExam(String paId, int clinicNum) throws RemoteException{
    	ExaminationVO exam = new ExaminationVO();
    	exam.setPa_id(paId);
    	exam.setExam_cont(tfvVisitClinic.getText());
    	exam.setFever(Integer.parseInt(tfFever.getText()));
    	exam.setRed_blood_cell(Integer.parseInt(tfRBC.getText()));
    	exam.setWhite_blood_cell(Integer.parseInt(tfWBC.getText()));
    	exam.setPlatelet(Integer.parseInt(tfPlatelet.getText()));
    	exam.setExam_bad1(Integer.parseInt(tfALT.getText()));
    	exam.setExam_bad2(Integer.parseInt(tfGlucose.getText()));
    	exam.setExam_bad3(Integer.parseInt(tfCholesterol.getText()));
    	exam.setDoctor_num(Integer.parseInt(LoginId.login_Id));
    	exam.setClinic_num(clinicNum);
    	int ie = DVCService.insertExam(exam);
    	if(ie<0) {
    		new GetAlert().alert("경고", "Examination insert 실패");
    	}else {
    		new GetAlert().info("complete", "저장되었습니다.");
    	}
    }
    

    @FXML
    void searchMedi(MouseEvent event) throws RemoteException {
    	String searchData = tfMedi.getText();
    	List<MedicineVO> mediList = null;
    	if(searchData.isEmpty()) {
    		mediList = SDService.getAllMedi();
    	}else {
    		Map<String, String> search = new HashMap<String, String>();
    		String searchField = cbMedi.getSelectionModel().getSelectedItem().equals("의약품코드")?"medi_code":"medi_name";
    		search.put("searchField", searchField);
    		search.put("searchData", searchData);
    		try {
				mediList = DVCService.getMedi(search);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	}
    	setMedi(mediList);
    }
    
    @FXML
    void addMedi(MouseEvent event) {
    	MedicineVO medi = tableViewMedi.getSelectionModel().getSelectedItem();
    	String medistr = "\n" + medi.getMedi_code() + " " + medi.getMedi_name() + " 1회";
    	tfprescription.appendText(medistr);
    }
    
    private IDoctorVisitClinicService DVCService;
    private ISearchDataService SDService;
    private int doctor_num = Integer.parseInt(LoginId.login_Id);

    @FXML
    void initialize() throws RemoteException {
    	assert tableViewAppointment != null : "fx:id=\"tableViewAppointment\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert appointDateCol != null : "fx:id=\"appointDateCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert appointNameCol != null : "fx:id=\"appointNameCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert appointAgeCol != null : "fx:id=\"appointAgeCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert appointGenCol != null : "fx:id=\"appointGenCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tableViewDisease != null : "fx:id=\"tableViewDisease\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert disNumCol != null : "fx:id=\"disNumCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert disNameCol != null : "fx:id=\"disNameCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert disSYMPCol != null : "fx:id=\"disSYMPCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfvVisitClinic != null : "fx:id=\"tfvVisitClinic\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfprescription != null : "fx:id=\"tfprescription\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfMedi != null : "fx:id=\"tfMedi\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert cbMedi != null : "fx:id=\"cbMedi\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tableViewMedi != null : "fx:id=\"tableViewMedi\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert mediCodeCol != null : "fx:id=\"mediCodeCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert mediNameCol != null : "fx:id=\"mediNameCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert medicateCol != null : "fx:id=\"medicateCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert mediPMSCol != null : "fx:id=\"mediPMSCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tableViewExamination != null : "fx:id=\"tableViewExamination\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert exDateCol != null : "fx:id=\"exDateCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert exFeverCol != null : "fx:id=\"exFeverCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert exRbcCol != null : "fx:id=\"exRbcCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert exWbcCol != null : "fx:id=\"exWbcCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert exPlateletCol != null : "fx:id=\"exPlateletCol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfDisName != null : "fx:id=\"tfDisName\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfDisSYMP != null : "fx:id=\"tfDisSYMP\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfFever != null : "fx:id=\"tfFever\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfRBC != null : "fx:id=\"tfRBC\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfWBC != null : "fx:id=\"tfWBC\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfPlatelet != null : "fx:id=\"tfPlatelet\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfALT != null : "fx:id=\"tfALT\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfGlucose != null : "fx:id=\"tfGlucose\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";
        assert tfCholesterol != null : "fx:id=\"tfCholesterol\" was not injected: check your FXML file 'DoctorVisitClinic.fxml'.";

        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			DVCService = (IDoctorVisitClinicService)reg.lookup("doctorVisitClinic");
			SDService = (ISearchDataService) reg.lookup("searchData");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        List<AppointmentVO> apList = new ArrayList<AppointmentVO>();
        try {
			apList = DVCService.getAllAppoint(doctor_num);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        
        List<ApptWait> awList = new ArrayList<ApptWait>();
        for (int i = 0; i < apList.size(); i++) {
        	PatientVO pa;
			try {
				pa = DVCService.getPaInfo(apList.get(i).getPa_id());
				ApptWait appt = new ApptWait(apList.get(i).getAppt_date(), pa.getPa_name(), pa.getPa_gen(),
											 pa.getPa_reg1(), pa.getPa_id(), apList.get(i).getAppt_num());
				awList.add(appt);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
        ObservableList<ApptWait> awData = FXCollections.observableArrayList(awList);
        tableViewAppointment.setItems(awData);
        appointDateCol.setCellValueFactory(new PropertyValueFactory<>("appt_date"));
        appointDateCol.setCellFactory(column -> {
    	    TableCell<ApptWait, Date> cell = new TableCell<ApptWait, Date>() {
    	        private SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    	        @Override
    	        protected void updateItem(Date item, boolean empty) {
    	            super.updateItem(item, empty);
    	            if(item == null || empty) {
    	                setText(null);
    	            }
    	            else {
    	                setText(format.format(item));
    	            }
    	        }
    	    };
    	    return cell;
    	});
        appointNameCol.setCellValueFactory(new PropertyValueFactory<>("pa_name"));
        appointGenCol.setCellValueFactory(new PropertyValueFactory<>("pa_gen"));
        appointAgeCol.setCellValueFactory(new PropertyValueFactory<>("pa_reg1"));
        cbMedi.getItems().addAll("의약품코드","의약품명");
        cbMedi.setValue("의약품코드");
        List<MedicineVO> mediList = SDService.getAllMedi();
        setMedi(mediList);
    }
    
    private void setMedi(List<MedicineVO> mediList) {
    	ObservableList<MedicineVO> mediData = FXCollections.observableArrayList(mediList);
    	tableViewMedi.setItems(mediData);
    	mediCodeCol.setCellValueFactory(new PropertyValueFactory<>("medi_code"));
    	mediNameCol.setCellValueFactory(new PropertyValueFactory<>("medi_name"));
    	medicateCol.setCellValueFactory(new PropertyValueFactory<>("medi_category"));
    	mediPMSCol.setCellValueFactory(new PropertyValueFactory<>("medi_pms"));
    }
    
    public class ApptWait{
    	private Date appt_date;
    	private String pa_name;
    	private String pa_gen;
    	private int pa_reg1;
    	private String pa_id;
    	private int appt_num;
    	ApptWait(Date appt_date, String pa_name, String pa_gen, int pa_reg1, String pa_id, int appt_num){
    		this.appt_date=appt_date;
    		this.pa_name=pa_name;
    		this.pa_gen=pa_gen;
    		this.pa_reg1=pa_reg1;
    		this.pa_id=pa_id;
    		this.appt_num=appt_num;
    	}
		public Date getAppt_date() {
			return appt_date;
		}
		public String getPa_name() {
			return pa_name;
		}
		public String getPa_gen() {
			return pa_gen;
		}
		public int getPa_reg1() {
			return pa_reg1;
		}
		public String getPa_id() {
			return pa_id;
		}
		public int getAppt_num() {
			return appt_num;
		}
    }
    
}
