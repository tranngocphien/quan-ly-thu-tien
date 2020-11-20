package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPassword;
	
	public void Login(ActionEvent event) throws IOException {
		String name = tfUsername.getText();
		String pass = tfPassword.getText();
		Parent home = FXMLLoader.load(getClass().getResource("/views/Home3.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(home,800,600));
        stage.setResizable(false);
        stage.show();
	}

}
