package board.notice;

import java.io.IOException;
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

import VO.NoticeVO;
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
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import login.LoginId;

public class NoticeMainController {

	private ObservableList<NoticeVO> dataList;

	private NoticeServiceInf noticeService;

	//한 화면에 출력될 데이터 개수
	private final static int rowsPerPage = 12;   // 한페이지에 넣고싶은 데이터 개수
	private List data;  // 테이블에 넣을 데이터 리스트형식
	
	private Stage primaryStage;
	private BorderPane borderpane;
	public void setPrimaryStage(Stage primaryStage, BorderPane borderpane) {
		this.primaryStage = primaryStage;
		this.borderpane=borderpane;
	}
	
	private NoticeVO noticeVo;
	public void setNoticeVo(NoticeVO noticeVo) {
		this.noticeVo = noticeVo;
	}
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField searchText;

	@FXML
	private Button searchButton;

	@FXML
	private Button createButton;

	@FXML
	private TableView<NoticeVO> tableView;

	@FXML
	private TableColumn<?, ?> numCol;

	@FXML
	private TableColumn<?, ?> titleCol;

	@FXML
	private TableColumn<?, ?> writerCol;

	@FXML
	private TableColumn<NoticeVO, Date> dateCol;

	@FXML
	private Pagination pagination;

	@FXML
	void createButtonAct(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/NoticeNew.fxml"));
			Parent newNotice = loader.load();
			
			NoticeNewController noticeNewController = loader.getController();
			noticeNewController.setPrimaryStage(primaryStage, borderpane);
			borderpane.setCenter(newNotice);
			
//			((TabPane)(((BorderPane)((StackPane)createButton.getScene().getRoot()).getChildren().get(0)).getCenter())).
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void searchButtonAct(ActionEvent event) {
		if(searchText.getText().isEmpty()) {
			alert("warning","검색할 값을 입력하세요");
			String id = LoginId.login_Id;
			System.out.println(id);
			return;
		}

		String searchData = searchText.getText();
		List<NoticeVO> list = null;
		try {
			list = noticeService.searchNotice(searchData);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		dataList = FXCollections.observableArrayList(list);
		tableView.setItems(dataList);
	}
	
	@FXML
	void dataClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/NoticeContent.fxml"));
			Parent root = loader.load();

			NoticeVO nvo = tableView.getSelectionModel().getSelectedItem();
			if(tableView.getSelectionModel().isEmpty()) {
				return;
			}

			NoticeContentController noticeContentController = loader.getController();
			noticeContentController.setPrimaryStage(primaryStage, borderpane);
			noticeContentController.setNoticeVo(nvo);
			
			borderpane.setCenter(root);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void checkId () {
		
	}
	
	int alert(String msgType, String msg){
		int result = 0;
		if(msgType.equals("warning")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("경고");
			alert.setHeaderText("경고");
			alert.setContentText(msg);
			alert.showAndWait();
		}else if(msgType.equals("info")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("알림");
			alert.setHeaderText("알림");
			alert.setContentText(msg);
			alert.showAndWait();
		}
		return result;
	}


	@FXML
	void initialize() {
		assert searchText != null : "fx:id=\"searchText\" was not injected: check your FXML file 'NoticeMain.fxml'.";
		assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'NoticeMain.fxml'.";
		assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'NoticeMain.fxml'.";
		assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'NoticeMain.fxml'.";
		assert numCol != null : "fx:id=\"numCol\" was not injected: check your FXML file 'NoticeMain.fxml'.";
		assert titleCol != null : "fx:id=\"titleCol\" was not injected: check your FXML file 'NoticeMain.fxml'.";
		assert writerCol != null : "fx:id=\"writerCol\" was not injected: check your FXML file 'NoticeMain.fxml'.";
		assert dateCol != null : "fx:id=\"dateCol\" was not injected: check your FXML file 'NoticeMain.fxml'.";
		assert pagination != null : "fx:id=\"pagenation\" was not injected: check your FXML file 'NoticeMain.fxml'.";
		
		String id = LoginId.login_Id;
		if(id.equals("admin")) {
			createButton.setDisable(false);
		}else if(id.equals("master")) {
			createButton.setDisable(false);
		}else {
			createButton.setDisable(true);
		}
		
		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			noticeService = (NoticeServiceInf)reg.lookup("Notice");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		List<NoticeVO> noticeList = new ArrayList<NoticeVO>();
		try {
			noticeList = noticeService.getAllNotice();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		dataList = FXCollections.observableArrayList(noticeList);
		tableView.setItems(dataList);
		data=noticeList;
		
		numCol.setCellValueFactory(new PropertyValueFactory<>("notice_num"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("notice_title"));
		writerCol.setCellValueFactory(new PropertyValueFactory<>("notice_writer"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("notice_date"));
		dateCol.setCellFactory(column -> {
    	    TableCell<NoticeVO, Date> cell = new TableCell<NoticeVO, Date>() {
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
	
	// 추가할 메서드들
	public void page(List data) {
	    	int totalPage = data.size()/rowsPerPage + (data.size()%rowsPerPage>0? 1:0);
	    	pagination.setPageCount(totalPage);
	    	pagination.setCurrentPageIndex(0);
	        changeTableView(0);
	        
	        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {

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
			tableView.setItems(FXCollections.observableArrayList(data.subList(startIndex, endIndex)));
	    }
}
