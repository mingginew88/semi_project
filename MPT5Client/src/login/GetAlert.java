package login;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class GetAlert {
	public void alert(String head, String msg) {
    	Alert alert = new Alert(AlertType.WARNING);
    	DialogPane dialogPane = alert.getDialogPane();
    	dialogPane.getStylesheets().add(
    			getClass().getResource("GetAlert.css").toExternalForm());
    	alert.setTitle("경고");
    	alert.setHeaderText(head);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
    
    public void info(String head, String msg) {
    	Alert info = new Alert(AlertType.INFORMATION);
    	DialogPane dialogPane = info.getDialogPane();
    	dialogPane.getStylesheets().add(
    			getClass().getResource("GetAlert.css").toExternalForm());
    	info.setTitle("정보");
    	info.setHeaderText(head);
    	info.setContentText(msg);
    	info.showAndWait();
    }
    
    public boolean confirm(String head, String msg) {
    	Alert confirm = new Alert(AlertType.CONFIRMATION);
    	DialogPane dialogPane = confirm.getDialogPane();
    	dialogPane.getStylesheets().add(
    	   getClass().getResource("GetAlert.css").toExternalForm());
    	confirm.setTitle("선택");
    	confirm.setHeaderText(head);
    	confirm.setContentText(msg);
    	confirm.showAndWait();
    	ButtonType conResult = confirm.showAndWait().get();
		if (conResult == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
    }
}
