package admin.memList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import VO.DoctorVO;
import VO.PatientVO;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.ILoginService;

public class MemListController {
	
	private Stage parentStage;

	public void setParentStage(Stage parentStage) {
		this.parentStage = parentStage;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXComboBox<String> combo1;

    @FXML
    private JFXComboBox<String> combo2;

    @FXML
    private JFXTextField txtFld;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private Pagination pg;
    
    @FXML
    private TableView table;  
    
    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> genderCol;

    @FXML
    private TableColumn<?, ?> regCol;

    @FXML
    private TableColumn<?, ?> addrCol;

    
    private ObservableList<PatientVO> patientList;
    private ObservableList<DoctorVO> doctorList;
    
    private IMemListService service;
    
    private String strWork = "";
    
    private PatientVO pa;
    private DoctorVO doc;
    
    
    private PatientDetailController pdc;
    private DoctorDetailController ddc;
    
    
    
    
   
	public TableView getTable() {
		return table;
	}

	public void setTable(TableView table) {
		this.table = table;
	}

	@FXML
    void addMember(ActionEvent event) {
    	
    }

    @FXML
    void search(ActionEvent event) {
    	if(txtFld.getText().isEmpty()) {
    		alert("입력 오류", "검색할 내용을 입력해 주세요");
    	}
    	
    	String searchData = txtFld.getText();
    	String field = "";
    	List<PatientVO> paList = null;
    	List<DoctorVO> docList = null;
    	HashMap<String, String> searchMap = new HashMap<String, String>();
    	
    	if(combo1.getValue().equals("환자")) {
    		if(combo2.getValue().equals("ID")) {
    			
    			field = "pa_id";
    			searchMap.put("searchField", field);
    			searchMap.put("searchData", txtFld.getText());
    			
    			
    		}
    		if(combo2.getValue().equals("이름")) {
    			field = "pa_name";
    			searchMap.put("searchField", field);
    			searchMap.put("searchData", txtFld.getText());
    			
    		}
    		if(combo2.getValue().equals("생년월일")) {
    			field = "pa_reg1";
    			searchMap.put("searchField", field);
    			searchMap.put("searchData", txtFld.getText());
    			
    		}
    		if(combo2.getValue().equals("주소")) {
    			field = "pa_addr";
    			searchMap.put("searchField", field);
    			searchMap.put("searchData", txtFld.getText());
    		}
    		paList = service.getSearchPatient(searchMap);
    		patientList = FXCollections.observableArrayList(paList);
    		table.setItems(patientList);
    	}
    	if(combo1.getValue().equals("의사")) {
    		if(combo2.getValue().equals("ID")) {
    			field ="doctor_num";
    			searchMap.put("searchField", field);
    			searchMap.put("searchData", txtFld.getText());
    		}if(combo2.getValue().equals("이름")) {
    			field = "doctor_name";
    			searchMap.put("searchField", field);
    			searchMap.put("searchData", txtFld.getText());
    			
    		}
    		if(combo2.getValue().equals("생년월일")) {
    			field = "doctor_reg1";
    			searchMap.put("searchField", field);
    			searchMap.put("searchData", txtFld.getText());
    			
    		}
    		if(combo2.getValue().equals("주소")) {
    			field = "doctor_addr";
    			searchMap.put("searchField", field);
    			searchMap.put("searchData", txtFld.getText());
    		}
    		docList = service.getSearchDoctor(searchMap);
    		doctorList = FXCollections.observableArrayList(docList);
    		table.setItems(doctorList);
    		data=doctorList;
    		page(data);
    	}
    	
    }

    @FXML
    void showDetails(MouseEvent event) {
    	Stage showDetailDialog = new Stage(StageStyle.DECORATED);
    	showDetailDialog.initModality(Modality.WINDOW_MODAL);
    	showDetailDialog.initOwner(parentStage);
    	if(combo1.getValue().equals("환자")) {
    		
    		try {
    			pa =(PatientVO) table.getSelectionModel().getSelectedItem();
    			
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PatientDetail.fxml"));
    			Parent chRoot = loader.load();
    			pdc = loader.getController();
    			pdc.setPa(pa);
    			
    			PatientDetailController patientDetailController = loader.getController();
    			patientDetailController.setParentStage(parentStage);
    			
    			Scene scene = new Scene(chRoot);
    			showDetailDialog.setScene(scene);
    			showDetailDialog.show();
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}else {
    		try {
				doc = (DoctorVO) table.getSelectionModel().getSelectedItem();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/DoctorDetail.fxml"));
				Parent chRoot = loader.load();
				ddc = loader.getController();
				ddc.setDoc(doc);
				
				DoctorDetailController doctorDetailController = loader.getController();
				doctorDetailController.setParentStage(parentStage);
				
				Scene scene = new Scene(chRoot);
				showDetailDialog.setScene(scene);
				showDetailDialog.show();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	
		
		
    }
    
    @FXML
    void selectMemKind(ActionEvent event) {
    	if(combo1.getValue()=="의사") {
    		List<DoctorVO> docList = service.getAllDoctor();
    		
    		idCol.setCellValueFactory(new PropertyValueFactory<>("doctor_num"));
    		nameCol.setCellValueFactory(new PropertyValueFactory<>("doctor_name"));
    		genderCol.setCellValueFactory(new PropertyValueFactory<>("doctor_gen"));
    		regCol.setCellValueFactory(new PropertyValueFactory<>("doctor_reg1"));
    		addrCol.setCellValueFactory(new PropertyValueFactory<>("doctor_addr"));
    		
    		doctorList = FXCollections.observableArrayList(docList);
    		table.setItems(doctorList);
    		data = doctorList;
    		page(data);
    		
    	}else {
    		 List<PatientVO> paList = service.getAllPatient();
    	        
    	        
    	        
    	        idCol.setCellValueFactory(new PropertyValueFactory<>("pa_id"));
    	        nameCol.setCellValueFactory(new PropertyValueFactory<>("pa_name"));
    	        genderCol.setCellValueFactory(new PropertyValueFactory<>("pa_gen"));
    	        regCol.setCellValueFactory(new PropertyValueFactory<>("pa_reg1"));
    	        addrCol.setCellValueFactory(new PropertyValueFactory<>("pa_addr"));
    	        patientList = FXCollections.observableArrayList(paList);

    	        table.setItems(patientList);
    	        data = patientList;
    	        page(data);
    	}
    }
    
//    private ObservableList<String> memKind;
//    private ObservableList<String> kind;
    
    private final static int rowsPerPage = 12;
    private List data;

    @FXML
    void initialize() {
    	assert combo1 != null : "fx:id=\"combo1\" was not injected: check your FXML file 'MemList.fxml'.";
        assert combo2 != null : "fx:id=\"combo2\" was not injected: check your FXML file 'MemList.fxml'.";
        assert txtFld != null : "fx:id=\"txtFld\" was not injected: check your FXML file 'MemList.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'MemList.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'MemList.fxml'.";
        assert pg != null : "fx:id=\"pg\" was not injected: check your FXML file 'MemList.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'MemList.fxml'.";
        
        assert idCol != null : "fx:id=\"idCol\" was not injected: check your FXML file 'MemList.fxml'.";
        assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'MemList.fxml'.";
        assert genderCol != null : "fx:id=\"genderCol\" was not injected: check your FXML file 'MemList.fxml'.";
        assert regCol != null : "fx:id=\"regCol\" was not injected: check your FXML file 'MemList.fxml'.";
        assert addrCol != null : "fx:id=\"addrCol\" was not injected: check your FXML file 'MemList.fxml'.";
        
        ObservableList<String> memKind = FXCollections.observableArrayList("환자","의사");
        ObservableList<String> kind = FXCollections.observableArrayList("ID", "이름", "생년월일", "주소");
        
        combo1.setItems(memKind);
        combo2.setItems(kind);
        //---------------------------------------------------------------------------------------------------------

        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			service = (IMemListService)reg.lookup("memberList");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        
        combo1.setValue("환자");
        combo2.setValue("ID");
        List<PatientVO> paList = service.getAllPatient();
        
        
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("pa_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("pa_name"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("pa_gen"));
        regCol.setCellValueFactory(new PropertyValueFactory<>("pa_reg1"));
        addrCol.setCellValueFactory(new PropertyValueFactory<>("pa_addr"));
        patientList = FXCollections.observableArrayList(paList);

        table.setItems(patientList);
        
        data = patientList;
        page(data);
        //----------------------------------------------------------------
        
        

    }
    public void page(List data) {
    	int totalPage = data.size()/rowsPerPage + (data.size()%rowsPerPage>0? 1:0);
        pg.setPageCount(totalPage);
        pg.setCurrentPageIndex(0);
        changeTableView(0);
        
        pg.currentPageIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				changeTableView(newValue.intValue());
				
			}
        	
		});
        
    }
    public void changeTableView(int index) {
		int startIndex = index * rowsPerPage;  // 0    10
		int endIndex = Math.min(startIndex+rowsPerPage, data.size());  //10 20
		
		// 시작 인덱스부터 종료인덱스 이전까지의 자료를 추출
		table.setItems(FXCollections.observableArrayList(data.subList(startIndex, endIndex)));
    }
    
    public void alert(String head, String msg) {
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("알림");
    	alert.setHeaderText(head);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
}
