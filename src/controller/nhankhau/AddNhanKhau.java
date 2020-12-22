package controller.nhankhau;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
import models.HoKhauModel;
import models.NhanKhauModel;
import models.QuanHeModel;
import services.HoKhauService;
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
	@FXML
	private TextField tfQuanHe;

	public void addNhanKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
		// khai bao mot mau de so sanh
		Pattern pattern;

		// kiem tra id nhap vao
		// id la day so tu 1 toi 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfId.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã nhân khẩu hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem tra ID them moi co bi trung voi nhung ID da ton tai hay khong
		List<NhanKhauModel> listNhanKhauModels = new NhanKhauService().getListNhanKhau();
		for (NhanKhauModel nhankhau : listNhanKhauModels) {
			if (nhankhau.getId() == Integer.parseInt(tfId.getText())) {
				Alert alert = new Alert(AlertType.WARNING, "ID bị trùng với một người khác!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}

		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (tfTen.getText().length() >= 50 || tfTen.getText().length() <= 1) {
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
		if (!pattern.matcher(tfCmnd.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào CMND hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra sdt nhap vao
		// SDT nhap vao phai khong chua chu cai va nho hon 15 chu so
		pattern = Pattern.compile("\\d{1,15}");
		if (!pattern.matcher(tfSdt.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào SĐT hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra maHo nhap vao
		// ma ho nhap vao phai khong chua chu cai va nho hon 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfMaHoKhau.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã hộ hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra ma ho nhap vao da ton tai hay chua
		List<HoKhauModel> listHoKhauModels = new HoKhauService().getListHoKhau();
		long check = listHoKhauModels.stream()
				.filter(hokhau -> hokhau.getMaHo() == Integer.parseInt(tfMaHoKhau.getText())).count();
		if (check <= 0) {
			Alert alert = new Alert(AlertType.WARNING, "Không có hộ khẩu này!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// Kiem tra Quan he nhap vao
		if (tfQuanHe.getText().length() >= 30 || tfQuanHe.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 quan hệ hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// ghi nhan gia tri ghi tat ca deu da hop le
		int idInt = Integer.parseInt(tfId.getText());
		String tenString = tfTen.getText();
		int tuoiInt = Integer.parseInt(tfTuoi.getText());
		String cmndString = tfCmnd.getText();
		String sdtString = tfSdt.getText();
		int mahokhauInt = Integer.parseInt(tfMaHoKhau.getText());
		String quanheString = tfQuanHe.getText();

		NhanKhauService nhanKhauService = new NhanKhauService();
		QuanHeService quanHeService = new QuanHeService();

		NhanKhauModel nhanKhauModel = new NhanKhauModel(idInt, cmndString, tenString, tuoiInt, sdtString);
		QuanHeModel quanHeModel = new QuanHeModel(mahokhauInt, idInt, quanheString);

		nhanKhauService.add(nhanKhauModel);
		quanHeService.add(quanHeModel);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
}