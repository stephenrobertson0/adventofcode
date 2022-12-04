package _2022;

import java.io.BufferedReader;
import java.io.FileReader;


public class _4 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input4.txt"));
        
        long count = 0;
        
        while (true) {
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
            
            String splitPairs[] = line.split(",");
            
            String pair1 = splitPairs[0];
            String pair2 = splitPairs[1];
    
            String splitPair1[] = pair1.split("-");
            
            int pair1Min = Integer.parseInt(splitPair1[0]);
            int pair1Max = Integer.parseInt(splitPair1[1]);
    
            String splitPair2[] = pair2.split("-");
    
            int pair2Min = Integer.parseInt(splitPair2[0]);
            int pair2Max = Integer.parseInt(splitPair2[1]);
            
            if ((pair1Min <= pair2Min && pair1Max >= pair2Max) || (pair1Min >= pair2Min && pair1Max <= pair2Max)) {
                count++;
            }
        }
        
        System.out.println(count);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input4.txt"));
    
        long count = 0;
    
        while (true) {
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
    
            String splitPairs[] = line.split(",");
    
            String pair1 = splitPairs[0];
            String pair2 = splitPairs[1];
    
            String splitPair1[] = pair1.split("-");
    
            int pair1Min = Integer.parseInt(splitPair1[0]);
            int pair1Max = Integer.parseInt(splitPair1[1]);
    
            String splitPair2[] = pair2.split("-");
    
            int pair2Min = Integer.parseInt(splitPair2[0]);
            int pair2Max = Integer.parseInt(splitPair2[1]);
    
            if ((pair1Min <= pair2Max && pair1Max >= pair2Min)) {
                count++;
            }
        
        }
    
        System.out.println(count);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
