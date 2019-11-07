package admin;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import board.notice.NoticeMainController;
import board.qna.QnAMainController;
import controller.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.scene.layout.HBox;

public class AdminMainController {
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblName;

    @FXML
    private Label lblMember;

    @FXML
    private ImageView imgExit;

    @FXML
    private Tab memTab;

    @FXML
    private HBox memBox;

    @FXML
    private Tab dbTab;

    @FXML
    private BorderPane dbPane;

    @FXML
    private Label docMsg;

    @FXML
    private Label clinic;

    @FXML
    private Label medicine;

    @FXML
    private Tab boardTab;
    
    @FXML
    private BorderPane BorderPaneBoard;

    
    //private IAdminMainService service;

    @FXML
    void clickExit(MouseEvent event) {
    	
    }

    @FXML
    void viewClinic(MouseEvent event) {

    }

    @FXML
    void viewDocMsg(MouseEvent event) {

    }

    @FXML
    void viewMedicine(MouseEvent event) {

    }
    
    @FXML
    void clickNotice(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NoticeMain.fxml"));
		Parent root = loader.load();
		NoticeMainController noticecontroller = loader.getController();
		noticecontroller.setPrimaryStage(primaryStage, BorderPaneBoard);
		BorderPaneBoard.setCenter(root);
    }

    @FXML
    void clickQandA(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/QnAMain.fxml"));
		Parent root = loader.load();
		QnAMainController qnacontroller = loader.getController();
		qnacontroller.setPrimaryStage(primaryStage, BorderPaneBoard);
		BorderPaneBoard.setCenter(root);
    }

    @FXML
    void initialize() throws IOException {
        assert lblName != null : "fx:id=\"lblName\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert lblMember != null : "fx:id=\"lblMember\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert imgExit != null : "fx:id=\"imgExit\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert memTab != null : "fx:id=\"memTab\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert memBox != null : "fx:id=\"memBox\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert dbTab != null : "fx:id=\"dbTab\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert dbPane != null : "fx:id=\"dbPane\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert docMsg != null : "fx:id=\"docMsg\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert clinic != null : "fx:id=\"clinic\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert medicine != null : "fx:id=\"medicine\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert boardTab != null : "fx:id=\"boardTab\" was not injected: check your FXML file 'AdminMain.fxml'.";
        
        //------------------------------------------------------------------------------------------------------
        try {
 			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
 			//service = (IAdminMainService) reg.lookup("adminMain");
 		} catch (RemoteException e) {
 			e.printStackTrace();
 		}
// 		} catch (NotBoundException e) {
// 			e.printStackTrace();
// 		}
        //------------------------------------------------------------------------------------------------------
        
        try {
			Parent loader = FXMLLoader.load(getClass().getResource("../view/MemList.fxml"));
			memBox.getChildren().add(loader);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        Parent loader2 = FXMLLoader.load(getClass().getResource("../view/AdminDB.fxml"));
        dbPane.setCenter(loader2);
        
        
		
        ///임현이 만진 부분
        imgExit.setOnMouseClicked(e->{
        	Stage newPrimaryStage = new Stage();
        	Main main = new Main();
        	main.start(newPrimaryStage);
    		
    		Stage stage = (Stage) imgExit.getScene().getWindow();
    		stage.close();
        
        });

    }

	public void setlblName(String admin_id) {
		this.lblName.setText(admin_id);
		
	}
}
