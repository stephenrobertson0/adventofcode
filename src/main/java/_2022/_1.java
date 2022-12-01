package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class _1 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input1.txt"));
        
        long max = 0;
        long current = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null || line.isEmpty()) {
                
                if (current > max) {
                    max = current;
                }
                
                current = 0;
            } else {
                current += Long.parseLong(line);
            }
            
            if (line == null) {
                break;
            }
            
        }
        
        System.out.println(max);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input1.txt"));
        
        List<Long> allTotals = new ArrayList<>();
        long current = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null || line.isEmpty()) {
                
                allTotals.add(current);
                
                current = 0;
            } else {
                current += Long.parseLong(line);
            }
            
            if (line == null) {
                break;
            }
            
        }
        
        Collections.sort(allTotals);
        Collections.reverse(allTotals);
        
        System.out.println(allTotals.get(0) + allTotals.get(1) + allTotals.get(2));
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
