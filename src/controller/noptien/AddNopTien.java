package controller.noptien;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import models.NhanKhauModel;
import models.NopTienModel;
import services.NopTienService;

public class AddNopTien {
	@FXML
	private TextField tfTenKhoanThu;
	@FXML
	private TextField tfTenNguoiNop;
	private KhoanThuModel khoanThuModel;
	private NhanKhauModel nhanKhauModel;
	
	public void chooseKhoanThu() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/noptien/ChooseKhoanNop.fxml"));
		Parent home = loader.load(); 
        Stage stage = new Stage();
        stage.setScene(new Scene(home,800,600));
        stage.setResizable(false);
        stage.showAndWait();
        
        ChooseKhoanNop chooseKhoanNop = loader.getController();
        khoanThuModel = chooseKhoanNop.getKhoanthuChoose();
        if(khoanThuModel == null) return;
        
        tfTenKhoanThu.setText(khoanThuModel.getTenKhoanThu());
	}
	
	public void chooseNguoiNop() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/noptien/ChooseNguoiNop.fxml"));
		Parent home = loader.load(); 
        Stage stage = new Stage();
        stage.setScene(new Scene(home,800,600));
        stage.setResizable(false);
        stage.showAndWait();
        
        ChooseNguoiNop chooseNguoiNop = loader.getController();
        nhanKhauModel = chooseNguoiNop.getNhanKhauChoose();
        if(nhanKhauModel == null) return;
        
        tfTenNguoiNop.setText(nhanKhauModel.getTen());
	}
	
	public void addNopTien() throws ClassNotFoundException, SQLException {
		if(tfTenKhoanThu.getText().length() == 0 || tfTenNguoiNop.getText().length() == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng chọn thông tin nộp tiền!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		} else {
			//new NopTienService().add(new NopTienModel(khoanThuModel.getMaKhoanThu(), nhanKhauModel.getId()));
		}
	}
}
