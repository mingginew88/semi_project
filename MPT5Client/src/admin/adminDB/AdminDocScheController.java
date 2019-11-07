package admin.adminDB;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import VO.MessageVO;
import VO.ScheduleVO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import login.GetAlert;

public class AdminDocScheController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField txtFld;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private TableView<ScheduleVO> table;

    @FXML
    private TableColumn<?, ?> kindCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> contCol;

    @FXML
    private TableColumn<ScheduleVO, Date> dateCol;

    @FXML
    private Pagination pg;
    
    private IAdminDBService service;
    private List<ScheduleVO> list;
    private ObservableList<ScheduleVO> scheList;
    
    private final static int rowsPerPage = 12;
    private List data;
    private GetAlert getAlert = new GetAlert();

    @FXML
    void search(ActionEvent event) throws RemoteException {
    	if(txtFld == null) {
    		getAlert.alert("검색 오류", "일정을 검색할 의사의 이름을 입력해주세요");
    		txtFld.requestFocus();
    		
    	}
    	list = service.searchDocSche(txtFld.getText());
    	scheList = FXCollections.observableArrayList(list);
    	table.setItems(scheList);
    }

    @FXML
    void initialize() throws RemoteException {
        assert txtFld != null : "fx:id=\"mediFLd\" was not injected: check your FXML file 'AdminDocSche.fxml'.";
        assert searchBtn != null : "fx:id=\"searchBtn\" was not injected: check your FXML file 'AdminDocSche.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'AdminDocSche.fxml'.";
        assert kindCol != null : "fx:id=\"kindCol\" was not injected: check your FXML file 'AdminDocSche.fxml'.";
        assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'AdminDocSche.fxml'.";
        assert idCol != null : "fx:id=\"idCol\" was not injected: check your FXML file 'AdminDocSche.fxml'.";
        assert contCol != null : "fx:id=\"contCol\" was not injected: check your FXML file 'AdminDocSche.fxml'.";
        assert dateCol != null : "fx:id=\"dateCol\" was not injected: check your FXML file 'AdminDocSche.fxml'.";
        assert pg != null : "fx:id=\"pg\" was not injected: check your FXML file 'AdminDocSche.fxml'.";
        //-------------------------------------------------------------------------------------------------------------
        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			service = (IAdminDBService)reg.lookup("adminDB");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        //-----------------------------------------------------------------------------------------------------------
        list = service.getScheList();
        data = list;
        scheList = FXCollections.observableArrayList(list);
        table.setItems(scheList);
        kindCol.setCellValueFactory(new PropertyValueFactory<>("sche_kind"));  
        nameCol.setCellValueFactory(new PropertyValueFactory<>("sche_name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("doctor_num"));
        contCol.setCellValueFactory(new PropertyValueFactory<>("sche_cont"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("shce_startdate"));
        
        dateCol.setCellFactory(column -> {
    	    TableCell<ScheduleVO,Date> cell = new TableCell<ScheduleVO, Date>() {
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
        
        page(data);
        
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
    
   
}
