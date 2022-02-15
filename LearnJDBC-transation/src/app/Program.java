package app;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DBException;

public class Program {

	public static void main(String[] args) {
		
		// Essa implementação serve para proteger os dados do banco, impedindo 
		// que se caso aconteça um erro ele impede todas as alterações, sendo assim
		// as alterações só serão efetuadas caso não ocorra nenhum erro 
		
		Connection conn = null;
		
		Statement st = null;
		
		try {
			conn = DB.getConnection();
			
			st = conn.createStatement();
			
			conn.setAutoCommit(false);
			
			int row1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1 ");
			
			int i = 2;
			if(i < 3) {
				throw new SQLException("Fake error");
			}
			
			int row2 = st.executeUpdate("UPDATE seller SET BaseSalary = 5090 WHERE DepartmentId = 2" );
			
			conn.commit();
			
			System.out.println("Row1 affected: " + row1);
			System.out.println("Row2 affected: " + row2);
			
			
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transation rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Error trying rollback! Caused by: " + e1.getMessage());
			}
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}

}
