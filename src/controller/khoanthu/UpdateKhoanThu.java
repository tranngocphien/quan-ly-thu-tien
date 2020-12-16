package controller.khoanthu;

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
import models.KhoanThuModel;
import services.KhoanThuService;

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
		tfMaKhoanThu.setText(Integer.toString(khoanThuModel.getMaKhoanThu()));
		if (khoanThuModel.getLoaiKhoanThu() == 1) {
			tfLoaiKhoanThu.setText("Bắt buộc");
		} else {
			tfLoaiKhoanThu.setText("Tự nguyện");
		}
		tfSoTien.setText(Double.toString(khoanThuModel.getSoTien()));
	}

	public void updateKhoanThu(ActionEvent event) throws ClassNotFoundException, SQLException {
		Pattern pattern;
		
		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (tfTenKhoanThu.getText().length() >= 50 || tfTenKhoanThu.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 tên khoản thu hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra soTien nhap vao
		// so tien nhap vao phai la so va nho hon 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfSoTien.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào số tiền hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		
		// ghi nhan cac gia tri sau khi da kiem tra hop le
		int maKhoanThuInt = khoanThuModel.getMaKhoanThu();
		String tenKhoanThuString = tfTenKhoanThu.getText();
		int loaiKhoanThuInt = khoanThuModel.getLoaiKhoanThu();
		double soTienDouble = Double.parseDouble(tfSoTien.getText());
		
		new KhoanThuService().update(maKhoanThuInt, tenKhoanThuString, soTienDouble, loaiKhoanThuInt);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
	}
}
