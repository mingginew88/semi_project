package patientAppointment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import VO.AppointmentVO;
import VO.DoctorVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import login.GetAlert;

public class ApptCheckController {
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox vbox;

    @FXML
    private JFXTextField apptDateFld;

    @FXML
    private JFXTextField paNameFld;

    @FXML
    private JFXTextField docName;

    @FXML
    private JFXTextField kindFld;

    @FXML
    private JFXTextField ChIdFLd;

    @FXML
    private JFXTextField portFld;

    @FXML
    private JFXTextField IPFld;

    @FXML
    private JFXButton chkBtn;
    //------------------------------------------------------
    private IPatientAppointmentService service;
   // private PatientVisitAppointmentController pvc;
    private AppointmentVO apptVO;
    private DoctorVO docVO;
    
    private Date date;
    private String sdate;
    private String paName;
    private GetAlert alert = new GetAlert();
    private static int check;
    

    

    public static int getCheck() {
		return check;
	}

	public static void setCheck(int check) {
		ApptCheckController.check = check;
	}

	@FXML
    void checkAppt(ActionEvent event) throws RemoteException {
//    	apptVO = PatientVisitAppointmentController.getAppt();
    	int cnt = service.addAppoint(apptVO);
//    	System.out.println(cnt);
    	if(cnt >0) {
    		alert.info("Check!!", "예약이 완료되었습니다");
    		
    	}else {
    		alert.alert("Fail..", "예약이 실패하였습니다");
    	}
    	
    	
    }

    @FXML
    void initialize() throws RemoteException {
        assert vbox != null : "fx:id=\"vbox\" was not injected: check your FXML file 'ApptCheck.fxml'.";
        assert apptDateFld != null : "fx:id=\"apptDateFld\" was not injected: check your FXML file 'ApptCheck.fxml'.";
        assert paNameFld != null : "fx:id=\"paNameFld\" was not injected: check your FXML file 'ApptCheck.fxml'.";
        assert docName != null : "fx:id=\"docName\" was not injected: check your FXML file 'ApptCheck.fxml'.";
        assert kindFld != null : "fx:id=\"kindFld\" was not injected: check your FXML file 'ApptCheck.fxml'.";
        assert ChIdFLd != null : "fx:id=\"ChIdFLd\" was not injected: check your FXML file 'ApptCheck.fxml'.";
        assert portFld != null : "fx:id=\"portFld\" was not injected: check your FXML file 'ApptCheck.fxml'.";
        assert IPFld != null : "fx:id=\"IPFld\" was not injected: check your FXML file 'ApptCheck.fxml'.";
        assert chkBtn != null : "fx:id=\"chkBtn\" was not injected: check your FXML file 'ApptCheck.fxml'.";
        
      //--------------------------------------------------------------------------------------------------------------------
        try {
 			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
 			service = (IPatientAppointmentService) reg.lookup("patientAppointment");
 		} catch (RemoteException e) {
 			e.printStackTrace();
 		} catch (NotBoundException e) {
 			e.printStackTrace();
 		}
        
        SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddhhmmss");
        //--------------------------------------------------------------------------------------------------------------------
       if(check == 1) {
    	   apptVO = PatientVisitAppointmentController.getAppt();
    	   docVO = PatientVisitAppointmentController.getDocVO();
       }else {
    	   apptVO = PatientRMTAppointmentController.getAppt();
    	   docVO = PatientRMTAppointmentController.getDocVO();
       }
        
    	
        System.out.println("appt =>"+ apptVO.getAppt_date()+"/"+apptVO+ apptVO.getAppt_kind()+"/"+apptVO.getDoctor_num()+"/"+apptVO.getPa_id());
        date = apptVO.getAppt_date();
        sdate =fm.format(date);
        System.out.println(sdate);
        apptDateFld.setText(sdate.substring(0, 4)+"년"+sdate.substring(4, 6)+"월"+sdate.substring(6, 8)+"일"+sdate.substring(8, 10)+"시");
        
        paName = service.getPaName(apptVO.getPa_id());
        paNameFld.setText(paName);
        docName.setText(docVO.getDoctor_name());
        kindFld.setText(apptVO.getAppt_kind());
        
        
        
        
    }
    
    
}
