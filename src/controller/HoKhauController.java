package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ChuHoModel;
import models.HoKhauModel;
import models.NhanKhauModel;
import models.NopTienModel;
import services.ChuHoService;
import services.HoKhauService;
import services.NhanKhauService;

public class HoKhauController implements Initializable {
	@FXML
	TableColumn<HoKhauModel, String> colMaHoKhau;
	@FXML
	TableColumn<HoKhauModel, String> colMaChuHo;
	@FXML
	TableColumn<HoKhauModel, String> colSoThanhVien;
	@FXML
	TableColumn<HoKhauModel, String> colDiaChi;
	@FXML
	TableView<HoKhauModel> tvHoKhau;

	@FXML
	ComboBox<String> cbChooseSearch;
	
	ObservableList<HoKhauModel> listValueTableView;
	private List<HoKhauModel> listHoKhau;
	
	// Hien thi thong tin nhan khau
	public void showNhanKhau() throws ClassNotFoundException, SQLException {
		listHoKhau = new HoKhauService().getListHoKhau();
		listValueTableView = FXCollections.observableArrayList(listHoKhau);
		List<NhanKhauModel> listNhanKhau = new NhanKhauService().getListNhanKhau();
		List<ChuHoModel> listChuHo = new ChuHoService().getListChuHo();
		
		Map<Integer, Integer> mapMahoToId = new HashMap<>();
		listChuHo.forEach(chuho -> {
			mapMahoToId.put(chuho.getMaHo(), chuho.getIdChuHo());
		});
		
		Map<Integer, String> mapIdToTen = new HashMap<>();
		listNhanKhau.forEach(nhankhau -> {
			mapIdToTen.put(nhankhau.getId(), nhankhau.getTen());
		});
		
		// Thiet lap Table views
		colMaHoKhau.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("maHo"));
		colMaChuHo.setCellValueFactory(
				(CellDataFeatures<HoKhauModel, String> p) -> new ReadOnlyStringWrapper(mapIdToTen.get(mapMahoToId.get(p.getValue().getMaHo())).toString())
		);
		colSoThanhVien.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("soThanhvien"));
		colDiaChi.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("diaChi"));
		tvHoKhau.setItems(listValueTableView);

		// Thiet lap Combo box
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Mã hộ", "Id chủ hộ", "Địa chỉ");
		cbChooseSearch.setValue("Chọn");
		cbChooseSearch.setItems(listComboBox);
	}

	public void addHoKhau(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views.hokhau/AddHoKhau.fxml"));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showNhanKhau();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
