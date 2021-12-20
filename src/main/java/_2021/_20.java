package _2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class _20 {
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input20.txt")));
        
        String algorithmLine = fileReader.readLine();
        
        fileReader.readLine();
        
        int size = 100;
        int padding = 10;
        
        int[][] board = new int[size+padding*2][size+padding*2];
        
        int index = padding;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    board[padding+j][index] = 1;
                } else {
                    board[padding+j][index] = 0;
                }
            }
            
            index++;
        }
        
        /*for (int y = 0; y < board.length; y ++) {
            for (int x = 0; x < board[y].length; x ++) {
                System.out.print(board[x][y]);
            }
            System.out.println();
        }*/
        
        for (int j = 0; j < 2; j ++) {
            int[][] newBoard = new int[size+padding*2][size+padding*2];
            
            for (int y = 0; y < board.length-2; y ++) {
                for (int x = 0; x < board[y].length-2; x ++) {
                    
                    int binaryNum = 0;
                    
                    binaryNum += board[x][y] * Math.pow(2, 8);
                    binaryNum += board[x+1][y] * Math.pow(2, 7);
                    binaryNum += board[x+2][y] * Math.pow(2, 6);
                    binaryNum += board[x][y+1] * Math.pow(2, 5);
                    binaryNum += board[x+1][y+1] * Math.pow(2, 4);
                    binaryNum += board[x+2][y+1] * Math.pow(2, 3);
                    binaryNum += board[x][y+2] * Math.pow(2, 2);
                    binaryNum += board[x+1][y+2] * Math.pow(2, 1);
                    binaryNum += board[x+2][y+2] * Math.pow(2, 0);
                    
                    newBoard[x+1][y+1] = algorithmLine.charAt(binaryNum) == '#' ? 1 : 0;
                    
                }
            }
            
            board = newBoard;
        }
        
        int count = 0;
        
        // Shave off fake infinity artifacts -
        
        for (int y = 3; y < board.length-3; y ++) {
            for (int x = 3; x < board[y].length-3; x ++) {
                count += board[x][y];
            }
        }
        
        for (int y = 3; y < board.length-3; y ++) {
            for (int x = 3; x < board[y].length-3; x ++) {
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
        
        System.out.println(count);
        
        //5857 wrong
    }
    
    public static void b() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input20.txt")));
        
        String algorithmLine = fileReader.readLine();
        
        fileReader.readLine();
        
        int size = 100;
        int padding = 200;
        
        int[][] board = new int[size+padding*2][size+padding*2];
        
        int index = padding;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    board[padding+j][index] = 1;
                } else {
                    board[padding+j][index] = 0;
                }
            }
            
            index++;
        }
        
        /*for (int y = 0; y < board.length; y ++) {
            for (int x = 0; x < board[y].length; x ++) {
                System.out.print(board[x][y]);
            }
            System.out.println();
        }*/
        
        for (int j = 0; j < 50; j ++) {
            int[][] newBoard = new int[size+padding*2][size+padding*2];
            
            for (int y = 0; y < board.length-2; y ++) {
                for (int x = 0; x < board[y].length-2; x ++) {
                    
                    int binaryNum = 0;
                    
                    binaryNum += board[x][y] * Math.pow(2, 8);
                    binaryNum += board[x+1][y] * Math.pow(2, 7);
                    binaryNum += board[x+2][y] * Math.pow(2, 6);
                    binaryNum += board[x][y+1] * Math.pow(2, 5);
                    binaryNum += board[x+1][y+1] * Math.pow(2, 4);
                    binaryNum += board[x+2][y+1] * Math.pow(2, 3);
                    binaryNum += board[x][y+2] * Math.pow(2, 2);
                    binaryNum += board[x+1][y+2] * Math.pow(2, 1);
                    binaryNum += board[x+2][y+2] * Math.pow(2, 0);
                    
                    newBoard[x+1][y+1] = algorithmLine.charAt(binaryNum) == '#' ? 1 : 0;
                    
                }
            }
            
            board = newBoard;
        }
        
        int count = 0;
        
        // Shave off fake infinity artifacts -
        
        for (int y = 50; y < board.length-50; y ++) {
            for (int x = 50; x < board[y].length-50; x ++) {
                count += board[x][y];
            }
        }
        
        for (int y = 50; y < board.length-50; y ++) {
            for (int x = 50; x < board[y].length-50; x ++) {
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
        
        System.out.println(count);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}