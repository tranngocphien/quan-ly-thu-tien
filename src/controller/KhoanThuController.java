package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.KhoanThuModel;

public class KhoanThuController implements Initializable {

	@FXML
	private TableView<KhoanThuModel> tvKhoanPhi;
	@FXML
	private TableColumn<KhoanThuModel,String> colMaKhoanPhi;
	@FXML
	private TableColumn<KhoanThuModel, String> colTenKhoanThu;
	@FXML
	private TableColumn<KhoanThuModel, String> colSoTien;
	@FXML
	private TableColumn<KhoanThuModel, String> colLoaiKhoanThu;
	@FXML
	private TextField tfSearch;
	@FXML
	private ComboBox<String> cbChooseSearch;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
