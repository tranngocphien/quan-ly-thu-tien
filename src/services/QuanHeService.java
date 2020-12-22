package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.QuanHeModel;

public class QuanHeService {

	// checked
	public boolean add(QuanHeModel quanHeModel) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO quan_he(MaHo, IDThanhVien, QuanHe)" + " values (?, ?, ?)";

		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, quanHeModel.getMaHo());
		preparedStatement.setInt(2, quanHeModel.getIdThanhVien());
		preparedStatement.setString(3, quanHeModel.getQuanHe());
		preparedStatement.executeUpdate();
		query = "UPDATE ho_khau set SoThanhVien = SoThanhVien + 1 where maho='" + quanHeModel.getMaHo() + "';";
		preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	// cheked
	public boolean del(int maHo, int idThanhVien) throws ClassNotFoundException, SQLException {
//		String sql = "DELETE FROM quan_he WHERE  MaHo='" + maHo + "' AND IDThanhVien = '" + idThanhVien + "';";
		Connection connection = MysqlConnection.getMysqlConnection();
//		PreparedStatement preparedStatement = connection.prepareStatement(sql);
//		preparedStatement.executeUpdate();
		String sql = "UPDATE ho_khau set SoThanhVien = SoThanhVien - 1 where maho='" + maHo + "';";
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	// checked
	public List<QuanHeModel> getListQuanHe() throws ClassNotFoundException, SQLException {
		List<QuanHeModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM quan_he";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			QuanHeModel quanHeModel = new QuanHeModel();
			quanHeModel.setMaHo(rs.getInt("MaHo"));
			quanHeModel.setIdThanhVien(rs.getInt("IDThanhVien"));
			quanHeModel.setQuanHe(rs.getString("QuanHe"));
			list.add(quanHeModel);
		}

		preparedStatement.close();
		connection.close();
		return list;
	}

}
