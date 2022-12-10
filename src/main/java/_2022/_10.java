package _2022;

import java.io.BufferedReader;
import java.io.FileReader;


public class _10 {
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input10.txt"));
    
        int cycle = 0;
        int currentValue = 1;
        int totalScore = 0;
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String[] split = line.split(" ");
            
            if (split.length == 1) {
                cycle++;
                
                if ((cycle + 20) % 40 == 0 && cycle < 250) {
                    totalScore += (currentValue * cycle);
                }
            } else {
    
                cycle++;
    
                if ((cycle + 20) % 40 == 0 && cycle < 250) {
                    totalScore += (currentValue * cycle);
                }
                
                cycle++;
    
                if ((cycle + 20) % 40 == 0 && cycle < 250) {
                    totalScore += (currentValue * cycle);
                }
                
                int add = Integer.parseInt(split[1]);
                
                currentValue += add;
            }
        }
    
        System.out.println(totalScore);
        
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input10.txt"));
    
        int cycle = 0;
        int currentValue = 1;
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            String[] split = line.split(" ");
        
            if (split.length == 1) {
                cycle++;
                
                if (Math.abs(cycle - currentValue-1) <= 1) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
    
                if (cycle % 40 == 0) {
                    cycle = 0;
                    System.out.println();
                }
            } else {
            
                cycle++;
                
                if (Math.abs(cycle - currentValue-1) <= 1) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
    
                if (cycle % 40 == 0) {
                    cycle = 0;
                    System.out.println();
                }
            
                cycle++;
                
                if (Math.abs(cycle - currentValue-1) <= 1) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
    
                if (cycle % 40 == 0) {
                    cycle = 0;
                    System.out.println();
                }
            
                int add = Integer.parseInt(split[1]);
            
                currentValue += add;
            }
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
