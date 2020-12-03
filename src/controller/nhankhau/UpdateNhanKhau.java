package controller.nhankhau;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import models.NhanKhauModel;

public class UpdateNhanKhau implements Initializable{
		@FXML
	    private TextField tfMaNhanKhau;
	    @FXML
	    private TextField tfMaHoKhau;
	    @FXML
	    private TextField tfTuoi;
	    @FXML
	    private TextField tfTenNhanKhau;
	    @FXML
	    private TextField tfSoDienThoai;
	    @FXML
	    private TextField tfSoCMND;
	    
	
	
	public void showUpdateNhanKhau() {
		//NhanKhauModel nhanKhauModel = tvNhanKhau.getSelectionModel().getSelectedItem();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showUpdateNhanKhau();
		
	}
	
}


