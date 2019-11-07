package msg;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;

import VO.DoctorVO;
import VO.MessageVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.LoginId;

public class MsgController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXListView<MessageVO> MsgListView;

    @FXML
    private ImageView btnSendMsg;

    private IMsgService service;

    @FXML
    private Label test;
    
    List<MessageVO> msgList = null;

    @FXML
    void msgClicked(MouseEvent event) {
    	if(event.getClickCount()>1&&MsgListView.getSelectionModel().getSelectedItem()!=null) {
    		try {
				Stage msgClicked = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MsgWrite.fxml"));
				MessageVO msg = MsgListView.getSelectionModel().getSelectedItem();
				Parent addRoot = loader.load();
				MsgWriteController msgWriteController = loader.getController();				
				msgWriteController.setMsgVO(MsgListView.getSelectionModel().getSelectedItem());
				DoctorVO doctor = (DoctorVO) service.searchDoctor(msg.getMsg_sd());
				msgWriteController.setMsg(doctor, msg);
				Scene scene = new Scene(addRoot);
				msgClicked.setScene(scene);
				msgClicked.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }

    @FXML
    void initialize() {
        assert MsgListView != null : "fx:id=\"MsgListView\" was not injected: check your FXML file 'MsgMain.fxml'.";
        assert btnSendMsg != null : "fx:id=\"btnSendMsg\" was not injected: check your FXML file 'MsgMain.fxml'.";
        assert test != null : "fx:id=\"test\" was not injected: check your FXML file 'MsgMain.fxml'.";
        
		try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
			service = (IMsgService) reg.lookup("msg");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		reMsg();
    }

    public void CustomListView(List<MessageVO> msgList) {
    	ObservableList<MessageVO> data = FXCollections.observableArrayList(msgList);
    	MsgListView.setItems(data);
    	MsgListView.setCellFactory(new Callback<ListView<MessageVO>, ListCell<MessageVO>>() {
    		
    		@Override
    		public ListCell<MessageVO> call(ListView<MessageVO> param) {
    			return new CustomListCell();
    		}
    	});
    }
    
    @FXML
    void receiveMsg(MouseEvent event) {
    	reMsg();
    }
    
    public void reMsg() {
    	List<MessageVO> msgList = null;
    	try {
    		msgList = service.getAllMsg(Integer.parseInt(LoginId.login_Id));
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    	} catch (RemoteException e) {
    		e.printStackTrace();
    	}
    	CustomListView(msgList);
    }
    
    @FXML
    void sendMsg(MouseEvent event) {
    	List<MessageVO> msgList = null;
		try {
			msgList = service.getAllReMsg(Integer.parseInt(LoginId.login_Id));
		} catch (NumberFormatException | RemoteException e) {
			e.printStackTrace();
		}
    	CustomListView(msgList);
    }
    
    @FXML
    void sendNewMsg(MouseEvent event) {
    	try {
    		Stage msgClicked = new Stage();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MsgWrite.fxml"));
    		Parent addRoot = loader.load();
    		MsgWriteController msgWriteController = loader.getController();				
    		Scene scene = new Scene(addRoot);
    		msgClicked.setScene(scene);
    		msgClicked.show();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}

    }
    
    private class CustomListCell extends ListCell<MessageVO>{
    	private HBox msg;
    	private Text msg_doc;
    	private Text msg_cont;
    	private Text msg_date;
    	
    	public CustomListCell() {
    		msg_doc = new Text();
    		msg_cont = new Text();
    		msg_date = new Text();
    		VBox vbox = new VBox(msg_doc, msg_cont);
    		msg = new HBox(new ImageView(new Image(getClass().getResourceAsStream("docmsg.png"))),vbox,msg_date);
    		msg.setSpacing(10);
    	}
    	
    	@Override
    	protected void updateItem(MessageVO item, boolean empty) {
    		super.updateItem(item, empty);
    		if(item!=null&&!empty) {
    			try {
    				if(item.getMsg_sd()==Integer.parseInt(LoginId.login_Id)) {
    					msg_doc.setText(service.searchDoctorName(item.getMsg_rc()));
    				}else {
    					msg_doc.setText(service.searchDoctorName(item.getMsg_sd()));
    					
    				}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
    			msg_cont.setText(item.getMsg_cont().length()>6?item.getMsg_cont().substring(0, 5)+"...":item.getMsg_cont());
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			msg_date.setText(sdf.format(item.getMsg_date()));
    			setGraphic(msg);
    		}else {
    			setGraphic(null);
    		}
    	}
    }
    
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}
