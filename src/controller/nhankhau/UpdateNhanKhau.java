package controller.nhankhau;

import java.sql.SQLException;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.NhanKhauModel;
import services.NhanKhauService;

public class UpdateNhanKhau {
	private int maNhanKhau;
	
	@FXML
	private TextField tfMaNhanKhau;
	@FXML
	private TextField tfTuoi;
	@FXML
	private TextField tfTenNhanKhau;
	@FXML
	private TextField tfSoDienThoai;
	@FXML
	private TextField tfSoCMND;

	private NhanKhauModel nhanKhauModel;

	public NhanKhauModel getNhanKhauModel() {
		return nhanKhauModel;
	}

	public void setNhanKhauModel(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
		this.nhanKhauModel = nhanKhauModel;

		maNhanKhau = nhanKhauModel.getId();
		tfMaNhanKhau.setText(Integer.toString(maNhanKhau));
		tfTuoi.setText(Integer.toString(nhanKhauModel.getTuoi()));
		tfTenNhanKhau.setText(nhanKhauModel.getTen());
		tfSoDienThoai.setText(nhanKhauModel.getSdt());
		tfSoCMND.setText(nhanKhauModel.getCmnd());

	}

	public void updateNhanKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
		// khai bao mot mau de so sanh
		Pattern pattern;

		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (tfTenNhanKhau.getText().length() >= 50 || tfTenNhanKhau.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 tên hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra tuoi nhap vao
		// tuoi nhap vao nhieu nhat la 1 so co 3 chu so
		pattern = Pattern.compile("\\d{1,3}");
		if (!pattern.matcher(tfTuoi.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào tuổi hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra cmnd nhap vao
		// cmnd nhap vao phai la mot day so tu 1 toi 20 so
		pattern = Pattern.compile("\\d{1,20}");
		if (!pattern.matcher(tfSoCMND.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào CMND hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra sdt nhap vao
		// SDT nhap vao phai khong chua chu cai va nho hon 15 chu so
		pattern = Pattern.compile("\\d{1,15}");
		if (!pattern.matcher(tfSoDienThoai.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào SĐT hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		
		// ghi nhan gia tri ghi tat ca deu da hop le
		String tenString = tfTenNhanKhau.getText();
		int tuoiInt = Integer.parseInt(tfTuoi.getText());
		String cmndString = tfSoCMND.getText();
		String sdtString = tfSoDienThoai.getText();
		
		// xoa di nhan khau hien tai va them vao nhan khau vua cap nhat
		new NhanKhauService().update(maNhanKhau, cmndString, tenString, tuoiInt, sdtString);
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
		
	}
}
