package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.ChuHoModel;
import models.KhoanThuModel;
import models.NhanKhauModel;
import models.NopTienModel;
import services.ChuHoService;
import services.KhoanThuService;
import services.NhanKhauService;
import services.NopTienService;

public class NopTienController implements Initializable {
	@FXML
	private TableView<NopTienModel> tvNopTien;
	@FXML
	private TableColumn<NopTienModel, String> tbcTenNguoi;
	@FXML
	private TableColumn<NopTienModel, String> tbcTenKhoanThu;
	@FXML
	private TableColumn<NopTienModel, String> tbcNgayThu;
	@FXML
	private ComboBox<String> cbChooseSearch;
	@FXML
	private TextField tfSearch;

	ObservableList<NopTienModel> listValueTableView;
	private List<NopTienModel> listNopTien;
	private List<NhanKhauModel> listNhanKhau;
	private List<KhoanThuModel> listKhoanThu;
	Map<Integer, String> mapIdToTen;
	Map<Integer, String> mapIdToTenKhoanThu;

	public void showNopTien() throws ClassNotFoundException, SQLException {
		listNopTien = new NopTienService().getListNopTien();
		listKhoanThu = new KhoanThuService().getListKhoanThu();
		listNhanKhau = new NhanKhauService().getListNhanKhau();
		listValueTableView = FXCollections.observableArrayList(listNopTien);

		mapIdToTen = new HashMap<>();
		listNhanKhau.forEach(nhankhau -> {
			mapIdToTen.put(nhankhau.getId(), nhankhau.getTen());
		});
		mapIdToTenKhoanThu = new HashMap<>();
		listKhoanThu.forEach(khoanthu -> {
			mapIdToTenKhoanThu.put(khoanthu.getMaKhoanThu(), khoanthu.getTenKhoanThu());
		});

		try {
			tbcTenNguoi.setCellValueFactory((CellDataFeatures<NopTienModel, String> p) -> new ReadOnlyStringWrapper(
					mapIdToTen.get(p.getValue().getIdNopTien())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			tbcTenKhoanThu.setCellValueFactory((CellDataFeatures<NopTienModel, String> p) -> new ReadOnlyStringWrapper(
					mapIdToTenKhoanThu.get(p.getValue().getMaKhoanThu())));
		} catch (Exception e) {
			// TODO: handle exception
		}

		tbcNgayThu.setCellValueFactory(new PropertyValueFactory<>("ngayThu"));
		tvNopTien.setItems(listValueTableView);

		// thiet lap gia tri cho combobox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Tên người nộp", "Tên khoản thu");
		cbChooseSearch.setValue("Tên người nộp");
		cbChooseSearch.setItems(listComboBox);
	}

	public void searchNopTien() {
		ObservableList<NopTienModel> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// tim kiem thong tin theo lua chon da lay ra
		switch (typeSearchString) {
		case "Tên người nộp": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvNopTien.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int index = 0;
			List<NopTienModel> listNopTienModelsSearch = new ArrayList<>();
			for (NopTienModel nopTienModel : listNopTien) {
				if (mapIdToTen.get(nopTienModel.getIdNopTien()).contains(keySearch)) {
					listNopTienModelsSearch.add(nopTienModel);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listNopTienModelsSearch);
			tvNopTien.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
			// tim thay
			if (index == 0) {
				tvNopTien.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		default: { // truong hop con lai : tim theo ten khoan thu
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvNopTien.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int index = 0;
			List<NopTienModel> listNopTienModelsSearch = new ArrayList<>();
			for (NopTienModel nopTienModel : listNopTien) {
				if (mapIdToTenKhoanThu.get(nopTienModel.getIdNopTien()).contains(keySearch)) {
					listNopTienModelsSearch.add(nopTienModel);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listNopTienModelsSearch);
			tvNopTien.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
			// tim thay
			if (index == 0) {
				tvNopTien.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
		}
		}
	}

	public void addNopTien(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/noptien/AddNopTien.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
		stage.setResizable(false);
		stage.showAndWait();
		showNopTien();
	}

	public void delNopTien() throws ClassNotFoundException, SQLException {
		NopTienModel nopTienModel = tvNopTien.getSelectionModel().getSelectedItem();

		if (nopTienModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn khoản nộp bạn muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		
		Alert alert = new Alert(AlertType.WARNING, "Bạn có chắc chắn muốn xóa nhân khẩu này!", ButtonType.YES,
				ButtonType.NO);
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.NO) {
			return;
		} else {
			new NopTienService().del(nopTienModel.getIdNopTien(), nopTienModel.getMaKhoanThu());
		}
		
		showNopTien();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showNopTien();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
