package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;


public class _4 {
    
    private static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input4.txt"));
        
        final String line = fileReader.readLine();
    
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        int counter = 0;
        
        while (true) {
            String toHash = line + counter;
            
            md.update(toHash.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            
            if (myHash.startsWith("00000")) {
                break;
            }
            
            counter++;
        }
    
        System.out.println(counter);
        
    }
    
    private static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input4.txt"));
    
        final String line = fileReader.readLine();
    
        MessageDigest md = MessageDigest.getInstance("MD5");
    
        int counter = 0;
    
        while (true) {
            String toHash = line + counter;
        
            md.update(toHash.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        
            if (myHash.startsWith("000000")) {
                break;
            }
        
            counter++;
        }
    
        System.out.println(counter);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}