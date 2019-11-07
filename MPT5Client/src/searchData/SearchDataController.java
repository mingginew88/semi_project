package searchData;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import VO.MedicineVO;
import VO.PatientVO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import login.LoginId;

public class SearchDataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField searchData;

    @FXML
    private JFXComboBox<String> cbdata;

    @FXML
    private TableView tabledata;
    
    @FXML
    private Pagination pagination;
    private ISearchDataService sdService;
    private List<PatientVO> paList = new ArrayList<PatientVO>();
    private List<MedicineVO> mediList = new ArrayList<MedicineVO>();
    private final static int rowsPerPage = 10;

    @FXML
    void getSearchData(MouseEvent event) throws RemoteException {
    	if(cbdata.getSelectionModel().getSelectedItem().equals("환자명")) {
    		if(searchData.getText().isEmpty()) {
    			paList = sdService.getAllPa(Integer.parseInt(LoginId.login_Id));
    		}else {
    			paList = sdService.searchPa(searchData.getText());
    		}
    		getPaData(paList);
    	}else {
    		if(searchData.getText().isEmpty()) {
    			mediList = sdService.getAllMedi();
    		}else {
    			mediList = sdService.searchMedi(searchData.getText());
    		}
    		getMediData(mediList);
    	}
    }
    

    
    @FXML
    void initialize() throws RemoteException {
        assert searchData != null : "fx:id=\"searchData\" was not injected: check your FXML file 'searchData.fxml'.";
        assert cbdata != null : "fx:id=\"cbdata\" was not injected: check your FXML file 'searchData.fxml'.";
        assert tabledata != null : "fx:id=\"tabledata\" was not injected: check your FXML file 'searchData.fxml'.";
        cbdata.getItems().addAll("환자명","의약품명");
        cbdata.setValue("환자명");
        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
			sdService = (ISearchDataService) reg.lookup("searchData");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        paList = sdService.getAllPa(Integer.parseInt(LoginId.login_Id));
        getPaData(paList);
    }
    
    public class PaData{
    	String pa_id;
    	String pa_name;
    	String pa_gen;
    	int pa_reg1;
    	Date PaRecentVSTClinicDate;
    	PaData(String pa_id, String pa_name, String pa_gen, int pa_reg1, Date PaRecentVSTClinicDate){
    		this.pa_id=pa_id;
    		this.pa_name=pa_name;
    		this.pa_gen=pa_gen;
    		this.pa_reg1=pa_reg1;
    		this.PaRecentVSTClinicDate=PaRecentVSTClinicDate;
    	}
		public String getPa_name() {
			return pa_name;
		}
		public String getPa_id() {
			return pa_id;
		}
		public String getPa_gen() {
			return pa_gen;
		}
		public int getPa_reg1() {
			return pa_reg1;
		}
		public Date getPaRecentVSTClinicDate() {
			return PaRecentVSTClinicDate;
		}
    }
    
    private void getPaData(List<PatientVO> paList) throws RemoteException {
    	if(!tabledata.getItems().isEmpty()) {
    		tabledata.getItems().clear();
    		tabledata.getColumns().clear();
    	}
    	
    	List<PaData> paData = new ArrayList<PaData>();
    	
    	for(int i=0; i<paList.size(); i++) {
    		PaData pd = new PaData(
    				paList.get(i).getPa_id(), paList.get(i).getPa_name(), 
    				paList.get(i).getPa_gen(),paList.get(i).getPa_reg1(),
    				sdService.getPaRecentVSTClinicDate(paList.get(i).getPa_id()));
    		paData.add(pd);
    	}
    	
    	ObservableList<PaData> data = FXCollections.observableArrayList(paData);
    	
    	page(paData, pagination, tabledata);
    	tabledata.setItems(data);
    	TableColumn<PaData, String> PaNameCol = new TableColumn<PaData, String>("이름");
    	PaNameCol.setCellValueFactory(new PropertyValueFactory<PaData, String>("pa_name"));
    	PaNameCol.setPrefWidth(200);
    	TableColumn<PaData, String> PaGenCol = new TableColumn<PaData, String>("성별");
    	PaGenCol.setCellValueFactory(new PropertyValueFactory<PaData, String>("pa_gen"));
    	PaGenCol.setPrefWidth(200);
    	TableColumn<PaData, Integer> PaRegCol = new TableColumn<PaData, Integer>("생년월일");
    	PaRegCol.setCellValueFactory(new PropertyValueFactory<PaData, Integer>("pa_reg1"));
    	PaRegCol.setPrefWidth(200);
    	TableColumn<PaData, Date> PaVCDCol = new TableColumn<PaData, Date>("최근 진료일");
    	PaVCDCol.setCellValueFactory(new PropertyValueFactory<PaData, Date>("PaRecentVSTClinicDate"));
    	PaVCDCol.setPrefWidth(200);
    	PaVCDCol.setCellFactory(column -> {
    	    TableCell<PaData, Date> cell = new TableCell<PaData, Date>() {
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
    	TableColumn strCol = new TableColumn<String, String>("비고");
    	strCol.setPrefWidth(290);
    	tabledata.getColumns().addAll(PaNameCol, PaGenCol, PaRegCol,PaVCDCol, strCol);
    }
    
    
    private void getMediData(List<MedicineVO> mediList) {
    	if(!tabledata.getItems().isEmpty()) {
    		tabledata.getItems().clear();
    		tabledata.getColumns().clear();
    	}
//    	tabledata.setItems(FXCollections.observableArrayList());
    	ObservableList<MedicineVO> data = FXCollections.observableArrayList(mediList);
    	
    	page(mediList, pagination, tabledata);
    	tabledata.setItems(data);
    	
    	TableColumn<MedicineVO, Integer> MediCodeCol = new TableColumn<MedicineVO, Integer>("의약품 코드");
    	MediCodeCol.setCellValueFactory(new PropertyValueFactory<MedicineVO, Integer>("medi_code"));
    	MediCodeCol.setPrefWidth(250);
    	TableColumn<MedicineVO, String> MediNameCol = new TableColumn<MedicineVO, String>("의약품명");
    	MediNameCol.setCellValueFactory(new PropertyValueFactory<MedicineVO, String>("medi_name"));
    	MediNameCol.setPrefWidth(250);
    	TableColumn<MedicineVO, String> MediCateCol = new TableColumn<MedicineVO, String>("분류");
    	MediCateCol.setCellValueFactory(new PropertyValueFactory<MedicineVO, String>("medi_category"));
    	MediCateCol.setPrefWidth(250);
    	TableColumn<MedicineVO, String> MediPMSCol = new TableColumn<MedicineVO, String>("복용량");
    	MediPMSCol.setCellValueFactory(new PropertyValueFactory<MedicineVO, String>("medi_pms"));
    	MediPMSCol.setPrefWidth(250);
    	TableColumn<MedicineVO, String> MediDOSECol = new TableColumn<MedicineVO, String>("허가유무");
    	MediDOSECol.setCellValueFactory(new PropertyValueFactory<MedicineVO, String>("medi_dose"));
    	MediDOSECol.setPrefWidth(90);
    	
    	tabledata.getColumns().addAll(MediCodeCol, MediNameCol, MediCateCol, MediPMSCol, MediDOSECol);
    }
    
    public void page(List data, Pagination pg, TableView table) {
        int totalPage = data.size()/rowsPerPage + (data.size()%rowsPerPage>0? 1:0);
         pg.setPageCount(totalPage);
         pg.setCurrentPageIndex(0);
         changeTableView(0,table,data);
         
         pg.currentPageIndexProperty().addListener(new ChangeListener<Number>() {

          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
             changeTableView(newValue.intValue(), table, data);
             
          }
            
       });
         
     }
     
     public void changeTableView(int index, TableView table, List data) {
       int startIndex = index * rowsPerPage;  // 0    10
       int endIndex = Math.min(startIndex+rowsPerPage, data.size());  //10 20
       
       // 시작 인덱스부터 종료인덱스 이전까지의 자료를 추출
       table.setItems(FXCollections.observableArrayList(data.subList(startIndex, endIndex)));
     }
}
