package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;


public class _8 {
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input8.txt"));
        
        int[][] board = new int[99][99];
        
        int count = 0;
        
        while (true) {
        
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
            
            for (int j = 0; j < line.length(); j++) {
                board[j][count] = Integer.parseInt(""+line.charAt(j));
            }
            
            count++;
        }
    
        Set<String> allVisible = new HashSet<>();
        
        for (int y = 0; y < 99; y++) {
    
            // Left to right
            int max = -1;
            
            for (int x = 0; x < 99; x++) {
                if (board[x][y] > max) {
                    allVisible.add(x + "-" + y);
                    max = board[x][y];
                }
            }
    
            // Right to left
            max = -1;
    
            for (int x = 98; x >= 0; x--) {
                if (board[x][y] > max) {
                    allVisible.add(x + "-" + y);
                    max = board[x][y];
                }
            }
        }
    
        for (int x = 0; x < 99; x++) {
        
            // Top to bottom
            int max = -1;
        
            for (int y = 0; y < 99; y++) {
                if (board[x][y] > max) {
                    allVisible.add(x + "-" + y);
                    max = board[x][y];
                }
            }
        
            // Bottom to top
            max = -1;
        
            for (int y = 98; y >= 0; y--) {
                if (board[x][y] > max) {
                    allVisible.add(x + "-" + y);
                    max = board[x][y];
                }
            }
        }
    
        System.out.println(allVisible.size());
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input8.txt"));
    
        int s = 99;
        
        int[][] board = new int[s][s];
    
        int count = 0;
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            for (int j = 0; j < line.length(); j++) {
                board[j][count] = Integer.parseInt(""+line.charAt(j));
            }
        
            count++;
        }
        
        int maxScore = 0;
        
        for (int y = 0; y < s; y++) {
        
            for (int x = 0; x < s; x++) {
                
                int boardValue = board[x][y];
                
                int score1 = 0;
                
                for (int j = y+1; j < s; j ++) {
                    score1++;
                    if (board[x][j] >= boardValue) {
                        break;
                    }
                }
    
                int score2 = 0;
    
                for (int j = y-1; j >= 0; j --) {
                    score2++;
                    if (board[x][j] >= boardValue) {
                        break;
                    }
                }
    
                int score3 = 0;
    
                for (int j = x+1; j < s; j ++) {
                    score3++;
                    if (board[j][y] >= boardValue) {
                        break;
                    }
                }
    
                int score4 = 0;
    
                for (int j = x-1; j >= 0; j --) {
                    score4++;
                    if (board[j][y] >= boardValue) {
                        break;
                    }
                }
                
                int totalScore = score1*score2*score3*score4;
    
                //System.out.println("Scores for " + x + " " + y + " are " + score1 + " " + score2 + " " + score3 + " " + score4);
                
                if (totalScore > maxScore) {
                    maxScore = totalScore;
                }
            }
            
        }
    
        System.out.println(maxScore);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
