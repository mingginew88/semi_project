package patientClinic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class C_PatientChatController {
	
	private Stage primaryStage;
	private BorderPane borderPane;
	
	public void setPrimaryStage(Stage primaryStage, BorderPane borderPane) {
		this.primaryStage = primaryStage;
		this.borderPane = borderPane;
	}
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXTextField tfMsg;

	@FXML
	private JFXTextArea taOutputMsg;

	@FXML
	private JFXButton btnSend;

	@FXML
	private ImageView ImgView;

	@FXML
	private JFXTextArea guestListArea;

	@FXML
	private Label lblServer;

	@FXML
	private JFXButton conServerBtn;

	@FXML
	private JFXTextField tfName;
	
	@FXML
	private JFXTextField tfPort;

	@FXML
	private JFXTextField tfIp;

	private Socket clientSocket = null;

	////////////////////////////////////////////////////////////////////////////////
	public void msgSend() {// 메세지 보내기 기능
		try {
			if (clientSocket == null) {
				taOutputMsg.setText("서버에 접속되지 않았습니다.\n");
				return;
			}

			// 입력창에서 입력 후 'Enter'키를 누르면 메시지 전송
			String sendMessage = tfName.getText() + " : " + tfMsg.getText();
			byte[] byteArray = sendMessage.getBytes("UTF-8");
			OutputStream outputStream = clientSocket.getOutputStream();
			outputStream.write(byteArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tfMsg.clear();
	}

	////////////////////////////////////////////////////////////////////////////
	@FXML
	void btnSendOnClick(ActionEvent event) {
		msgSend();
	}

	private void tfMsgSend(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			msgSend();
		}
	}

	@FXML
	void conServerBtn(ActionEvent event) {
		if (lblServer.getText().equals("No Server")) {
    		try {
    			if(tfIp.getText().isEmpty()){
    				taOutputMsg.setText("접속할 서버의 IP주소를 입력하세요");
    				tfIp.requestFocus();
    				return;
    			}
				
    			if(tfPort.getText().isEmpty()){
    				taOutputMsg.setText("접속할 서버의 Port번호를 입력하세요");
    				tfPort.requestFocus();
    				return;
    			}
    			
    			if(tfName.getText().isEmpty()){
    				taOutputMsg.setText("이름을 입력하세요");
    				tfName.requestFocus();
    				return;
    			}
    			////////////////////////////////////////////////////////////정보 비어있는지 확인 체크
    			
    			// 방법1
    			clientSocket = new Socket(tfIp.getText(), Integer.parseInt(tfPort.getText()));
    			
    			/*
    			// 방법2
    			clientSocket = new Socket();
					//clientSocket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 4040));
    			clientSocket.connect(new InetSocketAddress(tfIp.getText(), Integer.parseInt( tfPort.getText())) );
    			*/
    			tfIp.setDisable(true);
    			tfPort.setDisable(true);
    			tfName.setDisable(true);
    			
    			taOutputMsg.setDisable(false);
    			tfMsg.setDisable(false);
    			btnSend.setDisable(false);
    			
				taOutputMsg.setText("서버에 접속하였습니다.\n\n");
				conServerBtn.setText("접속 종료");
				lblServer.setText("Server Ready");
				try {
					// 서버 접속후 '이름'을 서버로 전송
					String sendMessage = tfName.getText();
					//System.out.println("메시지 : " + sendMessage);
					byte[] byteArray = sendMessage.getBytes("UTF-8");
					OutputStream outputStream = clientSocket.getOutputStream();
					outputStream.write(byteArray);
					outputStream.flush();
						 
				 } catch (Exception e) {
						 e.printStackTrace();
				 }
					// ClientReader객체 생성
					ClientReader clientReader = new ClientReader(clientSocket);
					clientReader.setDaemon(true);
					clientReader.start();
				 } catch (Exception e) {
						 e.printStackTrace();
				 }
		 } else if (lblServer.getText().equals("Server Ready")) { //접속종료 하면 컨트롤러들 제한
			 try {
				 tfIp.setDisable(false);
				 tfPort.setDisable(false);
				 tfName.setDisable(false);
    			
				 taOutputMsg.setDisable(true);
				 tfMsg.setDisable(true);
				 btnSend.setDisable(true);
				 
				 
				 taOutputMsg.setText("\n서버에 접속해제하였습니다.\n\n");
				 conServerBtn.setText("서버 접속");
				 lblServer.setText("No Server");
				 clientSocket.close();
		     } catch (Exception e) {
		    	 if(!clientSocket.isClosed())
		    		 e.printStackTrace();
		     }
		 }
	}

	@FXML
	void initialize() {
		assert tfMsg != null : "fx:id=\"tfMsg\" was not injected: check your FXML file 'PatientChat.fxml'.";
		assert taOutputMsg != null : "fx:id=\"taOutputMsg\" was not injected: check your FXML file 'PatientChat.fxml'.";
		assert btnSend != null : "fx:id=\"btnSend\" was not injected: check your FXML file 'PatientChat.fxml'.";
		assert ImgView != null : "fx:id=\"ImgView\" was not injected: check your FXML file 'PatientChat.fxml'.";
		assert guestListArea != null : "fx:id=\"guestListArea\" was not injected: check your FXML file 'PatientChat.fxml'.";
		assert lblServer != null : "fx:id=\"lblServer\" was not injected: check your FXML file 'PatientChat.fxml'.";
		assert conServerBtn != null : "fx:id=\"conServerBtn\" was not injected: check your FXML file 'PatientChat.fxml'.";
		assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'PatientChat.fxml'.";
		assert tfPort != null : "fx:id=\"tfPort\" was not injected: check your FXML file 'PatientChat.fxml'.";
		assert tfIp != null : "fx:id=\"tfIp\" was not injected: check your FXML file 'PatientChat.fxml'.";
		
		tfMsg.setOnKeyPressed(e -> tfMsgSend(e));
		tfPort.setText(C_PatientClinicMainController.getPort());
		System.out.println(C_PatientClinicMainController.getPort());
		tfIp.setText(C_PatientClinicMainController.getIpAddr());
	}
	//-----------------------------------------------------------------------------
    // Reader class
    class ClientReader extends Thread {
    	Socket clientSocket = null;
        String readMessage = null;
 
        ClientReader(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
 
        @Override
        public void run() {
            try {
                while (true) {
                    InputStream inputStream = clientSocket.getInputStream();
                    byte[] byteArray = new byte[256];
                    int size = inputStream.read(byteArray);
                    if (size == -1){
                    	break;  // 강제종료일 경우
                    }
                    readMessage = new String(byteArray, 0, size, "UTF-8");
                    if(readMessage.equals("FFFF")) {
                        tfMsg.clear();
                        taOutputMsg.clear();
                        
                        readMessage = "서버가 종료되었습니다";    
                        Platform.runLater(() -> {
                        	tfIp.setDisable(false);
	           				tfPort.setDisable(false);
	           				tfName.setDisable(false);
	               			
	           				taOutputMsg.setDisable(true);
	           				tfMsg.setDisable(true);
	           				btnSend.setDisable(true);
	           				 
	           				conServerBtn.setText("서버 접속");
                        });
                    }
                    taOutputMsg.appendText(readMessage + "\n");

                }
            } catch (Exception e) {
            	//System.out.println("--> " + clientSocket.isClosed());
            	if(!clientSocket.isClosed())
					try {	
						clientSocket.close();  
						taOutputMsg.appendText("서버가 종료되었습니다\n");
					} catch (IOException e1) {	}
            	else
            		e.printStackTrace();
            }
        }
    }
    
}
