package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.HoKhauModel;

public class HoKhauService {

	// checked
	public boolean add(HoKhauModel hoKhauModel) throws ClassNotFoundException, SQLException {

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO ho_khau(MaHo, SoThanhVien, DiaChi)" + " values (?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, hoKhauModel.getMaHo());
		preparedStatement.setInt(2, hoKhauModel.getSoThanhvien());
		preparedStatement.setString(3, hoKhauModel.getDiaChi());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	// checked
	public boolean del(int maHo) throws ClassNotFoundException, SQLException {

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM chu_ho WHERE chu_ho.MaHo='" + maHo + "';";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM chu_ho WHERE MaHo='" + maHo + "'";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		}

		query = "SELECT * FROM quan_he WHERE quan_he.MaHo='" + maHo + "';";
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			query = "DELETE FROM quan_he WHERE MaHo='" + maHo + "'";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		}
		query = "DELETE FROM ho_khau WHERE MaHo = '" + maHo + "'";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public boolean update(int maho ,String diachi) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		PreparedStatement preparedStatement;

		String query = "UPDATE ho_khau " + "set DiaChi =" + "'" + diachi + "' where MaHo =" + maho;
		preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	// checked
	public List<HoKhauModel> getListHoKhau() throws ClassNotFoundException, SQLException {
		List<HoKhauModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM ho_khau";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			HoKhauModel hoKhauModel = new HoKhauModel(rs.getInt("MaHo"), rs.getInt("SoThanhVien"),
					rs.getString("DiaChi"));
			list.add(hoKhauModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}
}
