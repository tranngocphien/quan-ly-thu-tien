package controller.khoanthu;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.KhoanThuModel;

public class UpdateKhoanThu {
	@FXML
	private TextField tfMaKhoanThu;
	@FXML
	private TextField tfTenKhoanThu;
	@FXML
	private TextField tfLoaiKhoanThu;
	@FXML
	private TextField tfSoTien;
	
	private KhoanThuModel khoanThuModel;
	
	public void setKhoanThuModel(KhoanThuModel khoanThuModel) {
		this.khoanThuModel = khoanThuModel;
		
		tfTenKhoanThu.setText(khoanThuModel.getTenKhoanThu());
	}
}
