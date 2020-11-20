package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.ChuHoModel;

public class ChuHoService {
	//checked
		public boolean add(ChuHoModel chuHoModel) throws ClassNotFoundException, SQLException {
			Connection connection = MysqlConnection.getMysqlConnection();
	        String query = "INSERT INTO chu_ho(MaHo, IDChuHo)" 
	                    + " values (?, ?)";
	        
	        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setInt(1,chuHoModel.getMaHo());
	        preparedStatement.setInt(2, chuHoModel.getIdChuHo());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
	        connection.close();
			return true;
		}
		
		// cheked
		public boolean del(int maHo, int idChuHo ) throws ClassNotFoundException, SQLException {
			String sql = "DELETE FROM chu_ho WHERE  MaHo='" +maHo + "' AND IDChuHo = '" +idChuHo+"';" ;
	            Connection connection = MysqlConnection.getMysqlConnection();
	            PreparedStatement preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	            return true;
		}
		
		// checked
		public List<ChuHoModel> getListChuHo() throws ClassNotFoundException, SQLException{
			List<ChuHoModel> list = new ArrayList<>();
			
			Connection connection = MysqlConnection.getMysqlConnection();
	        String query = "SELECT * FROM chu_ho";
	        PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
	        ResultSet rs = preparedStatement.executeQuery();
	        while (rs.next()){
	            ChuHoModel chuHoModel = new ChuHoModel();
	            chuHoModel.setMaHo(rs.getInt("MaHo"));
	            chuHoModel.setIdChuHo(rs.getInt("IDChuHo"));
	            list.add(chuHoModel);
	       }
	       
	        preparedStatement.close();
	        connection.close();
			return list;
		}
}
