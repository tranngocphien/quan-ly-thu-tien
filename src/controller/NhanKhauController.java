package controller;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.NhanKhauModel;
import services.NhanKhauService;

public class NhanKhauController implements Initializable {
	@FXML
	TableColumn<NhanKhauModel, String> colMaNhanKhau;
	@FXML
	TableColumn<NhanKhauModel, String> colTen;
	@FXML
	TableColumn<NhanKhauModel, String> colTuoi;
	@FXML
	TableColumn<NhanKhauModel, String> colCMND;
	@FXML
	TableColumn<NhanKhauModel, String> colSDT;
	@FXML
	TableColumn<NhanKhauModel, String> colMaHo;
	@FXML
	TableView<NhanKhauModel> tvNhanKhau;
	@FXML
	TextField tfSearch;
	@FXML
	ComboBox<String> cbChooseSearch;

	ObservableList<NhanKhauModel> listValueTableView;
	private List<NhanKhauModel> listNhanKhau;

	public NhanKhauController() throws ClassNotFoundException, SQLException {
		listNhanKhau = new NhanKhauService().getListNhanKhau();
	}

	// Hien thi thong tin nhan khau
	public void showNhanKhau() throws ClassNotFoundException, SQLException {
		listValueTableView = FXCollections.observableArrayList(listNhanKhau);

		// Thiet lap Table views
		colMaNhanKhau.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("id"));
		colTen.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("ten"));
		colTuoi.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("tuoi"));
		colCMND.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("cmnd"));
		colSDT.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("sdt"));
		tvNhanKhau.setItems(listValueTableView);

		// Thiet lap Combo box
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Tên", "Tuổi", "id");
		cbChooseSearch.setValue("Chọn");
		cbChooseSearch.setItems(listComboBox);
	}

	// Tim kiem nhan khau theo ten, tuoi, id
	public void searchNhanKhau() {
		ObservableList<NhanKhauModel> listValueTableView_tmp;
		String keySearch = tfSearch.getText();

		// Lay ra lua chon tim kiem cua nguoi dung
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// Tim kiem theo lua chon
		switch (typeSearchString) {
		case "Tên": {
			// Neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvNhanKhau.setItems(listValueTableView);
				JOptionPane.showMessageDialog(null, "Bạn phải nhập vào cái gì đó");
				break;
			}
			
			int index = 0;
			for (NhanKhauModel nhanKhauModel : listNhanKhau) {
				if (nhanKhauModel.getTen().equals(keySearch)) {
					listValueTableView_tmp = FXCollections.observableArrayList(nhanKhauModel);
					tvNhanKhau.setItems(listValueTableView_tmp);
					index++;
				}
			}

			// Duyet het vong for khong tim thay thong tin can tim -> thong bao loi
			if (index == 0) {
				JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin");
			}
			break;
		}
		case "Tuổi": {
			// Neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvNhanKhau.setItems(listValueTableView);
				JOptionPane.showMessageDialog(null, "Bạn phải nhập vào cái gì đó");
				break;
			}

			// Kiem tra chuoi nhap vao co phai so hay khong, chua ki tu khong phai so -> in
			// ra thong bao loi
			int index = 0;
			for (int i = 0; i < keySearch.length(); i++) {
				if ((int) keySearch.charAt(i) < 48 || (int) keySearch.charAt(i) > 57)
					index++;
			}
			if (index > 0) {
				JOptionPane.showMessageDialog(null, "Bạn phải nhập vào một số");
				break;
			}

			List<NhanKhauModel> listNhanKhau_tmp = new ArrayList<>();
			for (NhanKhauModel nhanKhauModel : listNhanKhau) {
				if (nhanKhauModel.getTuoi() == Integer.parseInt(keySearch)) {
					listNhanKhau_tmp.add(nhanKhauModel);
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listNhanKhau_tmp);
			tvNhanKhau.setItems(listValueTableView_tmp);
			break;
		}
		default: { // tim theo id, hoac khong chon tim theo gi
			// Neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvNhanKhau.setItems(listValueTableView);
				JOptionPane.showMessageDialog(null, "Bạn phải nhập vào cái gì đó");
				break;
			}

			// Kiem tra chuoi nhap vao co phai so hay khong, chua ki tu khong phai so -> in
			// ra thong bao loi
			int index = 0;
			for (int i = 0; i < keySearch.length(); i++) {
				if ((int) keySearch.charAt(i) < 48 || (int) keySearch.charAt(i) > 57)
					index++;
			}
			if (index > 0) {
				JOptionPane.showMessageDialog(null, "Bạn phải nhập vào một số");
				break;
			}

			List<NhanKhauModel> listNhanKhau_tmp = new ArrayList<>();
			for (NhanKhauModel nhanKhauModel : listNhanKhau) {
				if (nhanKhauModel.getId() == Integer.parseInt(keySearch)) {
					listNhanKhau_tmp.add(nhanKhauModel);
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listNhanKhau_tmp);
			tvNhanKhau.setItems(listValueTableView_tmp);
		}
		}
	}

	public void addNhanKhau(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/nhankhau/AddNhanKhau.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(home,800,600));
        stage.setResizable(false);
        stage.showAndWait();
        showNhanKhau();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			showNhanKhau();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
