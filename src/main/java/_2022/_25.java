package _2022;

import java.io.BufferedReader;
import java.io.FileReader;


public class _25 {
    
    static char[] chars = new char[]{'=','-','0','1','2'};
    
    private static long snafuToNum(String snafu) {
    
        long total = 0;
        
        for (int j = 0; j < snafu.length(); j++) {
            char ch = snafu.charAt(snafu.length()-1-j);
            
            int num = 0;
            boolean negative = false;
            
            if (ch == '=') {
                num = 2;
                negative = true;
            } else if (ch == '-') {
                num = 1;
                negative = true;
            } else if (ch == '0') {
                num = 0;
                negative = false;
            } else if (ch == '1') {
                num = 1;
                negative = false;
            } else if (ch == '2') {
                num = 2;
                negative = false;
            }
            
            total += (negative ? -1 : 1) * num * Math.pow(5, j);
        }
        
        return total;
        
    }
    
    private static String numToSnafu(long num) {
        
        int[] digits = new int[30];
        
        for (int j = 0; j < 30; j ++) {
            int power = 30-j-1;
            
            long number = (long)Math.pow(5, power);
            
            long div = num / number;
            
            digits[j] = (int)div;
            
            num = num % number;
        }
    
        for (int j = 0; j < 30; j ++) {
            int power = 30-j-1;
        
            if (digits[power] > 2) {
                digits[power - 1] +=1;
                digits[power] -= 5;
            }
        }
    
        String result = "";
        
        boolean begin = false;
        
        for (int j = 0; j < 30; j ++) {
            
            if (digits[j] != 0) {
                begin = true;
            }
            
            if (begin) {
                result += chars[digits[j] + 2];
            }
        }
        
        return result;
    }
    
    public static void a() throws Exception {
    
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input25.txt"));
    
        long total = 0;
        
        while (true) {
    
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
            
            total += snafuToNum(line);
        }
    
        System.out.println(numToSnafu(total));
        
    }
    
    public static void b() throws Exception {
    
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
