package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.IntStream;


public class _5 {
    
    private static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input5.txt"));
        
        int count = 0;
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            if (line.contains("ab") || line.contains("cd") || line.contains("pq") || line.contains("xy")) {
                continue;
            }
            
            if (Arrays.stream("aeiou".chars().toArray())
                    .map(v -> Arrays.stream(line.chars().toArray()).map(x -> x == v ? 1 : 0).sum())
                    .sum() < 3) {
                continue;
            }
            
            if (!IntStream.range(0, 26).map(v -> 'a' + v).anyMatch(v -> line.contains("" + (char)v + (char)v))) {
                continue;
            }
            
            count++;
        }
        
        System.out.println(count);
    }
    
    private static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input5.txt"));
    
        int count = 0;
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            boolean anyMatch = false;
            
            for (int j = 0; j < line.length() - 1; j++) {
                String str1 = line.substring(0, j);
                String str2 = line.substring(j+2);
                
                String toCheck = line.substring(j, j+2);
                
                if ((str1.contains(toCheck) || str2.contains(toCheck))) {
                    anyMatch = true;
                    break;
                }
            }
            
            if (!anyMatch) {
                continue;
            }
            
            anyMatch = false;
    
            for (int j = 0; j < line.length() - 2; j++) {
                if (line.charAt(j) == line.charAt(j+2)) {
                    anyMatch = true;
                    break;
                }
            }
    
            if (!anyMatch) {
                continue;
            }
        
            count++;
        }
    
        System.out.println(count);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}