package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.ChuHoModel;
import models.HoKhauModel;
import models.NhanKhauModel;
import models.QuanHeModel;
import services.ChuHoService;
import services.HoKhauService;
import services.NhanKhauService;
import services.QuanHeService;

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
	
	// Hien thi thong tin ho khau
	public void showHoKhau() throws ClassNotFoundException, SQLException {
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

	public void addHoKhau() throws ClassNotFoundException, SQLException, IOException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/hokhau/AddHoKhau.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(home,800,600));
        stage.setResizable(false);
        stage.showAndWait();
        showHoKhau();
	}

	public void delHoKhau() throws ClassNotFoundException, SQLException {
		HoKhauModel hoKhauModel = tvHoKhau.getSelectionModel().getSelectedItem();
		
		if(hoKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn hộ khẩu bạn muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Khi xóa hộ khẩu, tất cả thành viên trong hộ đều sẽ bị xóa!", ButtonType.YES, ButtonType.NO);
			alert.setHeaderText("Bạn có chắc chắn muốn xóa hộ khẩu này");
			Optional<ButtonType> result = alert.showAndWait();
			
			if(result.get() == ButtonType.NO) {
				return;
			} else {
				/// tao map anh xa gia tri Id sang maHo
				Map<Integer, Integer> mapIdToMaho = new HashMap<>();
				List<QuanHeModel> listQuanHe = new QuanHeService().getListQuanHe();
				listQuanHe.forEach(quanhe -> {
					mapIdToMaho.put(quanhe.getIdThanhVien(), quanhe.getMaHo());
				});
				
				int idHoKhauDel = hoKhauModel.getMaHo();
				// xoa toan bo nhan khau trong ho khau
				List<NhanKhauModel> listNhanKhauModels = new NhanKhauService().getListNhanKhau();
				listNhanKhauModels.stream().filter(nhankhau -> mapIdToMaho.get(nhankhau.getId()) == idHoKhauDel).forEach(nk -> {
					try {
						new NhanKhauService().del(nk.getId());
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				
				// xoa ho khau
				new HoKhauService().del(idHoKhauDel);
			}
		}
		
		showHoKhau();
	}
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showHoKhau();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
