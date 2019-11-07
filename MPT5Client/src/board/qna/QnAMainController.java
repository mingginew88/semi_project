package board.qna;

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

import VO.QuestionVO;
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

public class QnAMainController {

	private ObservableList<QuestionVO> dataList;

	private QnAServiceInf qnaService;

	private final static int rowsPerPage = 12;   // 한페이지에 넣고싶은 데이터 개수
	private List data;  // 테이블에 넣을 데이터 리스트형식

	private Stage primaryStage;
	private BorderPane borderPane;
	public void setPrimaryStage(Stage primaryStage, BorderPane borderPane) {
		this.primaryStage = primaryStage;
		this.borderPane=borderPane;
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
	private TableView<QuestionVO> tableView;

	@FXML
	private TableColumn<?, ?> numCol;

	@FXML
	private TableColumn<?, ?> titleCol;

	@FXML
	private TableColumn<?, ?> writerCol;

	@FXML
	private TableColumn<QuestionVO, Date> dateCol;

	@FXML
	private Pagination pagination;

	@FXML
	void createButtonAct(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/QnANew.fxml"));
			Parent root = loader.load();

			QnANewController QnANewController = loader.getController();
			QnANewController.setPrimaryStage(primaryStage, borderPane);
			
			borderPane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void dataClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/QnAContent.fxml"));
			Parent root = loader.load();

			QuestionVO qvo = tableView.getSelectionModel().getSelectedItem();
			if(tableView.getSelectionModel().isEmpty()) {
				return;
			}
			QnAContentController qnaContentController = loader.getController();
			qnaContentController.setQuestionVo(qvo);
			qnaContentController.setPrimaryStage(primaryStage, borderPane);
			qnaContentController.content();

			borderPane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void searchButtonAct(ActionEvent event) {
		if(searchText.getText().isEmpty()) {
			alert("warning","검색할 값을 입력하세요.");

			return;
		}
		String searchData = searchText.getText();
		List<QuestionVO> list = null;
		try {
			list = qnaService.searchQuestion(searchData);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		dataList = FXCollections.observableArrayList(list);
		tableView.setItems(dataList);
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
		assert searchText != null : "fx:id=\"searchText\" was not injected: check your FXML file 'QnAMain.fxml'.";
		assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'QnAMain.fxml'.";
		assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'QnAMain.fxml'.";
		assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'QnAMain.fxml'.";
		assert numCol != null : "fx:id=\"numCol\" was not injected: check your FXML file 'QnAMain.fxml'.";
		assert titleCol != null : "fx:id=\"titleCol\" was not injected: check your FXML file 'QnAMain.fxml'.";
		assert writerCol != null : "fx:id=\"writerCol\" was not injected: check your FXML file 'QnAMain.fxml'.";
		assert dateCol != null : "fx:id=\"dateCol\" was not injected: check your FXML file 'QnAMain.fxml'.";
		assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'QnAMain.fxml'.";

		
		String id = LoginId.login_Id;//.substring(0, 5);
		int length = id.length();
		String subId="";
		if(length > 8) {
			subId = id.substring(0, 4);
			if(Integer.parseInt(subId) == 2014) {
				createButton.setDisable(true);
			}
		}else if(length < 8){
			createButton.setDisable(false);
		}
		
		
		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218",9988);
			qnaService = (QnAServiceInf)reg.lookup("Question");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		List<QuestionVO> qnaList = new ArrayList<QuestionVO>();
		try {
			qnaList = qnaService.getAllQuestion();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		dataList = FXCollections.observableArrayList(qnaList);
		tableView.setItems(dataList);

		data = qnaList;

		numCol.setCellValueFactory(new PropertyValueFactory<>("que_num"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("que_title"));
		writerCol.setCellValueFactory(new PropertyValueFactory<>("pa_id"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("que_date"));
		dateCol.setCellFactory(column -> {
			TableCell<QuestionVO, Date> cell = new TableCell<QuestionVO, Date>() {
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
