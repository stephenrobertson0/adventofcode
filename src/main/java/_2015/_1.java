package _2015;

import java.io.BufferedReader;
import java.io.FileReader;


public class _1 {
    
    private static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input1.txt"));
        
        int count = 0;
        
        final String line = fileReader.readLine();
        
        for (char c : line.toCharArray()) {
            if (c == '(') {
                count ++;
            } else {
                count --;
            }
        }
        
        System.out.println(count);
    }
    
    private static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input1.txt"));
    
        int index = 1;
        int count = 0;
    
        final String line = fileReader.readLine();
    
        for (char c : line.toCharArray()) {
            if (c == '(') {
                count ++;
            } else {
                count --;
            }
            
            if (count < 0) {
                break;
            }
            
            index++;
        }
    
        System.out.println(index);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}