package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.NhanKhauModel;
import services.NhanKhauService;

public class NhanKhauController implements Initializable{
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
	
	private ObservableList<NhanKhauModel> listNhanKhau;
	
	public void showNhanKhau() throws ClassNotFoundException, SQLException {
		listNhanKhau = FXCollections.observableArrayList((new NhanKhauService()).getListNhanKhau());
		
		colMaNhanKhau.setCellValueFactory(new PropertyValueFactory<NhanKhauModel,String >("id"));
		colTen.setCellValueFactory(new PropertyValueFactory<NhanKhauModel,String >("ten"));
		colTuoi.setCellValueFactory(new PropertyValueFactory<NhanKhauModel,String >("tuoi"));
		colCMND.setCellValueFactory(new PropertyValueFactory<NhanKhauModel,String >("cmnd"));
		colSDT.setCellValueFactory(new PropertyValueFactory<NhanKhauModel,String >("sdt"));
		
		tvNhanKhau.setItems(listNhanKhau);
		
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
