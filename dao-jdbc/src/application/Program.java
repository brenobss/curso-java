package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
    //Eu instancio objetos através do DaoFactory

	public static void main(String[] args) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== Seller Test 1 findById ===");
		Seller seller = sellerDao.findById(4);
		System.out.println(seller);
		
		System.out.println("\n=== Seller Test 2 findByDepartment ===");
		Department dep = new Department(4, null);
		List<Seller> list = sellerDao.findByDepartment(dep);
		for(Seller obj : list) {
		    System.out.println(obj);
		}
		
		System.out.println("\n=== Seller Test 3 findAll ===");
		list = sellerDao.findAll();
		for(Seller obj : list) {
		    System.out.println(obj);
		}
		
		System.out.println("\n=== Seller Test 4 insert ===");
		Seller newSeller = new Seller(null, "Greg", "greg@email.com", new Date(), 4000.00, dep);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());
		
		System.out.println("\n=== Seller Test 5 update ===");
		seller = sellerDao.findById(1);
		seller.setName("Maria Johnson");
		sellerDao.update(seller);
		System.out.println("Updated!");
		System.out.println(seller);
		
		System.out.println("\n=== Seller Test 6 delete ===");
		sellerDao.deleteById(90);
		System.out.println("Deleted!");
	}

}










