package app;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection conn = DB.getConnection();
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
//			st = conn.prepareStatement("INSERT INTO department "
//					+ "(Name) "
//					+ "VALUES "
//					+ "(?)",
//					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, "Cassio");
			st.setString(2, "cassio@email.com");
			st.setDate(3, new Date(sdf.parse("12/04/1978").getTime()));
			st.setDouble(4, 5000.00);
			st.setInt(5, 4);
			
//			st.setString(1, "test1");
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				
				ResultSet rs = st.getGeneratedKeys();
				
				while(rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Generated id: " + id);
				}
				
				
			} else {
				System.out.println("Nothing rows affected");
			}

			
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
			catch (ParseException e) {
			e.printStackTrace();
		}
		
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}

	}

}
