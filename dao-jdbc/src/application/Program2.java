package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

    public static void main(String[] args) {
	DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
	
	System.out.println("==== Department test findById ====");
	Department booksDepartment = departmentDao.findById(6);
	System.out.println("Department id = " + booksDepartment.getId() + " Name = " + booksDepartment.getName());
	
	System.out.println("==== Department test findAll ====");
	List<Department> list = departmentDao.findAll();
	for(Department dep : list) {
	    System.out.println(dep);
	}
	
	System.out.println("==== Department test insert ====");
	Department department = new Department(null, "Movies");
	departmentDao.insert(department);
	System.out.println("Inserted! New id = " + department.getId() + " New department = " + department.getName());
	
	System.out.println("==== Department test update ====");
	department.setName("Food");
	departmentDao.update(department);
	System.out.println("Updated department " + department);
	
	System.out.println("==== Department test delete ====");
	departmentDao.deleteById(6);
	System.out.println("Department deleted!");
	
    }

}
