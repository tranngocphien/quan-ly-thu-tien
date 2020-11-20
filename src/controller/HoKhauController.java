package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

public class HoKhauController {
	
	public void addHoKhau(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views.hokhau/AddHoKhau.fxml"));
		
		
	}

}
