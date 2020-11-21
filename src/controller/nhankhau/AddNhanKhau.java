package controller.nhankhau;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.NhanKhauModel;
import models.QuanHeModel;
import services.NhanKhauService;
import services.QuanHeService;

public class AddNhanKhau {
	@FXML
	private TextField tfId;
	@FXML
	private TextField tfTen;
	@FXML
	private TextField tfTuoi;
	@FXML
	private TextField tfCmnd;
	@FXML
	private TextField tfSdt;
	@FXML
	private TextField tfMaHoKhau;
	
	public void addNhanKhau() throws ClassNotFoundException, SQLException {
		NhanKhauService nhanKhauService = new NhanKhauService();
		QuanHeService quanHeService = new QuanHeService();
		
		int idInt = Integer.parseInt(tfId.getText());
		String tenString = tfTen.getText();
		int tuoiInt = Integer.parseInt(tfTuoi.getText());
		String cmndString = tfCmnd.getText();
		String sdtString = tfSdt.getText();
		int mahokhauInt = Integer.parseInt(tfMaHoKhau.getText());
		
		NhanKhauModel nhanKhauModel = new NhanKhauModel(idInt, cmndString, tenString, tuoiInt, sdtString);
		QuanHeModel quanHeModel = new QuanHeModel(mahokhauInt, idInt, "con");
		
		nhanKhauService.add(nhanKhauModel);
		quanHeService.add(quanHeModel);
	}
}
