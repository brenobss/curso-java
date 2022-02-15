package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Employee;

public class Main {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Input file path: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Employee> list = new ArrayList<>();
			
			String line = br.readLine();
			
			while(line != null) {
				String[] field = line.split(",");
				list.add(new Employee(field[0], field[1], Double.parseDouble(field[2])));
				line = br.readLine();
			}
			
			System.out.print("Enter salary: ");
			double salary = sc.nextDouble();
			
			List<String> emails = list.stream()
					.filter(s -> s.getSalary() > salary)
					.map(e -> e.getEmail())
					.sorted()
					.toList();
			
			System.out.println("Email of people whose salary is more than " + String.format("%.2f", salary) + ":");
			emails.forEach(System.out::println);
			
//			First method			
//			List<Double> salaryCharM = list.stream()
//					.filter(p -> p.getName().charAt(0) == 'M')
//					.map(p -> p.getSalary())
//					.toList();
//			
//			double sumSalaryCharM = 0.0;
//			
//			for(double s : salaryCharM) {
//				sumSalaryCharM += s;
//			}
			
//			Best method
			double sumSalaryCharM = list.stream()
					.filter(p -> p.getName().charAt(0) == 'M')
					.map(p -> p.getSalary())
					.reduce(0.0, (x,y) -> x + y);
			
			
			System.out.println("Sum of salary of people whose name starts with 'M': " + String.format("%.2f", sumSalaryCharM));
			
			
		} catch (IOException e) {
			System.out.println(e);
		}
		
		
		sc.close();

	}

}
