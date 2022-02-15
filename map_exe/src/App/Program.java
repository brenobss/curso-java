package App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter file full path: ");
		String path = sc.nextLine();

		Map<String, Integer> results = new LinkedHashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();
			while (line != null) {
				String[] candidateVotes = line.split(",");
				
				String name = candidateVotes[0];
				int votes = Integer.parseInt(candidateVotes[1]);
				int votesTotal = votes;
				if(results.containsKey(name)) {
					votesTotal = results.get(name) + votes;
				}
				
				results.put(name, votesTotal);
				
				line = br.readLine();
			}
			
			/*
			 * for (Map.Entry<String, Integer> entry : results.entrySet()) { String key =
			 * entry.getKey(); Integer val = entry.getValue();
			 * 
			 * System.out.println(key + ": " + val);
			 * 
			 * }
			 */
			
			for(String key : results.keySet()) {
				System.out.println(key + ": " + results.get(key));
			}

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		sc.close();
	}

}
