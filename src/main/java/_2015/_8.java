package _2015;

import java.io.BufferedReader;
import java.io.FileReader;


public class _8 {
    
    private static void a() throws Exception {
    
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input8.txt"));
    
        int total = 0;
        
        while (true) {
    
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
        
            String lineToCheck = line.substring(1, line.length()-1);
            
            String newLine = lineToCheck.replaceAll("\\\\\\\\", "").replaceAll("\\\\\"", "");
            
            int backslashAndQuoteCount = (lineToCheck.length() - newLine.length()) / 2;
            
            int hexCount = newLine.length() - newLine.replaceAll("\\\\x", "x").length();
            
            total += 2 + hexCount * 3 + backslashAndQuoteCount;
        }
        
        System.out.println(total);
        
    }
    
    private static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input8.txt"));
    
        int total = 0;
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            String newLine = line.replaceAll("\\\\", "").replaceAll("\"", "");
        
            int backslashAndQuoteCount = line.length() - newLine.length();
        
            total += 2 + backslashAndQuoteCount;
        }
        
        System.out.println(total);
    }
    
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}