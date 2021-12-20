package _2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class _2 {
    
    private static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input2.txt")));
    
        int xPos = 0;
        int yPos = 0;
    
        while (true) {
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            final int endIndex = line.indexOf(' ');
            String direction = line.substring(0, endIndex);
            int amount = Integer.parseInt(line.substring(endIndex + 1));
        
            if ("forward".equals(direction)) {
                xPos += amount;
            } else if ("down".equals(direction)) {
                yPos += amount;
            } else if ("up".equals(direction)) {
                yPos -= amount;
            }
        }
    
        System.out.println(xPos * yPos);
    }
    
    private static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input2.txt")));
    
        int xPos = 0;
        int yPos = 0;
        int aim = 0;
    
        while (true) {
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            final int endIndex = line.indexOf(' ');
            String direction = line.substring(0, endIndex);
            int amount = Integer.parseInt(line.substring(endIndex + 1));
        
            if ("forward".equals(direction)) {
                xPos += amount;
                yPos += amount * aim;
            } else if ("down".equals(direction)) {
                aim += amount;
            } else if ("up".equals(direction)) {
                aim -= amount;
            }
        }
    
        System.out.println(xPos * yPos);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}