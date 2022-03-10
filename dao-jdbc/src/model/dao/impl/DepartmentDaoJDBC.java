package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
	this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
	PreparedStatement statement = null;

	try {
	    statement = conn.prepareStatement("INSERT INTO department " + "(Name) " + "VALUE " + "(?)",
		    Statement.RETURN_GENERATED_KEYS);
	    statement.setString(1, obj.getName());

	    int rowsAffected = statement.executeUpdate();

	    if (rowsAffected > 0) {
		// Esse bloco serve para duas finalidades:
		// A primeira é verificar se o objeto foi inserido, se o rowsAffected não
		// retornar nada, significa que nada foi inserido
		// A segunda é para inserirmos o id criado no objeto que passamos como
		// parâmetro, já que o mesmo veio sem id
		ResultSet resultSet = statement.getGeneratedKeys();
		if (resultSet.next()) {
		    int id = resultSet.getInt(1);
		    obj.setId(id);
		} else {
		    throw new DBException("Unexpected error! No rows affected");
		}
		DB.closeResultSet(resultSet);

	    }

	} catch (SQLException e) {
	    throw new DBException(e.getMessage());
	} finally {
	    DB.closeStatement(statement);
	}

    }

    @Override
    public void update(Department obj) {
	PreparedStatement statement = null;

	try {
	    statement = conn.prepareStatement("UPDATE department " + "SET Name = ? " + "WHERE Id = ?");

	    statement.setString(1, obj.getName());
	    statement.setInt(2, obj.getId());

	    statement.executeUpdate();

	} catch (SQLException e) {
	    throw new DBException(e.getMessage());
	} finally {
	    DB.closeStatement(statement);
	}

    }

    @Override
    public void deleteById(Integer id) {
	PreparedStatement statement = null;

	try {
	    statement = conn.prepareStatement("DELETE FROM department " + "WHERE Id = ?");
	    statement.setInt(1, id);

	    int rows = statement.executeUpdate();

	    if (rows == 0) {
		throw new DBException("The given ID doesn't exist!");
	    }

	} catch (SQLException e) {
	    throw new DBException(e.getMessage());
	} finally {
	    DB.closeStatement(statement);
	}

    }

    @Override
    public Department findById(Integer id) {
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	
	try {
	    statement = conn.prepareStatement(
		    	"SELECT * "
		    	+ "FROM department "
		    	+ "WHERE department.Id = ?");
	    
	    statement.setInt(1, id);
	    
	    resultSet = statement.executeQuery();
	    
	    if(resultSet.next()) {
		Department department = new Department(resultSet.getInt("Id"), resultSet.getString("Name"));
		
		return department;
	    } else {
		throw new DBException("Id not found");
	    }
	    
	} catch (SQLException e) {
	    throw new DBException(e.getMessage());
	} finally {
	    DB.closeStatement(statement);
	    DB.closeResultSet(resultSet);
	}
    }

    @Override
    public List<Department> findAll() {
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	
	try {
	    statement = conn.prepareStatement(
		"SELECT * "
		+ "FROM department "
		+ "ORDER BY Id");
	    
	    resultSet = statement.executeQuery();
	    
	    List<Department> list = new ArrayList<>();
	    
	    while(resultSet.next()) {
		Department department = new Department(resultSet.getInt("Id"), resultSet.getString("Name"));
		list.add(department);
	    }
	    
	    return list;
	} catch (SQLException e) {
	    throw new DBException(e.getMessage());
	} finally {
	    DB.closeStatement(statement);
	    DB.closeResultSet(resultSet);
	}
    }

}
