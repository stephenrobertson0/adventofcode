package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;


public class _11 {
    
    private static void flashBoard(int x, int y, int[][] board, Set<String> alreadyFlashed) {
        
        if (alreadyFlashed.contains(x + "-" + y)) {
            return;
        }
        
        board[x][y]++;
        
        if (board[x][y] > 9) {
            
            alreadyFlashed.add(x + "-" + y);
            
            if (x != 0) {
                flashBoard(x - 1, y, board, alreadyFlashed);
                
                if (y != 0) {
                    flashBoard(x - 1, y - 1, board, alreadyFlashed);
                }
                
                if (y != 9) {
                    flashBoard(x - 1, y + 1, board, alreadyFlashed);
                }
                
            }
            
            if (x != 9) {
                flashBoard(x + 1, y, board, alreadyFlashed);
                
                if (y != 0) {
                    flashBoard(x + 1, y - 1, board, alreadyFlashed);
                }
                
                if (y != 9) {
                    flashBoard(x + 1, y + 1, board, alreadyFlashed);
                }
            }
            
            if (y != 0) {
                flashBoard(x, y - 1, board, alreadyFlashed);
            }
            
            if (y != 9) {
                flashBoard(x, y + 1, board, alreadyFlashed);
            }
        }
        
    }
    
    private static void printBoard(int[][] board) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
    }
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input11.txt")));
        
        int totalFlashes = 0;
        
        int[][] board = new int[10][10];
        
        int k = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            for (int j = 0; j < line.length(); j++) {
                board[j][k] = Integer.parseInt("" + line.charAt(j));
            }
            
            k++;
        }
        
        //Steps
        for (int j = 0; j < 100; j++) {
            
            //printBoard(board);
            
            // Increase energy
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    board[x][y]++;
                }
            }
            
            Set<String> alreadyFlashed = new HashSet<>();
            
            // Flash
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    if (board[x][y] > 9) {
                        flashBoard(x, y, board, alreadyFlashed);
                    }
                }
            }
            
            // Reset to zero
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    if (board[x][y] > 9) {
                        board[x][y] = 0;
                    }
                }
            }
            
            //System.out.println();
            
            //printBoard(board);
            
            totalFlashes += alreadyFlashed.size();
        }
        
        System.out.println(totalFlashes);
        
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input11.txt")));
        
        int[][] board = new int[10][10];
        
        int k = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            for (int j = 0; j < line.length(); j++) {
                board[j][k] = Integer.parseInt("" + line.charAt(j));
            }
            
            k++;
        }
        
        //Steps
        for (int j = 0; j < 10000; j++) {
            
            //printBoard(board);
            
            // Increase energy
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    board[x][y]++;
                }
            }
            
            Set<String> alreadyFlashed = new HashSet<>();
            
            // Flash
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    if (board[x][y] > 9) {
                        flashBoard(x, y, board, alreadyFlashed);
                    }
                }
            }
            
            // Reset to zero
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    if (board[x][y] > 9) {
                        board[x][y] = 0;
                    }
                }
            }
            
            //System.out.println();
            
            //printBoard(board);
            
            if (alreadyFlashed.size() == 100) {
                System.out.println("Step: " + (j + 1));
                break;
            }
            
            // 518 is wrong
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}