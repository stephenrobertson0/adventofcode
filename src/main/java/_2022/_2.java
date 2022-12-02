package _2022;

import java.io.BufferedReader;
import java.io.FileReader;


public class _2 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input2.txt"));
        
        long score = 0;
        
        while (true) {
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
            
            char start = line.charAt(0);
            char response = line.charAt(2);
            
            if (start == 'A') {
                if (response == 'X') {
                    score += 1 + 3;
                } else if (response == 'Y') {
                    score += 2 + 6;
                } else if (response == 'Z') {
                    score += 3 + 0;
                }
            } else if (start == 'B') {
                if (response == 'X') {
                    score += 1 + 0;
                } else if (response == 'Y') {
                    score += 2 + 3;
                } else if (response == 'Z') {
                    score += 3 + 6;
                }
            } else if (start == 'C') {
                if (response == 'X') {
                    score += 1 + 6;
                } else if (response == 'Y') {
                    score += 2 + 0;
                } else if (response == 'Z') {
                    score += 3 + 3;
                }
            }
            
        }
        
        System.out.println(score);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input2.txt"));
    
        long score = 0;
    
        while (true) {
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            char start = line.charAt(0);
            char result = line.charAt(2);
        
            if (start == 'A') {
                if (result == 'X') {
                    score += 3 + 0;
                } else if (result == 'Y') {
                    score += 1 + 3;
                } else if (result == 'Z') {
                    score += 2 + 6;
                }
            } else if (start == 'B') {
                if (result == 'X') {
                    score += 1 + 0;
                } else if (result == 'Y') {
                    score += 2 + 3;
                } else if (result == 'Z') {
                    score += 3 + 6;
                }
            } else if (start == 'C') {
                if (result == 'X') {
                    score += 2 + 0;
                } else if (result == 'Y') {
                    score += 3 + 3;
                } else if (result == 'Z') {
                    score += 1 + 6;
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
