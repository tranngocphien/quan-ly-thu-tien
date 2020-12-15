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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.NhanKhauModel;
import models.NopTienModel;
import services.NhanKhauService;
import services.NopTienService;

public class NopTienController implements Initializable {
	@FXML
	private TableView<NopTienModel> tvNopTien;
	@FXML
	private TableColumn<NopTienModel, String> tbcMaNguoi;
	@FXML
	private TableColumn<NopTienModel, String> tbcMaKhoanThu;
	@FXML
	private TableColumn<NopTienModel, String> tbcNgayThu;
	
	ObservableList<NopTienModel> listValueTableView;
	private List<NopTienModel> listDongPhi;
	private List<NhanKhauModel> listNhanKhau;
	
	public void showDongPhi() throws ClassNotFoundException, SQLException {
		listDongPhi = new NopTienService().getListNopTien();
		listNhanKhau = new NhanKhauService().getListNhanKhau();
		listValueTableView = FXCollections.observableArrayList(listDongPhi);
		
		System.out.println(listDongPhi.size());
		
		Map<Integer, String> mapIdToTen = new HashMap<>();
		listNhanKhau.forEach(nhankhau -> {
			mapIdToTen.put(nhankhau.getId(), nhankhau.getTen());
		});
		
		try {
			tbcMaNguoi.setCellValueFactory(
					(CellDataFeatures<NopTienModel, String> p) -> new ReadOnlyStringWrapper(mapIdToTen.get(p.getValue().getIdNopTien()))
			);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		tbcMaKhoanThu.setCellValueFactory(new PropertyValueFactory<>("maKhoanThu"));
		tbcNgayThu.setCellValueFactory(new PropertyValueFactory<>("ngayThu"));
		tvNopTien.setItems(listValueTableView);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showDongPhi();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}	
