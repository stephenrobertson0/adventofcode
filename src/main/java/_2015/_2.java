package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class _2 {
    
    private static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input2.txt"));
        
        int total = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String[] split = line.split("x");
            int side1 = Integer.parseInt(split[0]);
            int side2 = Integer.parseInt(split[1]);
            int side3 = Integer.parseInt(split[2]);
            
            int area1 = side1*side2;
            int area2 = side2*side3;
            int area3 = side1*side3;
            
            int totalArea = area1*2 + area2*2 + area3*2 + Math.min(Math.min(area1, area2), area3);
    
            total += totalArea;
        }
        
        System.out.println(total);
    }
    
    private static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input2.txt"));
    
        int total = 0;
    
        while (true) {
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            String[] split = line.split("x");
            int side1 = Integer.parseInt(split[0]);
            int side2 = Integer.parseInt(split[1]);
            int side3 = Integer.parseInt(split[2]);
    
            List<Integer> sides = Arrays.asList(side1, side2, side3);
            
            sides.sort(Comparator.comparingInt(x -> x));
    
            int ribbonTotal = (sides.get(0) + sides.get(1))*2 + side1*side2*side3;
            total += ribbonTotal;
        }
    
        System.out.println(total);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}