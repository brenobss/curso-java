package Application;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Program {
	public static void main(String[] args) {
		
		/* version one
		Scanner sc = new Scanner(System.in);
		Set<Integer> set = new HashSet<Integer>();
		
		System.out.print("How many students for course A? ");
		int studentsANumber = sc.nextInt();
		for (int i = 0; i < studentsANumber; i++) {
			set.add(sc.nextInt());
		}
		
		System.out.print("How many students for course B? ");
		int studentsBNumber = sc.nextInt();
		for (int i = 0; i < studentsBNumber; i++) {
			set.add(sc.nextInt());
		}
		
		System.out.print("How many students for course C? ");
		int studentsCNumber = sc.nextInt();
		for (int i = 0; i < studentsCNumber; i++) {
			set.add(sc.nextInt());
		}
		
		sc.close();
		
		System.out.println("Total students: " + set.size());
		
		*/
		
		// Version two best practices
		
		Scanner sc = new Scanner(System.in);
		
		Set<Integer> codStudantsCourseA = new HashSet<Integer>();
		Set<Integer> codStudantsCourseB = new HashSet<Integer>();
		Set<Integer> codStudantsCourseC = new HashSet<Integer>();
		
		System.out.print("How many students for course A? ");
		int n = sc.nextInt();
		for (int i = 0; i < n; i++) {
			codStudantsCourseA.add(sc.nextInt());
		}
		
		System.out.print("How many students for course B? ");
		n = sc.nextInt();
		for (int i = 0; i < n; i++) {
			codStudantsCourseB.add(sc.nextInt());
		}
		
		System.out.print("How many students for course C? ");
		n = sc.nextInt();
		for (int i = 0; i < n; i++) {
			codStudantsCourseC.add(sc.nextInt());
		}
		
		sc.close();
		
		Set<Integer> total = new HashSet<Integer>();
		
		total.addAll(codStudantsCourseA);
		total.addAll(codStudantsCourseB);
		total.addAll(codStudantsCourseC);
		
		
		System.out.println("Total students: " + total.size());
		
		
	}
}
