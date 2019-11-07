package clinic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import javafx.stage.Stage;

public class C_DoctorChatController {
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
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
	private JFXButton btnServerStart;

	@FXML
	private Label lblServer;
	
	@FXML
    private JFXTextField tfName;
	
	@FXML
	private JFXButton conServerBtn;

	// 접속한 소켓을 저장할 list --> (접속자리스트)
	private List<Socket> socketList = Collections.synchronizedList(new ArrayList<Socket>());
	ServerSocket mainServerSocket = null;
	private Socket clientSocket= null;
	
	////////////////////////////////////////////////////////////////////////////////
	public void msgSend(){// 메세지 보내기 기능
    	try {
        	if(clientSocket==null){
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
	void btnServerStartOnClick(ActionEvent event) {
		if (lblServer.getText().equals("No Server")) {
			try {
				mainServerSocket = null;
				taOutputMsg.setText("ip : " + InetAddress.getLocalHost() + "\n");

				// 방법1
				// mainServerSocket = new ServerSocket(port번호);

				// 방법2
				mainServerSocket = new ServerSocket();
				mainServerSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 4040));

				taOutputMsg.appendText("서버가 열렸습니다.\n\n");
				lblServer.setText("Server Ready");
				btnServerStart.setText("서버 종료");
				ConnectThread connectThread = new ConnectThread(mainServerSocket);
				connectThread.setDaemon(true);
				connectThread.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (lblServer.getText().equals("Server Ready")) {
			try {
				taOutputMsg.appendText("\n서버가 닫혔습니다.\n\n");
				lblServer.setText("No Server");
				btnServerStart.setText("서버 생성");
				// to send 'SERVER CLOSE' message to Client
				String sendMessage = "FFFF";
				byte[] byteArray = sendMessage.getBytes("UTF-8");
				for (int i = 0; i < socketList.size(); i++) {
					OutputStream outputStream = socketList.get(i).getOutputStream();
					outputStream.write(byteArray);
				}
				socketList.clear();
				mainServerSocket.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@FXML
    void conServerBtn(ActionEvent event) {
		if (conServerBtn.getText().equals("서버 연결")) {
    		try {
    			if(tfName.getText().isEmpty()){
    				taOutputMsg.setText("이름을 입력하세요");
    				tfName.requestFocus();
    				return;
    			}
    			////////////////////////////////////////////////////////////정보 비어있는지 확인 체크
    			
    			// 방법1
    			clientSocket = new Socket(InetAddress.getLocalHost(), 4040);
    			
    			/*
    			// 방법2
    			clientSocket = new Socket();
					//clientSocket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 4040));
    			clientSocket.connect(new InetSocketAddress(tfIp.getText(), Integer.parseInt( tfPort.getText())) );
    			*/
    			
    			
    			taOutputMsg.setDisable(false);
    			tfMsg.setDisable(false);
    			btnSend.setDisable(false);
    			
				taOutputMsg.setText("서버에 접속하였습니다.\n\n");
				conServerBtn.setText("접속 종료");
			
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
		 } else if (conServerBtn.getText().equals("접속 종료")) { //접속종료 하면 컨트롤러들 제한
			 try {
				 taOutputMsg.setDisable(true);
				 tfMsg.setDisable(true);
				 btnSend.setDisable(true);
				 
				 
				 taOutputMsg.setText("\n서버에 접속해제하였습니다.\n\n");
				 conServerBtn.setText("서버 연결");
				 clientSocket.close();
		     } catch (Exception e) {
		    	 if(!clientSocket.isClosed())
		    		 e.printStackTrace();
		     }
		 }
    }
	@FXML
	void initialize() {
		assert tfMsg != null : "fx:id=\"tfMsg\" was not injected: check your FXML file 'DoctorChat.fxml'.";
		assert taOutputMsg != null : "fx:id=\"taOutputMsg\" was not injected: check your FXML file 'DoctorChat.fxml'.";
		assert btnSend != null : "fx:id=\"btnSend\" was not injected: check your FXML file 'DoctorChat.fxml'.";
		assert ImgView != null : "fx:id=\"ImgView\" was not injected: check your FXML file 'DoctorChat.fxml'.";
		assert guestListArea != null : "fx:id=\"guestListArea\" was not injected: check your FXML file 'DoctorChat.fxml'.";
		assert btnServerStart != null : "fx:id=\"btnServerStart\" was not injected: check your FXML file 'DoctorChat.fxml'.";
		assert lblServer != null : "fx:id=\"lblServer\" was not injected: check your FXML file 'DoctorChat.fxml'.";
		assert conServerBtn != null : "fx:id=\"conServerBtn\" was not injected: check your FXML file 'DoctorChat.fxml'.";
		assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'DoctorChat.fxml'.";

		tfMsg.setOnKeyPressed(e -> tfMsgSend(e));
	}
	
	//------------------------------------------------------------------------------
    // Connect class
    class ConnectThread extends Thread {
    	ServerSocket mainServerSocket = null;
 
        ConnectThread(ServerSocket mainServerSocket) {
            this.mainServerSocket = mainServerSocket;
        }
 
        @Override
        public void run() {
            try {
                while (true) {
                    Socket serverSocket = mainServerSocket.accept();
                    
                    //outputField.setText("사용자 접속!");
 
                    socketList.add(serverSocket);
                    /*Platform.runLater(() -> {
                        lblCount.setText(socketList.size() + " 명");
                    });*/
 					
                    InputStream inputStream = serverSocket.getInputStream();
                    byte[] byteArray = new byte[256];
                    int size = inputStream.read(byteArray);
 
                    //System.out.println("------------>>>> size : " + size);
                    
                    if (size == -1) { // 
                        for (int i = 0; i < socketList.size();) {
                            if (serverSocket == socketList.get(i)) {
                            	socketList.remove(i);
                            } else {
                                i++;
                            }
                            /*Platform.runLater(() -> {
                                lblCount.setText(socketList.size() + " 명");
                            });*/
                        }
                        break;
                    }
                    String readMessage = new String(byteArray, 0, size, "UTF-8");
                    Platform.runLater(() -> {
                    	guestListArea.appendText(readMessage + "님 접속\n");
                    });
                    
                    //--
                    
                    String sendMessage = readMessage +"님이 접속하셨습니다.";
                    byteArray = sendMessage.getBytes("UTF-8");
                    for (int i = 0; i < socketList.size(); i++) {
                    	if (serverSocket != socketList.get(i)){
	                        OutputStream outputStream = socketList.get(i).getOutputStream();
	                        outputStream.write(byteArray);
	                        outputStream.flush();
                    	}
                    }
                    //--
                    
                    ServerReader serverReader = new ServerReader(serverSocket);
                    serverReader.start();
                }
            } catch (Exception e) {
            	//System.out.println("==> " + mainServerSocket.isClosed());
            	if(!mainServerSocket.isClosed())
            		e.printStackTrace();
            }
        }
    }
    //-------------------------------------------------------------------------------
    // Reader class
    class ServerReader extends Thread {
        Socket serverSocket = null;
 
        ServerReader(Socket serverSocket) {
            this.serverSocket = serverSocket;
        }
 
        @Override
        public void run() {
            try {
                while (true) {
                    InputStream inputStream = serverSocket.getInputStream();
                    byte[] byteArray = new byte[256];
                    int size = inputStream.read(byteArray);
                    //System.out.println("<<<<<============>>>> size = " + size);
                    if (size == -1) { // if(Logout)
                        for (int i = 0; i < socketList.size();) {
                            if (serverSocket == socketList.get(i)) {
                            	socketList.remove(i);
                            } else {
                                i++;
                            }
                            /*Platform.runLater(() -> {
                                lblCount.setText(socketList.size() + " 명");
                            });*/
                        }
                        break;
                    }
 
                    String readMessage = new String(byteArray, 0, size, "UTF-8");
                    byteArray = readMessage.getBytes("UTF-8");
                    
                    //--------------------------
                    for (int i = 0; i < socketList.size(); i++) {
							OutputStream outputStream = socketList.get(i).getOutputStream();
							outputStream.write(byteArray);
                    }
                    //---------------------------
                    
                }
            } catch (Exception e) {
                for (int i = 0; i < socketList.size();) {
                    if (serverSocket == socketList.get(i)) {
                    	socketList.remove(i);
                    } else {
                        i++;
                    }
                    
                }
            }
        }
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


