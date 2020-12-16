package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.KhoanThuModel;


public class KhoanThuService {
	
	// ckecked
	public  boolean add(KhoanThuModel khoanThuModel) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO khoan_thu(MaKhoanThu, TenKhoanThu, SoTien, LoaiKhoanThu)" 
                    + " values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1,khoanThuModel.getMaKhoanThu());
        preparedStatement.setString(2, khoanThuModel.getTenKhoanThu());
        preparedStatement.setDouble(3, khoanThuModel.getSoTien());
        preparedStatement.setInt(4, khoanThuModel.getLoaiKhoanThu());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
		return true;
	}
	
	// checked
	public boolean del(int maKhoanThu) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM nop_tien WHERE MaKhoanThu='"+maKhoanThu+"';";
	    PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
	    ResultSet rs = preparedStatement.executeQuery();
	    while (rs.next()){
	    	query="DELETE FROM nop_tien WHERE MaKhoanThu='"+maKhoanThu+"'";
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.executeUpdate();
	    }
	    query ="DELETE FROM khoan_thu WHERE MaKhoanThu = '"+maKhoanThu+"'";
	    preparedStatement = connection.prepareStatement(query);
		preparedStatement.executeUpdate();
		preparedStatement.close();
	    connection.close();
		return true;  
	}
	
	public boolean update(int maKhoanThu, String tenKhoanThu, double soTien, int loaiKhoanThu) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		PreparedStatement preparedStatement;

		String query = "UPDATE khoan_thu " + "set TenKhoanThu =" + "'" + tenKhoanThu + "'," + "SoTien ="
				+ soTien + "," + "LoaiKhoanThu =" + loaiKhoanThu + " where MaKhoanThu =" + maKhoanThu;
		System.out.println(query);
		preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		
		
		return true;
	}
	
	// checked
	public List<KhoanThuModel> getListKhoanThu() throws ClassNotFoundException, SQLException{
		List<KhoanThuModel> list = new ArrayList<>();
		
		Connection connection = MysqlConnection.getMysqlConnection();
	    String query = "SELECT * FROM khoan_thu";
	    PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
	    ResultSet rs = preparedStatement.executeQuery();
	    while (rs.next()){
	        KhoanThuModel khoanThuModel = new KhoanThuModel(rs.getInt("MaKhoanThu"),rs.getString("TenKhoanThu"),
	        		rs.getDouble("SoTien"),rs.getInt("LoaiKhoanThu"));
	        list.add(khoanThuModel);
	   }
	    preparedStatement.close();
	    connection.close();
		return list;
	}
	
}
