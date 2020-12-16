package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class HomeController implements Initializable {
	@FXML
	private BorderPane borderPane;
	
	public void setNhanKhau(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/NhanKhau.fxml"));
		Pane nhankhauPane = (Pane) loader.load();
		borderPane.setCenter(nhankhauPane);
	}

	public void setHoKhau(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/HoKhau.fxml"));
		Pane hokhauPane = (Pane) loader.load();
		borderPane.setCenter(hokhauPane);

	}

	public void setKhoanPhi(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/KhoanThu.fxml"));
		Pane khoanphiPane = (Pane) loader.load();
		borderPane.setCenter(khoanphiPane);
	}
	
	public void setDongPhi(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NopTien.fxml"));
		Pane dongphiPane = (Pane) loader.load();
		borderPane.setCenter(dongphiPane);
	}
	
	public void setThongKe(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/ThongKe.fxml"));
		Pane thongkePane = (Pane) loader.load();
		borderPane.setCenter(thongkePane);

	}
	
	public void setTrangChu(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/Main.fxml"));
		Pane trangchuPane = (Pane) loader.load();
		borderPane.setCenter(trangchuPane);

	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Pane login = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
			borderPane.setCenter(login);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
