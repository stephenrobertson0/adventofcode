package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class _3 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input3.txt"));
        
        long score = 0;
        
        while (true) {
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
            
            String string1 = line.substring(0, line.length()/2);
            String string2 = line.substring(line.length()/2);
            
            for (char a : string1.toCharArray()) {
                if (string2.contains(""+a)) {
                    
                    // Lowercase
                    if ((int)a > 91) {
                        score += (int)a - 96;
                    } else {
                        score += (int)a - 38;
                    }
                    
                    break;
                }
            }
            
        }
        
        System.out.println(score);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input3.txt"));
    
        long score = 0;
    
        while (true) {
            final String line1 = fileReader.readLine();
        
            if (line1 == null) {
                break;
            }
    
            final String line2 = fileReader.readLine();
            final String line3 = fileReader.readLine();
        
            Set<Character> set1 = new HashSet<>();
            Set<Character> set2 = new HashSet<>();
            Set<Character> set3 = new HashSet<>();
        
            for (char a : line1.toCharArray()) {
                set1.add(a);
            }
    
            for (char a : line2.toCharArray()) {
                set2.add(a);
            }
    
            for (char a : line3.toCharArray()) {
                set3.add(a);
            }
            
            Map<Character, Integer> allCounts = new HashMap<>();
            
            for (char c : set1) {
                if (allCounts.containsKey(c)) {
                    allCounts.put(c, allCounts.get(c) + 1);
                } else {
                    allCounts.put(c, 1);
                }
            }
    
            for (char c : set2) {
                if (allCounts.containsKey(c)) {
                    allCounts.put(c, allCounts.get(c) + 1);
                } else {
                    allCounts.put(c, 1);
                }
            }
    
            for (char c : set3) {
                if (allCounts.containsKey(c)) {
                    allCounts.put(c, allCounts.get(c) + 1);
                } else {
                    allCounts.put(c, 1);
                }
            }
            
            for (Map.Entry<Character, Integer> entry : allCounts.entrySet()) {
                if (entry.getValue() == 3) {
                    char a = entry.getKey();
                    
                    if ((int)a > 91) {
                        score += (int)a - 96;
                    } else {
                        score += (int)a - 38;
                    }
                }
            }
        
        }
    
        System.out.println(score);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
