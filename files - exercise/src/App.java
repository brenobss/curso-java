import entities.Product;

import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<Product> list = new ArrayList<>();
        System.out.println("Enter file path: ");
        String strPath = sc.nextLine();
        File path = new File(strPath);
        String pathFolder = path.getParent();
        boolean success = new File(pathFolder + "/out").mkdir();
        String targetFileStr = pathFolder + "/out/summary.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(strPath))){

            String line = br.readLine();
            while(line != null){
                String[] fields = line.split(",");
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int qtd = Integer.parseInt(fields[2]);
                list.add(new Product(name, price, qtd));
                line = br.readLine();
            }

            try(BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){

                for(Product item : list){
                    System.out.println(item.getName());
                    System.out.println(item.getPrice());
                    System.out.println(item.getQtd());
                    System.out.println(String.format("%.2f", item.totalPrice()));
                    bw.write(item.getName() + "," + String.format("%.2f", item.totalPrice()));
                    bw.newLine();
                }

                System.out.println(targetFileStr + " CREATED!");
            }
            catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }


        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
