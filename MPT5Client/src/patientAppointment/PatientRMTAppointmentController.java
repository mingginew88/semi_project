package patientAppointment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;

import VO.AppointmentVO;
import VO.DepartmentVO;
import VO.DoctorVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.GetAlert;
import login.LoginId;




public class PatientRMTAppointmentController {
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
    

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXDatePicker apptDate;

    @FXML
    private JFXButton apptBtn;

    @FXML
    private JFXListView<DepartmentVO> deptList;

    @FXML
    private JFXListView<DoctorVO> docList;

    @FXML
    private JFXListView<String> timeList;
    
    private IPatientAppointmentService service;
    private List<DepartmentVO> dept;
    private List<DoctorVO> doctor;
    private ObservableList<DepartmentVO> deptName;
    private ObservableList<DoctorVO> docName;
    
    private ObservableList<String> setTime;
    private List<String> t;

    private LocalDate date;
    private String finTime;
    
    private GetAlert alert = new GetAlert();
    private DepartmentVO dpVO;
    private static DoctorVO docVO;
    private static AppointmentVO appt;
    
    private static int apptNum;
    
    
    public static int getApptNum() {
		return apptNum;
	}

	public static void setApptNum(int apptNum) {
		PatientRMTAppointmentController.apptNum = apptNum;
	}

	public static DoctorVO getDocVO() {
		return docVO;
	}

	public void setDocVO(DoctorVO docVO) {
		this.docVO = docVO;
	}

	public static AppointmentVO getAppt() {
		return appt;
	}

	public void setAppt(AppointmentVO appt) {
		this.appt = appt;
	}

    @FXML
    void appoint(ActionEvent event) throws ParseException {
    	ApptCheckController.setCheck(2);
    	try {
			Stage apptCheckDialog = new Stage();
			/*apptCheckDialog.initModality(Modality.WINDOW_MODAL);
			apptCheckDialog.initOwner(primaryStage);*/
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ApptCheck.fxml"));
			Parent root = loader.load();
			
			ApptCheckController apptCheckController = loader.getController();
			apptCheckController.setPrimaryStage(primaryStage);
			
			Scene sceneCheck = new Scene(root);
			apptCheckDialog.setScene(sceneCheck);
			apptCheckDialog.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void selectDept(MouseEvent event) throws RemoteException {
    	dpVO = deptList.getSelectionModel().getSelectedItem();
    	doctor = service.getDoctorList(dpVO.getDept_num());
    	docName = FXCollections.observableArrayList(doctor);
    	
    	docList.setItems(docName);
    	docList.setCellFactory(new Callback<ListView<DoctorVO>, ListCell<DoctorVO>>() {

			@Override
			public ListCell<DoctorVO> call(ListView<DoctorVO> param) {
				
				return new ListCell<DoctorVO>() {
					protected void updateItem(DoctorVO item, boolean empty) {
						super.updateItem(item, empty);
						if(item==null || empty) {
							setText(null);
						}else {
							setText(item.getDoctor_name());
						}
					}
				};
			}
    	});

			
    }

    @FXML
    void selectDoc(MouseEvent event) throws RemoteException {
    	if(dpVO == null) {
    		alert.alert("예약 오류", "에약하고 싶은 과를 선택해주세요");
    		deptList.requestFocus();
    	}
    	docList.setItems(docName);
    	docVO = docList.getSelectionModel().getSelectedItem();
    	
    	String d = selectDate();
    	String tt ="";
    	List<String> aTime = new ArrayList<>();
    	
    	List<String> findTime = service.getTime(docVO.getDoctor_num());
    	System.out.println(findTime);
    	
    	for(int i =0; i < findTime.size(); i ++) {
    		if(d.equals(findTime.get(i).substring(0, 8))){
    			tt = findTime.get(i).substring(8, 10);
    			aTime.add(tt);
    		}
    	
    	}
    	HashSet<String> set = new HashSet<>();
    	if(aTime.isEmpty()) {
    		for(int i = 9; i <19; i++) {
    			if(i==9) {
					set.add("0"+i+":00");
				}else {
					set.add(i+":00");
				}
    		}
    	}else {
    		
    		for(int i =9; i < 19; i++) {
    			for(int j =0; j < aTime.size(); j++) {
    				if(i != Integer.parseInt(aTime.get(j))) {
    					if(i==9) {
    						set.add("0"+i+":00");
    					}else {
    						set.add(i+":00");
    					}
    				}
    			}
    		}
    	}
    	List<String> result = new ArrayList<String>();
    	result.addAll(set);
    	Collections.sort(result);
    	setTime = FXCollections.observableArrayList(result);
    	timeList.setItems(setTime);
    	
    }
    @FXML
    void selectTime(MouseEvent event) throws ParseException {
    	String selectDate = "";
    	String selectTime="";
    	
    	selectDate=selectDate();
    	if(selectDate == ""|| dpVO == null || docVO == null) {
    		alert.alert("예약 오류", "선택 항목을 확인해주세요");
    		apptDate.requestFocus();
    		deptList.requestFocus();
    		docList.requestFocus();
    		timeList.requestFocus();
    	}
    	selectTime = timeList.getSelectionModel().getSelectedItem();
    	
    	selectDate += selectTime.substring(0,2)+"0000";
    	
    	System.out.println("selectdate"+selectDate);
    	
    	SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddhhmmss");
    	Date findate = fm.parse(selectDate);
    	//Date date2 = new SimpleDateFormat("yyyy/MM/dd hh24").parse(selectDate);
    	
    	 
    	System.out.println("findate"+findate);
    	appt = new AppointmentVO();
    	appt.setAppt_date(findate);
    	appt.setPa_id(LoginId.login_Id);
    	appt.setDoctor_num(docVO.getDoctor_num());
    	appt.setAppt_kind("원격 진료");
    	
    	System.out.println("appt =>"+ appt.getAppt_date()+"/"+appt);
    }

    @FXML
    void initialize() throws RemoteException {
        assert apptDate != null : "fx:id=\"apptDate\" was not injected: check your FXML file 'PatientRMTAppointment.fxml'.";
        assert apptBtn != null : "fx:id=\"apptBtn\" was not injected: check your FXML file 'PatientRMTAppointment.fxml'.";
        assert deptList != null : "fx:id=\"deptList\" was not injected: check your FXML file 'PatientRMTAppointment.fxml'.";
        assert docList != null : "fx:id=\"docList\" was not injected: check your FXML file 'PatientRMTAppointment.fxml'.";
        assert timeList != null : "fx:id=\"timeList\" was not injected: check your FXML file 'PatientRMTAppointment.fxml'.";
        
      //--------------------------------------------------------------------------------------------------------------------
        try {
 			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
 			service = (IPatientAppointmentService) reg.lookup("patientAppointment");
 		} catch (RemoteException e) {
 			e.printStackTrace();
 		} catch (NotBoundException e) {
 			e.printStackTrace();
 		}
        
      //--------------------------------------------------------------------------------------------------------------------
        dept = service.getDeptList();
        System.out.println(dept);
        deptName = FXCollections.observableArrayList(dept);
        deptList.setItems(deptName);
        deptList.setCellFactory(new Callback<ListView<DepartmentVO>, ListCell<DepartmentVO>>() {

			@Override
			public ListCell<DepartmentVO> call(ListView<DepartmentVO> param) {
				
				return new ListCell<DepartmentVO>() {
					protected void updateItem(DepartmentVO item, boolean empty) {
						super.updateItem(item, empty);
						if(item==null || empty) {
							setText(null);
						}else {
							setText(item.getDept_name());
						}
					}
				};
			}
		});
        //-------------------------------------------------------------------------------------
        docList.setCellFactory(new Callback<ListView<DoctorVO>, ListCell<DoctorVO>>() {

			@Override
			public ListCell<DoctorVO> call(ListView<DoctorVO> param) {
				
				return new ListCell<DoctorVO>() {
					protected void updateItem(DoctorVO item, boolean empty) {
						super.updateItem(item, empty);
						if(item==null || empty) {
							setText(null);
						}else {
							setText(item.getDoctor_name());
						}
					}
				};
			}
    	});
        //--------------------------------------------------------------------------------------
        timeList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

			@Override
			public ListCell<String> call(ListView<String> param) {
				return new ListCell<String>() {
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(item==null || empty) {
							setText(null);
						}else {
							setText(item);
						}
					}
				};
			}
		});

        
        
    }
    
    public String selectDate() {
    	date = apptDate.getValue();
    	System.out.println(date);
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        String formatDate = formatter.format(date);
        System.out.println(formatDate);
        return formatDate;
    }
    
   
}
