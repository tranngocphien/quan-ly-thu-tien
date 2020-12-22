package controller.khoanthu;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import models.NhanKhauModel;
import services.KhoanThuService;
import services.NhanKhauService;

public class AddKhoanThu implements Initializable {
	@FXML
	private TextField tfMaKhoanThu;
	@FXML
	private TextField tfTenKhoanThu;
	@FXML
	private ComboBox<String> cbLoaiKhoanThu;
	@FXML
	private TextField tfSoTien;

	public void addKhoanThu(ActionEvent event) throws ClassNotFoundException, SQLException {
		Pattern pattern;

		// kiem tra maKhoanThu nhap vao
		// maKhoanThu la day so tu 1 toi 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfMaKhoanThu.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã khoản thu hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		
		// kiem tra ma khoan thu them moi co bi trung voi nhung ma khoan thu da ton tai hay khong
		List<KhoanThuModel> listKhoanThuModels = new KhoanThuService().getListKhoanThu(); 
		for(KhoanThuModel khoanThuModel : listKhoanThuModels) {
			if(khoanThuModel.getMaKhoanThu() == Integer.parseInt(tfMaKhoanThu.getText())) {
				Alert alert = new Alert(AlertType.WARNING, "Mã khoản thu đã bị trùng!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}
		
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
		pattern = Pattern.compile("^[1-9]\\d*(\\.\\d+)?$");
		if (!pattern.matcher(tfSoTien.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào số tiền hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// ghi nhan gia tri ghi tat ca deu da hop le
		SingleSelectionModel<String> loaiKhoanThuSelection = cbLoaiKhoanThu.getSelectionModel();
		String loaiKhoanThu_tmp = loaiKhoanThuSelection.getSelectedItem();
		
		int maKhoanThuInt = Integer.parseInt(tfMaKhoanThu.getText());
		String tenKhoanThuString = tfTenKhoanThu.getText();
		double soTienDouble = Double.parseDouble(tfSoTien.getText());
		int loaiKhoanThu;
		if(loaiKhoanThu_tmp.equals("Bắt buộc")) {
			loaiKhoanThu = 1;
		} else {
			loaiKhoanThu = 0;
		}

		new KhoanThuService().add(new KhoanThuModel(maKhoanThuInt, tenKhoanThuString, soTienDouble, loaiKhoanThu));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// thiet lap gia tri cho loai khoan thu
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Tự nguyện", "Bắt buộc");
		cbLoaiKhoanThu.setValue("Bắt buộc");
		cbLoaiKhoanThu.setItems(listComboBox);
	}
}
