package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;


public class _6 {
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input6.txt"));
            
        final String line = fileReader.readLine();
        
        for (int j = 0; j < line.length()-4; j++) {
            Set<Character> set = new HashSet<>();
            set.add(line.charAt(j));
            set.add(line.charAt(j+1));
            set.add(line.charAt(j+2));
            set.add(line.charAt(j+3));
            if (set.size() == 4) {
                System.out.println(j+4);
                break;
            }
        }
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input6.txt"));
    
        final String line = fileReader.readLine();
    
        for (int j = 0; j < line.length()-14; j++) {
            Set<Character> set = new HashSet<>();
            
            for (int k = 0; k < 14; k++) {
                set.add(line.charAt(j+k));
            }
            
            if (set.size() == 14) {
                System.out.println(j+14);
                break;
            }
        }
        
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
