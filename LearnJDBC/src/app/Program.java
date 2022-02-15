package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = DB.getConnection();
		
		Statement st = null;
		
		ResultSet rs = null;
		
		try {
			
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * from departament");
			
			while (rs.next()) {
				System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
			}
			
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			DB.closeConnection();
		}

	}

}
