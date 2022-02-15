package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//classe para se obter ou/e fechar a conexão com o banco

public class DB {
	
	private static Connection conn = null;
	
	public static Connection getConnection() {
		try {
			if(conn == null) {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			}
			return conn;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeConnection() {
		try {
			if(conn != null) {
				conn.close();
			}
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch(IOException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	
}
