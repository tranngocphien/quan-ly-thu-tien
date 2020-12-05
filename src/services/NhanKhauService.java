package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.NhanKhauModel;

public class NhanKhauService {

	// checked
	public boolean add(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO nhan_khau(ID, CMND, Ten, Tuoi, SDT)" + " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, nhanKhauModel.getId());
		preparedStatement.setString(2, nhanKhauModel.getCmnd());
		preparedStatement.setString(3, nhanKhauModel.getTen());
		preparedStatement.setInt(4, nhanKhauModel.getTuoi());
		preparedStatement.setString(5, nhanKhauModel.getSdt());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	// checked
	public boolean del(int ID) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM nop_tien WHERE IDNopTien='" + ID + "';";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM nop_tien WHERE IDNopTien='" + ID + "'";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		}

		query = "SELECT * FROM chu_ho WHERE chu_ho.IDChuHo='" + ID + "';";
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM chu_ho WHERE IDChuHo='" + ID + "'";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		}

		query = "SELECT * FROM quan_he WHERE quan_he.IDThanhVien='" + ID + "';";
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM quan_he WHERE IDThanhVien='" + ID + "'";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		}
		query = "DELETE FROM nhan_khau WHERE ID = '" + ID + "'";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public boolean update(int id, String cmnd, String ten, int tuoi, String sdt)
			throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		PreparedStatement preparedStatement;

		String query = "UPDATE nhan_khau " + "set CMND =" + "'" + cmnd + "'," + "Ten =" + "'" + ten + "'," + "Tuoi ="
				+ tuoi + "," + "SDT =" + "'" + sdt + "' where ID =" + id;
		preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	// checked
	public List<NhanKhauModel> getListNhanKhau() throws ClassNotFoundException, SQLException {
		List<NhanKhauModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM nhan_khau";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("ID"), rs.getString("CMND"), rs.getString("Ten"),
					rs.getInt("Tuoi"), rs.getString("SDT"));
			list.add(nhanKhauModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}

}
