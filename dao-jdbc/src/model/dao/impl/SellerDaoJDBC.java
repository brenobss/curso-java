package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
    
    private Connection conn;
    
    public SellerDaoJDBC(Connection conn) {
	this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
	PreparedStatement statement = null;
	
	try {
	    statement = conn.prepareStatement(
			"INSERT INTO seller "
			+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
			+ "VALUES "
			+ "(?, ?, ?, ?, ?)", 
			Statement.RETURN_GENERATED_KEYS);
	    
	    statement.setString(1, obj.getName());
	    statement.setString(2, obj.getEmail());
	    statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
	    statement.setDouble(4, obj.getBaseSalary());
	    statement.setInt(5, obj.getDepartment().getId());
	    
	    int rowsAffected = statement.executeUpdate();
	    
	    if(rowsAffected > 0) {
		ResultSet resultSet = statement.getGeneratedKeys();
		if(resultSet.next()) {
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
    public void update(Seller obj) {
	PreparedStatement statement = null;
	
	try {
	    statement = conn.prepareStatement(
			"UPDATE seller "
			+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
			+ "WHERE Id = ?");
	    
	    statement.setString(1, obj.getName());
	    statement.setString(2, obj.getEmail());
	    statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
	    statement.setDouble(4, obj.getBaseSalary());
	    statement.setInt(5, obj.getDepartment().getId());
	    statement.setInt(6, obj.getId());
	    
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
	    statement = conn.prepareStatement("DELETE FROM seller "
	    	+ "WHERE Id = ?");
	    statement.setInt(1, id);
	    
	    int rows = statement.executeUpdate();
	    
	    if(rows == 0) {
		throw new DBException("The given ID doesn't exist!");
	    }
	    
	} catch (SQLException e) {
	    throw new DBException(e.getMessage());
	} finally {
	    DB.closeStatement(statement);
	}
	
    }

    @Override
    public Seller findById(Integer id) {
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	
	try {
	    statement = conn.prepareStatement(
		"SELECT seller.*,department.Name as DepName "
	    	+ "FROM seller INNER JOIN department "
	    	+ "ON seller.DepartmentId = department.Id "
	    	+ "WHERE seller.Id = ?");
	    
	    statement.setInt(1, id);
	    resultSet = statement.executeQuery();
	    
	    if(resultSet.next()) {
		Department dep = instantiationDepartment(resultSet);
		
		Seller seller = instantiationSeller(resultSet, dep);
		
		return seller;
	    }
	    
	    return null;
	    
	} catch (SQLException e) {
	    throw new DBException(e.getMessage());
	} finally {
	    DB.closeStatement(statement);
	    DB.closeResultSet(resultSet);
	}
	
    }

    private Seller instantiationSeller(ResultSet resultSet, Department dep) throws SQLException {
	Seller seller = new Seller();
	seller.setId(resultSet.getInt("Id"));
	seller.setName(resultSet.getString("Name"));
	seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
	seller.setBirthDate(resultSet.getDate("BirthDate"));
	seller.setEmail(resultSet.getString("Email"));
	seller.setDepartment(dep);
	return seller;
    }

    private Department instantiationDepartment(ResultSet resultSet) throws SQLException {
	Department dep = new Department();
	dep.setId(resultSet.getInt("DepartmentId"));
	dep.setName(resultSet.getString("DepName"));
	return dep;
    }

    @Override
    public List<Seller> findAll() {
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	
	try {
	    statement = conn.prepareStatement(
		"SELECT seller.*,department.Name as DepName "
		+ "FROM seller INNER JOIN department "
		+ "ON seller.DepartmentId = department.Id "
		+ "ORDER BY Name");
	    
	    resultSet = statement.executeQuery();
	    
	    List<Seller> listSellers = new ArrayList<>();
	    Map<Integer, Department> mapDepartments = new HashMap<>();
	    
	    while(resultSet.next()) {
		
		Department dep = mapDepartments.get(resultSet.getInt("DepartmentId"));
		
		if(dep == null) {
		    dep = instantiationDepartment(resultSet);
		    mapDepartments.put(dep.getId(), dep);
		}
		
		Seller seller = instantiationSeller(resultSet, dep);
		
		listSellers.add(seller);
	
	    }
	    
	    return listSellers;
	    
	} catch (SQLException e) {
	    throw new DBException(e.getMessage());
	} finally {
	    DB.closeStatement(statement);
	    DB.closeResultSet(resultSet);
	}
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	
	try {
	    statement = conn.prepareStatement(
		"SELECT seller.*,department.Name as DepName "
		+ "FROM seller INNER JOIN department "
		+ "ON seller.DepartmentId = department.Id "
		+ "WHERE DepartmentId = ? "
		+ "ORDER BY Name");
	    
	    statement.setInt(1, department.getId());
	    resultSet = statement.executeQuery();
	    
	    List<Seller> listSellers = new ArrayList<>();
	    Map<Integer, Department> mapDepartments = new HashMap<>();
	    
	    while(resultSet.next()) {
		
		Department dep = mapDepartments.get(resultSet.getInt("DepartmentId"));
		
		if(dep == null) {
		    dep = instantiationDepartment(resultSet);
		    mapDepartments.put(dep.getId(), dep);
		}
		
		Seller seller = instantiationSeller(resultSet, dep);
		
		listSellers.add(seller);
	
	    }
	    
	    return listSellers;
	    
	} catch (SQLException e) {
	    throw new DBException(e.getMessage());
	} finally {
	    DB.closeStatement(statement);
	    DB.closeResultSet(resultSet);
	}
    }

}
