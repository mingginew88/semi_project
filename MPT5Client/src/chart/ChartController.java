package chart;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.collections.ObservableSetWrapper;

import VO.AppointmentVO;
import VO.ExaminationVO;
import appointment.IAppointmentService;
import disease.IDiseaseService;
import examination.IExaminationService;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import login.ILoginService;

public class ChartController {
	private ExaminationVO expvo = new ExaminationVO(); // 임시
	// 날짜를 가져오기 위한 인터페이스
	private IExaminationService iexaminationService;
	private ILoginService iloginService;
	private IDiseaseService idiseaseService;
	// private String loginId = LoginId.login_Id;
	private String loginId = "test3";
	private String docname;

	private List<String> dateCategoriesList = new ArrayList<>();
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXComboBox<Date> apptDateCbx;

	@FXML
	private JFXTextField diseaseTf;

	@FXML
	private JFXTextField feverTf;

	@FXML
	private JFXTextField redBloodTf;

	@FXML
	private JFXTextField whiteBloodTf;

	@FXML
	private JFXTextField plateletTf;

	@FXML
	private JFXTextField bad1Tf;

	@FXML
	private JFXTextField bad2Tf;

	@FXML
	private JFXTextField badTf3;

	@FXML
	private TextArea examContArea;

	@FXML
	private VBox chartVbox;

	@FXML
	private JFXButton chart1Btn;

	@FXML
	private JFXButton chart2Btn;

	@FXML
	void chart1BtnOnClick(ActionEvent event) {
		chartVbox.getChildren().clear();
		if (expvo == null) {
			alert("검사 날짜를 선택해 주세요");
			return;
		}
		ObservableList<PieChart.Data> pieChartData = createData();

		final DoughnutChart chart = new DoughnutChart(pieChartData);
		chart.setTitle("Imported Fruits");
		chartVbox.getChildren().add(chart);

		//////////////////////////////////////////////////////////// 여기 까지 도넛
		//////////////////////////////////////////////////////////////////////////////////
		// Stacked Bar Chart
		
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
		
		xAxis.setLabel("검사 날짜");
		for (int i = 0; i < examObList.size(); i++) {
			dateCategoriesList.add(examObList.get(i).getExam_rec_date() + "");// String으로 형변환
		}
		ObservableList<String> dateCategories = FXCollections.observableArrayList(dateCategoriesList);
		// xAxis 카테고리에 날짜를 넣기위해 만든 배열
		xAxis.setCategories(dateCategories);
		yAxis.setLabel("Value");
		stackedBarChart.setTitle("StackedBarChart");
		stackedBarChart.getData().addAll(createDataXYChart());
		
		chartVbox.getChildren().add(stackedBarChart);
		dateCategoriesList.clear();
	}

	@FXML
	void chart2BtnOnClick(ActionEvent event) {
		chartVbox.getChildren().clear();

		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
		
		xAxis.setLabel("검사 날짜");
		for (int i = 0; i < examObList.size(); i++) {
			dateCategoriesList.add(examObList.get(i).getExam_rec_date() + "");// String으로 형변환
		}
		ObservableList<String> dateCategories = FXCollections.observableArrayList(dateCategoriesList);
		// xAxis 카테고리에 날짜를 넣기위해 만든 배열
		xAxis.setCategories(dateCategories);
		yAxis.setLabel("Value");
		stackedBarChart.setTitle("StackedBarChart");
		stackedBarChart.getData().addAll(createDataXYChart());
		stackedBarChart.getStylesheets().add(this.getClass().getResource("/style/style.css").toExternalForm());
		chartVbox.getChildren().add(stackedBarChart);
		dateCategoriesList.clear();
		//////////////////////////////////////////////////////////////////////////////////
		// Stacked Bar Chart
		if (expvo == null) {
			alert("검사 날짜를 선택해 주세요");
			return;
		}
		ObservableList<PieChart.Data> pieChartData = createData();

		final DoughnutChart chart = new DoughnutChart(pieChartData);
		chart.setTitle("Imported Fruits");
		chart.getStylesheets().add(this.getClass().getResource("/style/style.css").toExternalForm());
		chartVbox.getChildren().add(chart);

		//////////////////////////////////////////////////////////// 여기 까지 도넛
		
		
		
	}

	List<ExaminationVO> examList = new ArrayList<ExaminationVO>();
	ObservableList<ExaminationVO> examObList;

	@FXML
	void initialize() throws RemoteException {
		assert apptDateCbx != null : "fx:id=\"apptDateCbx\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert diseaseTf != null : "fx:id=\"diseaseTf\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert feverTf != null : "fx:id=\"feverTf\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert redBloodTf != null : "fx:id=\"redBloodTf\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert whiteBloodTf != null : "fx:id=\"whiteBloodTf\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert plateletTf != null : "fx:id=\"plateletTf\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert bad1Tf != null : "fx:id=\"bad1Tf\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert bad2Tf != null : "fx:id=\"bad2Tf\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert badTf3 != null : "fx:id=\"badTf3\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert examContArea != null : "fx:id=\"examContArea\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert chartVbox != null : "fx:id=\"chartVbox\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert chart1Btn != null : "fx:id=\"chart1Btn\" was not injected: check your FXML file 'PatientChart.fxml'.";
		assert chart2Btn != null : "fx:id=\"chart2Btn\" was not injected: check your FXML file 'PatientChart.fxml'.";

		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
			iloginService = (ILoginService) reg.lookup("Login");
			iexaminationService = (IExaminationService) reg.lookup("examination");
			idiseaseService = (IDiseaseService) reg.lookup("disease");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// FX아이템에 넣기위한 배열로 만들어 주자
		//
		examList = iexaminationService.searchExaminationAll(loginId);
		examObList = FXCollections.observableArrayList(examList);

		// 콤보박스에 Date 삽입
		// 콤보박스에 아이템을 삽입하려면 꼭 getItems를 한 후 하자 setValue랑은 다른 문제다
		for (int i = 0; i < examObList.size(); i++) {
			apptDateCbx.getItems().addAll(examObList.get(i).getExam_rec_date());
		}

		apptDateCbx.valueProperty().addListener(new ChangeListener<Date>() {
			@Override
			public void changed(ObservableValue<? extends Date> observable, Date oldValue, Date newValue) {
				// 콤보박스에서 선택한 날짜와 동일한 정보를 세팅해줘야 하기 때문에 newValue와 일치하는 VO를 검색
				for (int i = 0; i < examObList.size(); i++) {
					if (examObList.get(i).getExam_rec_date().equals(newValue)) {
						expvo = examObList.get(i);
					}
				}
				try {
					// 의사 소견서에 의사 이름을 출력
					docname = iloginService.getDoctorInfo(expvo.getDoctor_num()).getDoctor_name();
					examContArea.setText(docname + " 의사의 소견 : \n");
					examContArea.appendText(expvo.getExam_cont() + "\n 충분한 휴식을 취하세요");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				diseaseTf.setText(docname);
				feverTf.setText("검사 당일 온도 는" + expvo.getFever() + "입니다.");
				redBloodTf.setText("적혈구 수치 : " + expvo.getRed_blood_cell());
				whiteBloodTf.setText("백혈구 수치 : " + expvo.getWhite_blood_cell());
				plateletTf.setText("혈소판 수치 :" + expvo.getPlatelet());
				bad1Tf.setText("간 수치 : " + expvo.getExam_bad1());
				bad2Tf.setText("콜레스테롤 수치 :" + expvo.getExam_bad2());
				badTf3.setText("당 수치 : " + expvo.getExam_bad3());

			}
		});

	}

	// alert창
	public void alert(String msg) {
		Alert alertWarning = new Alert(AlertType.WARNING);
		alertWarning.setTitle("경고");
		alertWarning.setContentText(msg);
		alertWarning.showAndWait();
	}

	// info창
	public void info(String msg) {
		Alert alertInfo = new Alert(AlertType.INFORMATION);
		alertInfo.setTitle("확인");
		alertInfo.setContentText(msg);
		alertInfo.showAndWait();
	}

	public ObservableList<PieChart.Data> createData() {
		return FXCollections.observableArrayList(new PieChart.Data("FEVER", expvo.getFever()),
				new PieChart.Data("적혈구 수치", expvo.getRed_blood_cell()),
				new PieChart.Data("백혈구 수치", expvo.getWhite_blood_cell()),
				new PieChart.Data("혈소판 수치", expvo.getPlatelet()), new PieChart.Data("EXAM_BAD1", expvo.getExam_bad1()),
				new PieChart.Data("EXAM_BAD2", expvo.getExam_bad2()),
				new PieChart.Data("EXAM_BAD3", expvo.getExam_bad3()));
	}

	public ObservableList<XYChart.Series<String, Number>> createDataXYChart() {
		ExaminationVO expvo1 = new ExaminationVO();
		ObservableList<XYChart.Series<String, Number>> seriesList = FXCollections.observableArrayList();
		// 나타낼 데이터 숫자만큼
		XYChart.Series<String, Number> series = new XYChart.Series();
		series.setName("FEVER");
		XYChart.Series<String, Number> series2 = new XYChart.Series();
		series2.setName("적혈구 수치");
		XYChart.Series<String, Number> series3 = new XYChart.Series();
		series3.setName("백혈구 수치");
		XYChart.Series<String, Number> series4 = new XYChart.Series();
		series4.setName("혈소판 수치");
		XYChart.Series<String, Number> series5 = new XYChart.Series();
		series5.setName("간 수치");
		XYChart.Series<String, Number> series6 = new XYChart.Series();
		series6.setName("당 수치");
		XYChart.Series<String, Number> series7 = new XYChart.Series();
		series7.setName("콜레스트롤 수치");
		for (int i = 0; i < dateCategoriesList.size(); i++) {
			// i번째 검사 날짜에 입력된 값을 가져오기 위해 회전하는 for문
			for (int j = 0; j < examObList.size(); j++) {
				if (examObList.get(j).getExam_rec_date().toString().equals(dateCategoriesList.get(i))) {
					
					expvo1 = examObList.get(j);
				}

			}
			series.getData().add(new XYChart.Data(dateCategoriesList.get(i), expvo1.getFever()));
			series2.getData().add(new XYChart.Data(dateCategoriesList.get(i), expvo1.getRed_blood_cell()));
			series3.getData().add(new XYChart.Data(dateCategoriesList.get(i), expvo1.getWhite_blood_cell()));
			series4.getData().add(new XYChart.Data(dateCategoriesList.get(i), expvo1.getPlatelet()));
			series5.getData().add(new XYChart.Data(dateCategoriesList.get(i), expvo1.getExam_bad1()));
			series6.getData().add(new XYChart.Data(dateCategoriesList.get(i), expvo1.getExam_bad2()));
			series7.getData().add(new XYChart.Data(dateCategoriesList.get(i), expvo1.getExam_bad3()));
		}
		seriesList.add(series);
		seriesList.add(series2);
		seriesList.add(series3);
		seriesList.add(series4);
		seriesList.add(series5);
		seriesList.add(series6);
		seriesList.add(series7);

		return seriesList;
	}
}
