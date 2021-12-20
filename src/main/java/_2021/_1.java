package _2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _1 {
    
    private static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input1.txt")));
        
        Integer previousNumber = null;
        int count = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            int num = Integer.parseInt(line);
            
            if (previousNumber != null && num > previousNumber) {
                count++;
            }
            
            previousNumber = num;
        }
        
        System.out.println(count);
    }
    
    private static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input1.txt")));
        
        List<Integer> allNumbers = new ArrayList<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            int num = Integer.parseInt(line);
            
            allNumbers.add(num);
        }
        
        int count = 0;
        
        for (int j = 3; j < allNumbers.size(); j++) {
            if (allNumbers.get(j - 2) + allNumbers.get(j - 1) + allNumbers.get(j)
                    > allNumbers.get(j - 3) + allNumbers.get(j - 2) + allNumbers.get(j - 1)) {
                count++;
            }
        }
        
        System.out.println(count);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}