package admin.adminDB;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import VO.DoctorVO;
import VO.ExaminationVO;
import VO.MedicineVO;
import VO.MessageVO;
import VO.VisitclinicVO;

import java.io.IOException;
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
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class AdminDBController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Tab mediTab;

    @FXML
    private AnchorPane mediPane;

    @FXML
    private JFXTextField mediFld;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private TableView<MedicineVO> mediTable;

    @FXML
    private TableColumn<?, ?> codeCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> catCol;

    @FXML
    private TableColumn<?, ?> doseCol;

    @FXML
    private TableColumn<?, ?> pmsCol;

    @FXML
    private Pagination pg;

    @FXML
    private Tab clinicTab;

    @FXML
    private AnchorPane clinicPane;

    @FXML
    private JFXComboBox<String> cb;

    @FXML
    private JFXTextField txtFld;

    @FXML
    private JFXButton searchBtn2;

    @FXML
    private TableView clinicTable;

    @FXML
    private TableColumn<?, ?> numCol;

    @FXML
    private TableColumn<?, ?> paCol;

    @FXML
    private TableColumn<?, ?> docCol;

    @FXML
    private TableColumn<?, ?> contentCol;

    @FXML
    private TableColumn<VisitclinicVO, Date> dateCol;

    @FXML
    private Pagination pg1;

    @FXML
    private Tab msgTable;

    @FXML
    private AnchorPane msgPane;

    @FXML
    private JFXComboBox<String> msgCb;

    @FXML
    private JFXTextField msgFld;

    @FXML
    private JFXButton searchBtn3;

    @FXML
    private TableView table11;

    @FXML
    private TableColumn<?, ?> msgNumCol;

    @FXML
    private TableColumn<?, ?> sdCol;

    @FXML
    private TableColumn<?, ?> rcCol;

    @FXML
    private TableColumn<?, ?> contCol;

    @FXML
    private TableColumn<MessageVO, Date> msgDateCol;

    @FXML
    private Pagination pg11;

    @FXML
    private Tab scheTab;

    @FXML
    private AnchorPane docSchePane;
    
    
    private List medidata;
    private List vcdata;
    private List msgdata;
    
    private final static int rowsPerPage = 12;
    
    private TableView table1;
    private TableView table2;
    private TableView table3;
    
    
    private IAdminDBService service;
    
    private List<MedicineVO> medi;
    private List<MessageVO> msg;
    private List<VisitclinicVO> vc;
    
    private ObservableList<MedicineVO> mediList;
    private ObservableList<MessageVO> msgList;
    private ObservableList<VisitclinicVO> vcList;
    
    private ObservableList<String> kind = FXCollections.observableArrayList("Sender", "Receiver");
    private ObservableList<String> kind2 = FXCollections.observableArrayList("환자", "의사", "진단내용", "날짜");
    
    

    @FXML
    void search(ActionEvent event) throws RemoteException {
    	if(mediFld.getText().isEmpty()) {
    		alert("검색 오류", "약 이름을 입력해주세요");
    		mediFld.requestFocus();
    		return;
    	}
    	medi = service.searchMedi(mediFld.getText());
    	mediList = FXCollections.observableArrayList(medi);
    	
    	mediTable.setItems(mediList);
    	medidata = mediList;
    	table1 = mediTable;
    	page(medidata, pg, mediTable);
    	
    }

    @FXML
    void searchClinic(ActionEvent event) throws RemoteException {
    	String field ="";
    	HashMap<String, String> searchMap = new HashMap<String, String>();
    	if(txtFld.getText().isEmpty()) {
    		alert("검색오류","검색할 내용을 입력해 주세요");
    		txtFld.requestFocus();
    		return;
    	}
    	if(cb.getValue().equals("환자")) {
    		field="pa_id";
    		searchMap.put("searchField", field);
    		searchMap.put("pa_name", txtFld.getText());
    		vc = service.searchVs(searchMap);
    	}
    	if(cb.getValue().equals("의사")) {
    		field="doctor_num";
    		searchMap.put("searchField", field);
    		searchMap.put("doctor_name", txtFld.getText());
    		vc = service.searchVs(searchMap);
    	}
    	if(cb.getValue().equals("진단내용")) {
    		field="vstclinic_cont";
    		searchMap.put("searchField", field);
    		searchMap.put("searchData", txtFld.getText());
    		vc = service.searchVs(searchMap);
    	}
    	if(cb.getValue().equals("날짜")) {
    		field="vstclinic_date";
    		searchMap.put("searchField", field);
    		searchMap.put("searchData", txtFld.getText());
    		vc = service.searchVs(searchMap);
    	}
    	vcList = FXCollections.observableArrayList(vc);
    	clinicTable.setItems(vcList);
    	vcdata=vcList;
    	table3 = clinicTable;
    	page(vcdata,pg1,table3);
    }

    @FXML
    void searchMsg(ActionEvent event) throws RemoteException {
    		
    	if(msgFld.getText().isEmpty()) {
    		alert("검색 오류", "검색할 의사 이름을 입력하세요");
    		msgFld.requestFocus();
    		return;
    	}
    	if(cb.getSelectionModel().equals("Sender")) {
    		msg = service.searchSdMsg(msgFld.getText());
    	}else {
    		msg = service.searchRcMsg(msgFld.getText());
    	}
    	msgList = FXCollections.observableArrayList(msg);
    	table11.setItems(msgList);
    	
    	msgdata = msgList;
    	table2 = table11;
    	page(msgdata,pg11,table2);
    }

    @FXML
    void initialize() throws RemoteException {
        assert mediTab != null : "fx:id=\"mediTab\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert mediPane != null : "fx:id=\"mediPane\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert mediFld != null : "fx:id=\"mediFld\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert searchBtn != null : "fx:id=\"searchBtn\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert mediTable != null : "fx:id=\"mediTable\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert codeCol != null : "fx:id=\"codeCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert catCol != null : "fx:id=\"catCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert doseCol != null : "fx:id=\"doseCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert pmsCol != null : "fx:id=\"pmsCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert pg != null : "fx:id=\"pg\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert clinicTab != null : "fx:id=\"clinicTab\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert clinicPane != null : "fx:id=\"clinicPane\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert cb != null : "fx:id=\"cb\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert txtFld != null : "fx:id=\"txtFld\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert searchBtn2 != null : "fx:id=\"searchBtn2\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert clinicTable != null : "fx:id=\"clinicTable\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert numCol != null : "fx:id=\"numCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert paCol != null : "fx:id=\"paCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert docCol != null : "fx:id=\"docCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert contentCol != null : "fx:id=\"contentCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert dateCol != null : "fx:id=\"dateCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert pg1 != null : "fx:id=\"pg1\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert msgTable != null : "fx:id=\"msgTable\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert msgPane != null : "fx:id=\"msgPane\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert msgCb != null : "fx:id=\"msgCb\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert msgFld != null : "fx:id=\"msgFld\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert searchBtn3 != null : "fx:id=\"searchBtn3\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert table11 != null : "fx:id=\"table11\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert msgNumCol != null : "fx:id=\"msgNumCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert sdCol != null : "fx:id=\"sdCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert rcCol != null : "fx:id=\"rcCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert contCol != null : "fx:id=\"contCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert msgDateCol != null : "fx:id=\"msgDateCol\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert pg11 != null : "fx:id=\"pg11\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert scheTab != null : "fx:id=\"scheTab\" was not injected: check your FXML file 'AdminDB.fxml'.";
        assert docSchePane != null : "fx:id=\"docSchePane\" was not injected: check your FXML file 'AdminDB.fxml'.";
        //-------------------------------------------------------------------------------------------------------------
        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			service = (IAdminDBService)reg.lookup("adminDB");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        Parent loader;
		try {
			loader = FXMLLoader.load(getClass().getResource("../../view/AdminDocSche.fxml"));
			docSchePane.getChildren().add(loader);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        //-------------------------------------------------------------------------------------------------------------
        medi = service.getAllMedi();
        codeCol.setCellValueFactory(new PropertyValueFactory<>("medi_code"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("medi_name"));
        catCol.setCellValueFactory(new PropertyValueFactory<>("medi_category"));
        doseCol.setCellValueFactory(new PropertyValueFactory<>("medi_pms"));
        pmsCol.setCellValueFactory(new PropertyValueFactory<>("medi_dose"));
        mediList = FXCollections.observableArrayList(medi);
        mediTable.setItems(mediList);
        msgdata =mediList;
        table1 = mediTable;
        page(msgdata, pg, table1);
        //-------------------------------------------------------------------------------------------------------------
        msg = service.getMsgList();
        
        
        cb.setItems(kind2);
        msgCb.setItems(kind);
        
        msgNumCol.setCellValueFactory(new PropertyValueFactory<>("msg_num"));
        sdCol.setCellValueFactory(new PropertyValueFactory<>("msg_sd"));
        rcCol.setCellValueFactory(new PropertyValueFactory<>("msg_rc"));
        contCol.setCellValueFactory(new PropertyValueFactory<>("msg_cont"));
        msgDateCol.setCellValueFactory(new PropertyValueFactory<>("msg_date"));
        
        msgDateCol.setCellFactory(column -> {
    	    TableCell<MessageVO,Date> cell = new TableCell<MessageVO, Date>() {
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
        
        
        msgList = FXCollections.observableArrayList(msg);
        
        table11.setItems(msgList);
        msgdata = msgList;
        table2 = table11;
        page(msgdata, pg11, table2);
        //-------------------------------------------------------------------------------------------------------------
        
        vc = service.getVCList();
//        
//        
        numCol.setCellValueFactory(new PropertyValueFactory<>("clinic_num"));
        paCol.setCellValueFactory(new PropertyValueFactory<>("pa_id"));
        docCol.setCellValueFactory(new PropertyValueFactory<>("doctor_num"));
        contentCol.setCellValueFactory(new PropertyValueFactory<>("vstclinic_cont"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("vstclinic_Date"));
        
        dateCol.setCellFactory(column -> {
    	    TableCell<VisitclinicVO,Date> cell = new TableCell<VisitclinicVO, Date>() {
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
       
        vcList = FXCollections.observableArrayList(vc);
        
        clinicTable.setItems(vcList);
        vcdata = vcList;
        table3 = clinicTable;
        page(vcdata, pg1, table3);
        
        
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
    
    public void alert(String head, String msg) {
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("알림");
    	alert.setHeaderText(head);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
}
