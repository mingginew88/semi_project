package schedule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import VO.ScheduleVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.LoginId;

public class ScheduleController {
	
	IScheduleService scheduleService;
	
	private String login_Id = LoginId.login_Id;
	
    public static boolean checkBoxesHaveBeenClicked = false;

    String currentDate;
    String currentMonth;
    
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

    ArrayList<ScheduleVO> allSchedule = new ArrayList<ScheduleVO>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private GridPane calendarGrid;
    
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private JFXComboBox<String> selectedYear;
    
    @FXML
    private JFXListView<String> monthSelect;
    
    @FXML
    private Label calendarNameLbl;
    
    @FXML
    private Label monthLabel;

    @FXML
    private StackPane showCalendar;

    @FXML
    private VBox centerArea;

    @FXML
    private HBox weekdayHeader;

    @FXML
    private VBox colorRootPane;
    
    //달력에 표시해주는 레이블 종류
    @FXML
    private Label lblVST;
    
    @FXML
    private Label lblRMT;
    
    @FXML
    private Label lblSemina;
    
    @FXML
    private Label lblVacation;
    
    @FXML
    private Label lblPrivateSche;

    //레이블과 일치하는 색
    @FXML
    private JFXButton VSTClinicCP;
    
    @FXML
    private JFXButton RMTClinicCP;
    
    @FXML
    private JFXButton seminaCP;
    
    @FXML
    private JFXButton vacationCP;
    
    @FXML
    private JFXButton privateScheCP;    

    //레이블 체크박스
    @FXML
    private JFXCheckBox VSTClinicCheckBox;
 
    @FXML
    private JFXCheckBox RMTClinicCheckBox;

    @FXML
    private JFXCheckBox seminaCheckBox;

    @FXML
    private JFXCheckBox vacationCheckBox;

    @FXML
    private JFXCheckBox privateScheCheckBox;

    @FXML
    private JFXCheckBox selectAllCheckBox;

    @FXML
    private JFXButton btnDelAllSche;

    @FXML
    private JFXButton findScheButton;
    

    @FXML
    void delAllScheBtn(MouseEvent event) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("모든 일정 삭제");
        alert.setContentText("달력에서 모든 일정을 삭제 하시겠습니까?");
        
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == buttonTypeYes){
            deleteAllScheduleInCalendar();
        }
    }

    @FXML
    void excelBtn(MouseEvent event) throws RemoteException {
    	FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());

        if(file != null){
            createExcelSheet(file);
        }
    }
    
	public void createExcelSheet(File file) throws RemoteException {
		// 의사번호
		int doctor_num = Integer.parseInt(login_Id);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(doctor_num + "님의 일정표");

		XSSFRow row = sheet.createRow(1);
		XSSFCell cell;

		cell = row.createCell(1);
		cell.setCellValue("일정 명");
		cell = row.createCell(2);
		cell.setCellValue("일정 종류");
		cell = row.createCell(3);
		cell.setCellValue("일정 날짜");
		cell = row.createCell(4);
		cell.setCellValue("일정 내용");

		ArrayList<ScheduleVO> scheduleList = new ArrayList<ScheduleVO>();
		scheduleList = scheduleService.getAllSchedule(doctor_num);
		
		int counter = 2;
		if (!scheduleList.isEmpty()) {
			for (int i = 0; i < scheduleList.size(); i++) {
				row = sheet.createRow(counter);
				cell = row.createCell(1);
				cell.setCellValue(scheduleList.get(i).getSche_name());
				cell = row.createCell(2);
				cell.setCellValue(scheduleList.get(i).getSche_kind());
				cell = row.createCell(3);
				cell.setCellValue(scheduleList.get(i).getSche_startdate());
				cell = row.createCell(4);
				cell.setCellValue(scheduleList.get(i).getSche_cont());
				counter++;
			}
//			for (int i = 0; i < 4; i++) {
//				sheet.autoSizeColumn(i);
//			}
		}
		
		try {
			FileOutputStream out = new FileOutputStream(file);
			wb.write(out);
			wb.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//PDF 파일 생성
    @FXML
    void pdfBtn(MouseEvent event) throws RemoteException {
    	int doctor_num = Integer.parseInt(login_Id);
    	
		TableView<ScheduleVO> table = new TableView<ScheduleVO>();

		double w = 500.00;
		// set width of table view
		table.setPrefWidth(w);
		
		// set resize policy
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		// intialize columns
		TableColumn<ScheduleVO, String> sche_name = new TableColumn<ScheduleVO, String>("일정 명");
		TableColumn<ScheduleVO, String> sche_kind = new TableColumn<ScheduleVO, String>("일정 종류");
		TableColumn<ScheduleVO, String> sche_startdate = new TableColumn<ScheduleVO, String>("일정 날짜");
		TableColumn<ScheduleVO, String> sche_cont = new TableColumn<ScheduleVO, String>("일정 내용");
		
		// set width of columns
		sche_name.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 50% width
		sche_kind.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 50% width
		sche_startdate.setMaxWidth(1f * Integer.MAX_VALUE * 20);
		sche_cont.setMaxWidth(1f * Integer.MAX_VALUE * 40); // 50% width
		
		sche_name.setCellValueFactory(new PropertyValueFactory<ScheduleVO, String>("sche_name"));
		sche_kind.setCellValueFactory(new PropertyValueFactory<ScheduleVO, String>("sche_kind"));
		sche_startdate.setCellValueFactory(new PropertyValueFactory<ScheduleVO, String>("sche_startdate"));
		sche_cont.setCellValueFactory(new PropertyValueFactory<ScheduleVO, String>("sche_cont"));
		
		// Add columns to the table
		table.getColumns().add(sche_name);
		table.getColumns().add(sche_kind);
		table.getColumns().add(sche_startdate);
		table.getColumns().add(sche_cont);
		//------------------------------------------------------------------
		//tableview set
		ArrayList<ScheduleVO> scheduleList = new ArrayList<ScheduleVO>();
		scheduleList = scheduleService.getAllSchedule(doctor_num);
		
		ObservableList<ScheduleVO> data = FXCollections.observableArrayList(scheduleList);
		table.getItems().setAll(data);
       
		// open dialog window and export table as pdf
		PrinterJob job = PrinterJob.createPrinterJob();
		if (job != null) {
			job.printPage(table);
			job.endJob();
		}
    }//END pdfBtn(MouseEvent event)

    @FXML
    void handleCheckBoxAction(ActionEvent event) {

    }    
    
    //일정 추가
    @FXML
    void newScheBtn(MouseEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AddSchedule.fxml"));		 
			Parent root = loader.load();
			
			AddScheduleController addScheduleController = loader.getController();
			addScheduleController.setPrimaryStage(primaryStage);
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    
    //일정 관리
    @FXML
    void scheManageBtn(MouseEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ListSchedule.fxml"));		 
			Parent root = loader.load();
			
			ListScheduleController listScheduleController = loader.getController();
			listScheduleController.setPrimaryStage(primaryStage);
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }//END scheManageBtn(MouseEvent event)

    
    //체크박스 모두 선택 또는 해제
    @FXML
    void selectAllCheckBoxes(ActionEvent event) {
    	if (selectAllCheckBox.isSelected()) {
          selectCheckBoxes();
    	} else {
          unSelectCheckBoxes();
    	}
    }
    
    //월 선택 시 이벤트
    @FXML
    void clickMonth(MouseEvent event) throws RemoteException, ParseException {
    	selectCheckBoxes();
    	
		String newValue = monthSelect.getSelectionModel().getSelectedItem();
		calendarNameLbl.setText(newValue);
		
		int year = Integer.parseInt(selectedYear.getSelectionModel().getSelectedItem());
		int month = Integer.parseInt(calendarNameLbl.getText()) - 1;
		currentMonth =  year + "/" + (month + 1);
		
		try {
			initializeCalendarGrid(year, month);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }//END clickMonth(MouseEvent event)
    
    
    //해당 일정찾기
    @FXML
    void findSche(MouseEvent event) throws RemoteException, NumberFormatException, ParseException {
    	if (!checkBoxesHaveBeenClicked) {
            checkBoxesHaveBeenClicked = true;
        }
        List<String> list = new ArrayList<String>();
		
        // 방문 진료
		if (VSTClinicCheckBox.isSelected()) {
			list.add("방문 진료");
		}
		
        //원격 진료
		if (RMTClinicCheckBox.isSelected()) {
			list.add("원격 진료");
		} 
        
        //세미나
        if (seminaCheckBox.isSelected()) {
			list.add("세미나");
        }
        
        // 휴가
        if (vacationCheckBox.isSelected()) {
			list.add("휴가");
        }
        
        // 개인 일정
        if (privateScheCheckBox.isSelected()) {
			list.add("개인 일정");
        }
       
        int year = Integer.parseInt(selectedYear.getSelectionModel().getSelectedItem());
        int month = Integer.parseInt(calendarNameLbl.getText());
		
        List<String> datelist = new ArrayList<String>();
        Map<String, List<String>> sche_map = new HashMap<String, List<String>>();
        sche_map.put("sche_kind", list);
        sche_map.put("sche_startdate", datelist);
		//-----------------------------------------------
		int rows = 6;
		int cols = 7;
		int day = 1;

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		enableCheckBoxes();
        
        int day_weak = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int end_day = cal.getActualMaximum(Calendar.DATE);
        int count = 1;
        
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				VBox vPane = new VBox();
				vPane.getStyleClass().add("calendar_pane");
				vPane.setMinWidth(weekdayHeader.getPrefWidth() / 7);

				if (count > day_weak) {
					if (day <= end_day) {
						String sche_startdate = "";
				        datelist.clear();
						sche_startdate = year + "/" + (month < 10 ? "0" + month : month) + "/" + (day < 10 ? "0" + day : day);
						datelist.add(sche_startdate);
						List<ScheduleVO> list2 = scheduleService.getSelectedSchedule(sche_map);
						
						vPane.getChildren().add(new Label(day + ""));
						if(list2 != null) {
							for (int k = 0; k < list2.size(); k++) {
								Label label = new Label();
								String color = list2.get(k).getSche_color();
								label.setText(list2.get(k).getSche_name());
								label.setStyle("-fx-background-color : #" + color);
								vPane.getChildren().add(label);
							}
						}
						day++;
					}
				}				
				vPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
					addSchedule(vPane);
				});

				GridPane.setVgrow(vPane, Priority.ALWAYS);
				calendarGrid.add(vPane, j, i);

				count++;
			}
		}
        for (int i = 0; i < 7; i++) {
			RowConstraints row = new RowConstraints();
			row.setMinHeight(scrollPane.getHeight()/7);
			calendarGrid.getRowConstraints().add(row);
        }
    }
    
    
	@FXML
	void initialize() throws RemoteException, ParseException {
		assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert calendarNameLbl != null : "fx:id=\"calendarNameLbl\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert showCalendar != null : "fx:id=\"showCalendar\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert monthLabel != null : "fx:id=\"monthLabel\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert selectedYear != null : "fx:id=\"selectedYear\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert monthSelect != null : "fx:id=\"monthSelect\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert centerArea != null : "fx:id=\"centerArea\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert weekdayHeader != null : "fx:id=\"weekdayHeader\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert calendarGrid != null : "fx:id=\"calendarGrid\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert colorRootPane != null : "fx:id=\"colorRootPane\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert lblVST != null : "fx:id=\"lblVST\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert lblRMT != null : "fx:id=\"lblRMT\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert lblSemina != null : "fx:id=\"lblSemina\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert lblVacation != null : "fx:id=\"lblVacation\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert lblPrivateSche != null : "fx:id=\"lblPrivateSche\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert VSTClinicCP != null : "fx:id=\"VSTClinicCP\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert RMTClinicCP != null : "fx:id=\"RMTClinicCP\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert seminaCP != null : "fx:id=\"seminaCP\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert vacationCP != null : "fx:id=\"vacationCP\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert privateScheCP != null : "fx:id=\"privateScheCP\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert VSTClinicCheckBox != null : "fx:id=\"VSTClinicCheckBox\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert RMTClinicCheckBox != null : "fx:id=\"RMTClinicCheckBox\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert seminaCheckBox != null : "fx:id=\"seminaCheckBox\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert vacationCheckBox != null : "fx:id=\"vacationCheckBox\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert privateScheCheckBox != null : "fx:id=\"privateScheCheckBox\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert selectAllCheckBox != null : "fx:id=\"selectAllCheckBox\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert btnDelAllSche != null : "fx:id=\"btnDelAllSche\" was not injected: check your FXML file 'Schedule.fxml'.";
		assert findScheButton != null : "fx:id=\"findScheButton\" was not injected: check your FXML file 'Schedule.fxml'.";

		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
			scheduleService = (IScheduleService) reg.lookup("schedule");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		ObservableList<String> data = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12");
		monthSelect.setItems(data);
		
		initializeCalendarWeekdayHeader();
		calendarGenerate();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		int year = cal.get(cal.YEAR);
		int month = cal.get(cal.MONTH);
		
		currentMonth = year + "/" + (month + 1);
		
		initializeCalendarGrid(year, month);
		
    }//END initialize()
    
	
	//체크박스 사용유무 관리
	public void disableCheckBoxes() {
		VSTClinicCheckBox.setDisable(true);
		RMTClinicCheckBox.setDisable(true);
		seminaCheckBox.setDisable(true);
		vacationCheckBox.setDisable(true);
		privateScheCheckBox.setDisable(true);
		selectAllCheckBox.setDisable(true);
	}

	public void enableCheckBoxes() {
		VSTClinicCheckBox.setDisable(false);
		RMTClinicCheckBox.setDisable(false);
		seminaCheckBox.setDisable(false);
		vacationCheckBox.setDisable(false);
		privateScheCheckBox.setDisable(false);
		selectAllCheckBox.setDisable(false);

		selectAllCheckBox.setSelected(true);
	}

	public void selectCheckBoxes() {
		VSTClinicCheckBox.setSelected(true);
		RMTClinicCheckBox.setSelected(true);
		seminaCheckBox.setSelected(true);
		vacationCheckBox.setSelected(true);
		privateScheCheckBox.setSelected(true);
	}

	public void unSelectCheckBoxes() {
		VSTClinicCheckBox.setSelected(false);
		RMTClinicCheckBox.setSelected(false);
		seminaCheckBox.setSelected(false);
		vacationCheckBox.setSelected(false);
		privateScheCheckBox.setSelected(false);
	}
	
    
    //달력
	public void initializeCalendarGrid(int year, int month) throws RemoteException, ParseException {
		int rows = 6;
		int cols = 7;
		int day = 1;

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);
		
		enableCheckBoxes();
        
        int day_weak = cal.get(Calendar.DAY_OF_WEEK) - 1;
        
        int end_day = cal.getActualMaximum(Calendar.DATE);
        int count = 1;
        
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				VBox vPane = new VBox();
				vPane.getStyleClass().add("calendar_pane");
				vPane.setMinWidth(weekdayHeader.getPrefWidth() / 7);

				if (count > day_weak) {
					if (day <= end_day) {
						currentDate = year + "/" + (month + 1) + "/" + day;
						Date date = new SimpleDateFormat("yyyy/MM/dd").parse(currentDate);
						String dateString = new SimpleDateFormat("yyyy/MM/dd").format(date);
						
						ScheduleVO scheduleVo = new ScheduleVO();
						scheduleVo.setSche_startdate(dateString);
						
						scheduleVo.setDoctor_num(Integer.parseInt(login_Id));
						
						List<ScheduleVO> list = scheduleService.getScheduleFromDate(scheduleVo);
						vPane.getChildren().add(new Label(day + ""));
						if(list != null) {
							for (int k = 0; k < list.size(); k++) {
								Label label = new Label();
								String color = list.get(k).getSche_color();	
								label.setText(list.get(k).getSche_name());
								label.setStyle("-fx-background-color : #" + color);
								vPane.getChildren().add(label);
							}
							day++;
						}						
					}
				}
				
				vPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
					addSchedule(vPane);
				});

				GridPane.setVgrow(vPane, Priority.ALWAYS);
				calendarGrid.add(vPane, j, i);

				count++;
			}
		}
        
        for (int i = 0; i < 7; i++) {
			RowConstraints row = new RowConstraints();
			row.setMinHeight(scrollPane.getHeight()/7);
			calendarGrid.getRowConstraints().add(row);
        }
    }
	
    
    //달력에 일정 추가
    private void addSchedule(VBox day) {
        if(!day.getChildren().isEmpty()) {
            Label lbl = (Label) day.getChildren().get(0);
            
            //일정추가 보여주기
            try {
               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("../view/AddSchedule.fxml"));
               AnchorPane rootLayout = (AnchorPane) loader.load();
               Stage stage = new Stage(StageStyle.UNDECORATED);
               stage.initModality(Modality.APPLICATION_MODAL);

               AddScheduleController scheduleController = loader.getController();
               scheduleController.setMainController(this);
               
               Scene scene = new Scene(rootLayout);
               stage.setScene(scene);
               stage.show();
           } catch (IOException ex) {
               Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }
    
    //달력 요일 추가
    public void initializeCalendarWeekdayHeader(){
        int weekdays = 7;
        String[] weekAbbr = {"Sun","Mon","Tue", "Wed", "Thu", "Fri", "Sat"};
        
        for (int i = 0; i < weekdays; i++){
            StackPane pane = new StackPane();
            pane.getStyleClass().add("weekday-header");
            
            // Make panes take up equal space
            HBox.setHgrow(pane, Priority.ALWAYS);
            pane.setMaxWidth(Double.MAX_VALUE);
            pane.setMinWidth(weekdayHeader.getPrefWidth()/7);
            
            // Add it to the header
            weekdayHeader.getChildren().add(pane);
            
            // Add weekday name
            pane.getChildren().add(new Label(weekAbbr[i]));
        }
    }
    
    
    // 현재 달력에서 모든 일정 삭제
    public void deleteAllScheduleInCalendar() throws RemoteException {
    	int doctor_num = Integer.parseInt(LoginId.login_Id);
    	int result = scheduleService.deleteAllSchedule(doctor_num);
    	
    	if(result >= 1) {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("모든 일정이 삭제 되었습니다.");
            alertMessage.showAndWait();
        } else {
            //Show message indicating that the calendar could not be deleted
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("일정 삭제에 실패하였습니다.");
            alertMessage.showAndWait();
        }
    }
    
   
	public void calendarGenerate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		int year = cal.get(cal.YEAR);
		int month = cal.get(cal.MONTH);

		// Load year selection
		selectedYear.getItems().add(year + "");
		selectedYear.getItems().add((year + 1) + "");

		// Select the first YEAR as default
		selectedYear.getSelectionModel().selectFirst();

		// Enable year selection box
		selectedYear.setVisible(true);

		// Set calendar name label
		calendarNameLbl.setText((month + 1) + "");

	}
   

    
    
}
