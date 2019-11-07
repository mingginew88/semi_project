package msg;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import VO.DoctorVO;
import VO.MessageVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.GetAlert;
import login.LoginId;

public class MsgWriteController {
	
	private MessageVO msgVo;
	
	public void setMsgVO(MessageVO msgVo) {
		this.msgVo=msgVo;
	}
	
	@FXML
	private Label msgLabel;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXComboBox<DoctorVO> cboxRc;

    @FXML
    private JFXTextArea msgCont;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXButton btnCancel;

    @FXML
    void MsgCancel(MouseEvent event) {
    	Stage stage = (Stage) btnCancel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void MsgSend(MouseEvent event) {
    	if(btnSend.getText().equals("답장하기")) {
    		msgCont.clear();
    		msgLabel.setText("받는 사람");
    		btnSend.setText("보내기");
    		return;
    	}
    	MessageVO msg = new MessageVO();
    	msg.setMsg_cont(msgCont.getText());
    	int doctor_num = cboxRc.getSelectionModel().getSelectedItem().getDoctor_num();
    	if(doctor_num==Integer.parseInt(LoginId.login_Id)) {
    		msg.setMsg_rc(Integer.parseInt(LoginId.login_Id));
    		msg.setMsg_sd(doctor_num);
    		
    	}else {
    		msg.setMsg_rc(doctor_num);
    		msg.setMsg_sd(Integer.parseInt(LoginId.login_Id));
    	}
    	try {
			if(service.insertMsg(msg)>0) {
				new GetAlert().info("complete", "메시지가 전송되었습니다.");
				Stage stage = (Stage) btnCancel.getScene().getWindow();
		    	stage.close();
			}else {
				System.out.println("저장안돼..");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
    
    private IMsgService service;
    
    public void setMsg(DoctorVO doctor, MessageVO msg) { //doctor>>받는사람
    	DoctorVO rc = null;
    	try {
			rc = service.searchDoctor(msg.getMsg_rc());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	msgCont.setText(msg.getMsg_cont());
    	if(msg.getMsg_sd()==Integer.parseInt(LoginId.login_Id)) {
    		btnSend.setText("재전송");
    		cboxRc.getItems().add(rc);
    		cboxRc.setValue(rc);
    	}else {
    		cboxRc.getItems().add(doctor);
    		cboxRc.setValue(doctor);
    		msgLabel.setText("보낸 사람");
    		btnSend.setText("답장하기");
    	}
    }

    @FXML
    void initialize() {
        assert cboxRc != null : "fx:id=\"cboxRc\" was not injected: check your FXML file 'MsgWrite.fxml'.";
        assert msgCont != null : "fx:id=\"msgCont\" was not injected: check your FXML file 'MsgWrite.fxml'.";
        assert btnSend != null : "fx:id=\"btnSend\" was not injected: check your FXML file 'MsgWrite.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'MsgWrite.fxml'.";
        
        try {
			Registry reg = LocateRegistry.getRegistry("192.168.207.218", 9988);
			service = (IMsgService) reg.lookup("msg");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        List<DoctorVO> doctorList = null;
		try {
			doctorList = service.getAllDoctor();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        ObservableList<DoctorVO> data = FXCollections.observableArrayList(doctorList);
        cboxRc.setItems(data);
        cboxRc.setCellFactory(new Callback<ListView<DoctorVO>, ListCell<DoctorVO>>() {
			
			@Override
			public ListCell<DoctorVO> call(ListView<DoctorVO> param) {
				return new ListCell<DoctorVO>() {
					@Override
					protected void updateItem(DoctorVO item, boolean empty) {
						super.updateItem(item, empty);
						if(item!=null&&!empty) {
							setText(item.getDoctor_name()+" ("+item.getDoctor_num()+")");
						}else {
							setText(null);
						}
					}
				};
			}
		});
        cboxRc.setButtonCell(new ListCell<DoctorVO>() {
        	@Override
        	protected void updateItem(DoctorVO item, boolean empty) {
        		super.updateItem(item, empty);
        		if(item!=null&&!empty) {
        			setText(item.getDoctor_name()+" ("+item.getDoctor_num()+")");
        		}else {
        			setText(null);
        		}
        	}
        });
    }
}
