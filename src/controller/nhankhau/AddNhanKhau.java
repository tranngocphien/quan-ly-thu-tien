package controller.nhankhau;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import models.NhanKhauModel;
import models.QuanHeModel;
import services.NhanKhauService;
import services.QuanHeService;

public class AddNhanKhau {
	@FXML
	private TextField tfId;
	@FXML
	private TextField tfTen;
	@FXML
	private TextField tfTuoi;
	@FXML
	private TextField tfCmnd;
	@FXML
	private TextField tfSdt;
	@FXML
	private TextField tfMaHoKhau;
	@FXML
	private TextField tfQuanHe;
	@FXML
	private CheckBox cboxChuHo;

	public void addNhanKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
		// Khai bao mot mau de so sanh va kiem tra cac truong nhap vao
		Pattern pattern; 
		
		// Kiem tra nhap vao ID 
		// ID phai la mot so it hon 11 chu so va khong duoc chua chu cai
		pattern = Pattern.compile("\\d{1,11}");
		if(!pattern.matcher(tfId.getText()).matches()) {
			JOptionPane.showMessageDialog(null, "     ID không chứa chữ cái \nvà phải là 1 số nhỏ hơn 11 chữ số");
			return;
		}

		// Kiem tra nhap vao Ten
		// Ten la chuoi ki tu co nhieu nhat 50 ki tu
		if (tfTen.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Bạn phải nhập vào Tên");
			return;
		} else {
			if (tfTen.getText().length() > 50) {
				JOptionPane.showMessageDialog(null, "Tên phải ít hơn 50 kí tự");
				return;
			}
		}

		// Kiem tra nhap vao Tuoi
		// Tuoi nhap vao phai la mot so co nhieu nhat 3 chu so
		pattern = Pattern.compile("\\d{1,3}");
		if(!pattern.matcher(tfTuoi.getText()).matches()) {
			JOptionPane.showMessageDialog(null, "Bạn phải nhập vào tuổi là 1 số \n    và số tuổi hợp lệ");
			return;
		}
		
		// Kiem tra so CMND nhap vao
		// CMND nhap vao phai khong chua ki tu chu cai la nho hon 20 chu so
		pattern = Pattern.compile("\\d{1,20}");
		if(!pattern.matcher(tfCmnd.getText()).matches()) {
			JOptionPane.showMessageDialog(null, "    CMND không bao gồm chữ \nvà phải nhỏ hơn 20 chữ số");
			return;
		}
		
		// Kiem tra sdt nhap vao
		// SDT nhap vao phai khong chua chu cai va nho hon 15 chu so
		pattern = Pattern.compile("\\d{1,15}");
		if(!pattern.matcher(tfSdt.getText()).matches()) {
			JOptionPane.showMessageDialog(null, "  SĐT không bao gồm chữ \nvà phải nhỏ hơn 15 chữ số");
			return;
		}
		
		// Kiem tra ma ho nhap vao
		// Ma ho nhap vao phai khong chua chu cai va nho hon 11 chu so 
		pattern = Pattern.compile("\\d{1,11}");
		if(!pattern.matcher(tfMaHoKhau.getText()).matches()) {
			JOptionPane.showMessageDialog(null, "       Mã hộ không chứa chữ cái \nvà phải là 1 số nhỏ hơn 11 chữ số");
			return;
		}
		

		// Kiem tra Quan he nhap vao
		if (tfQuanHe.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Bạn phải nhập vào Tên");
			return;
		} else {
			if (tfQuanHe.getText().length() > 30) {
				JOptionPane.showMessageDialog(null, "Tên phải ít hơn 30 kí tự");
				return;
			}
		}
		
		// Ghi nhan gia tri da nhap vao hop le
		int idInt = Integer.parseInt(tfId.getText());
		String tenString = tfTen.getText();
		int tuoiInt = Integer.parseInt(tfTuoi.getText());
		String cmndString = tfCmnd.getText();
		String sdtString = tfSdt.getText();
		int mahokhauInt = Integer.parseInt(tfMaHoKhau.getText());
		String quanheString = tfQuanHe.getText();
		boolean isChuHo = cboxChuHo.isSelected();

		if(!isChuHo) {
			NhanKhauService nhanKhauService = new NhanKhauService();
			QuanHeService quanHeService = new QuanHeService();

			NhanKhauModel nhanKhauModel = new NhanKhauModel(idInt,cmndString,tenString,tuoiInt,sdtString);
			QuanHeModel quanHeModel = new QuanHeModel(mahokhauInt, idInt, quanheString);
			
			nhanKhauService.add(nhanKhauModel);
			quanHeService.add(quanHeModel);
			
			Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
	        stage.close();
		}
		
				
	}
}
