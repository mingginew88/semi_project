package patient;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import VO.AppointmentVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import login.LoginId;

public class PatientMainSearchApptController {
	
	public void setPaName(String paName) {
		LabelName.setText(paName);
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<AppointmentVO, Integer> ApptNumCol;

    @FXML
    private TableColumn<AppointmentVO, Date> ApptDateCol;

    @FXML
    private TableColumn<AppointmentVO, Integer> ApptDocCol;

    @FXML
    private TableColumn<AppointmentVO, String> ApptKindCol;

    @FXML
    private Label LabelName;

    @FXML
    private Label LabelName1;

    @FXML
    private Label LabelName11;
    
    @FXML
    private TableView<AppointmentVO> tvAppt;
    
    private IPatientInfoService service;
    
    @FXML
    void initialize() throws RemoteException {
        assert ApptNumCol != null : "fx:id=\"ApptNumCol\" was not injected: check your FXML file 'PatientMainSearchAppt.fxml'.";
        assert ApptDateCol != null : "fx:id=\"ApptDateCol\" was not injected: check your FXML file 'PatientMainSearchAppt.fxml'.";
        assert ApptDocCol != null : "fx:id=\"ApptDocCol\" was not injected: check your FXML file 'PatientMainSearchAppt.fxml'.";
        assert ApptKindCol != null : "fx:id=\"ApptKindCol\" was not injected: check your FXML file 'PatientMainSearchAppt.fxml'.";
        assert LabelName != null : "fx:id=\"LabelName\" was not injected: check your FXML file 'PatientMainSearchAppt.fxml'.";
        assert LabelName1 != null : "fx:id=\"LabelName1\" was not injected: check your FXML file 'PatientMainSearchAppt.fxml'.";
        assert LabelName11 != null : "fx:id=\"LabelName11\" was not injected: check your FXML file 'PatientMainSearchAppt.fxml'.";
        
        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			service = (IPatientInfoService)reg.lookup("patient");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        List<AppointmentVO> apptList = service.searchPaAppt(LoginId.login_Id);
        ObservableList<AppointmentVO> apptData = FXCollections.observableArrayList(apptList);
        tvAppt.setItems(apptData);
        ApptNumCol.setCellValueFactory(new PropertyValueFactory<>("appt_num"));
        ApptDateCol.setCellValueFactory(new PropertyValueFactory<>("appt_date"));
        ApptDateCol.setCellFactory(column -> {
    	    TableCell<AppointmentVO, Date> cell = new TableCell<AppointmentVO, Date>() {
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
        ApptDocCol.setCellValueFactory(new PropertyValueFactory<>("doctor_num"));
        ApptKindCol.setCellValueFactory(new PropertyValueFactory<>("appt_kind"));
        
    }
}
