package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import models.KhoanThuModel;
import models.NopTienModel;
import services.KhoanThuService;
import services.NopTienService;

public class ThongKeController implements Initializable {
	@FXML
	TableColumn<KhoanThuModel, String> colTenPhi;
	@FXML
	TableColumn<KhoanThuModel, String> colTongSoTien;
	@FXML
	TableView<KhoanThuModel> tvThongKe;
	@FXML
	ComboBox<String> cbChooseSearch;

	private ObservableList<KhoanThuModel> listValueTableView;
	private List<KhoanThuModel> listKhoanThu;

	public void showThongKe() throws ClassNotFoundException, SQLException {
		listKhoanThu = new KhoanThuService().getListKhoanThu();
		listValueTableView = FXCollections.observableArrayList(listKhoanThu);

		List<NopTienModel> listNopTien = new NopTienService().getListNopTien();
		Map<Integer, Long> mapMaKhoanThuToSoLuong = new TreeMap<>();
		for (KhoanThuModel khoanThuModel : listKhoanThu) {
			long cntNopTien = listNopTien.stream()
					.filter(noptien -> noptien.getMaKhoanThu() == khoanThuModel.getMaKhoanThu()).count();
			mapMaKhoanThuToSoLuong.put(khoanThuModel.getMaKhoanThu(), cntNopTien);
		}

		// thiet lap cac cot cho tableviews
		colTenPhi.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("tenKhoanThu"));
		try {
			colTongSoTien.setCellValueFactory(
					(CellDataFeatures<KhoanThuModel, String> p) -> new ReadOnlyStringWrapper(Double.toString(
							mapMaKhoanThuToSoLuong.get(p.getValue().getMaKhoanThu()) * p.getValue().getSoTien())));
		} catch (Exception e) {
			// TODO: handle exception
		}

		tvThongKe.setItems(listValueTableView);
		// thiet lap gia tri cho combobox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Bắt buộc", "Tự nguyện");
		cbChooseSearch.setValue("Tất cả");
		cbChooseSearch.setItems(listComboBox);
	}

	public void loc() {
		ObservableList<KhoanThuModel> listValueTableView_tmp = null;
		
		List<KhoanThuModel> listKhoanThuBatBuoc = new ArrayList<>();
		List<KhoanThuModel> listKhoanThuTuNguyen = new ArrayList<>();
		for (KhoanThuModel khoanThuModel : listKhoanThu) {
			if (khoanThuModel.getLoaiKhoanThu() == 0) {
				listKhoanThuTuNguyen.add(khoanThuModel);
			} else {
				listKhoanThuBatBuoc.add(khoanThuModel);
			}
		}

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();
		
		switch (typeSearchString) {
		case "Tất cả":
			tvThongKe.setItems(listValueTableView);
			break;
		case "Bắt buộc":
			listValueTableView_tmp = FXCollections.observableArrayList(listKhoanThuBatBuoc);
			tvThongKe.setItems(listValueTableView_tmp);
			break;
		case "Tự nguyện":
			listValueTableView_tmp = FXCollections.observableArrayList(listKhoanThuTuNguyen);
			tvThongKe.setItems(listValueTableView_tmp);
			break;
		default:
			break;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showThongKe();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
