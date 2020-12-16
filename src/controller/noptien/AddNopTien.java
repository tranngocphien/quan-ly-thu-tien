package controller.noptien;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
        stage.setTitle("Chọn người nộp");
        stage.setScene(new Scene(home,800,600));
        stage.setResizable(false);
        stage.showAndWait();
        
        ChooseNguoiNop chooseNguoiNop = loader.getController();
        nhanKhauModel = chooseNguoiNop.getNhanKhauChoose();
        if(nhanKhauModel == null) return;
        
        tfTenNguoiNop.setText(nhanKhauModel.getTen());
	}
	
	public void addNopTien(ActionEvent event) throws ClassNotFoundException, SQLException {		
		if(tfTenKhoanThu.getText().length() == 0 || tfTenNguoiNop.getText().length() == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng nhập khoản nộp hợp lí!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			List<NopTienModel> listNopTien = new NopTienService().getListNopTien();
			for(NopTienModel nopTienModel : listNopTien) {
				if(nopTienModel.getIdNopTien() == nhanKhauModel.getId() 
						&& nopTienModel.getMaKhoanThu() == khoanThuModel.getMaKhoanThu()) {
					Alert alert = new Alert(AlertType.WARNING, "Người này đã từng nộp khoản phí này!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					return;
				}
			}
			
			new NopTienService().add(new NopTienModel( nhanKhauModel.getId(),khoanThuModel.getMaKhoanThu()));
		}
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Thêm nộp tiền");
		stage.setResizable(false);
        stage.close();
	}
}
