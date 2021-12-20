package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;


public class _7 {
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input7.txt")));
        
        final String line = fileReader.readLine();
        
        List<String> input = Arrays.asList(line.split(","));
        
        int minFuel = Integer.MAX_VALUE;
        
        for (int j = 0; j < 10000; j++) {
            
            int totalFuel = 0;
            
            for (String in : input) {
                int num = Integer.parseInt(in);
                
                totalFuel += Math.abs(num - j);
            }
            
            if (totalFuel < minFuel) {
                minFuel = totalFuel;
            }
        }
        
        System.out.println(minFuel);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input7.txt")));
        
        final String line = fileReader.readLine();
        
        List<String> input = Arrays.asList(line.split(","));
        
        long minFuel = Long.MAX_VALUE;
        
        for (int j = 0; j < 10000; j++) {
            
            long totalFuel = 0;
            
            for (String in : input) {
                int num = Integer.parseInt(in);
                
                totalFuel += fuelCost(Math.abs(num - j));
            }
            
            if (totalFuel < minFuel) {
                minFuel = totalFuel;
            }
        }
        
        System.out.println(minFuel);
    }
    
    private static long fuelCost(int x) {
        long total = 0;
        
        for (int j = 0; j < x; j++) {
            total += j + 1;
        }
        
        return total;
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}