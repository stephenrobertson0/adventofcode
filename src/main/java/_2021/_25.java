package _2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class _25 {
    
    private static class Pos {
        int x;
        int y;
    
        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    private static class Board {
        
        int xSize = 139;
        int ySize = 137;
     
        // 0 - empty, 1 - east, 2 - south
        int[][] board = new int[xSize][ySize];
        
        public Pos getAdjacentFacingPosition(int x, int y) {
            
            int val = board[x][y];
            
            int newX = x;
            int newY = y;
            
            if (val == 1) {
                newX = x + 1;
                if (newX >= xSize) {
                    newX = 0;
                }
            } else if (val == 2) {
                newY = y + 1;
                if (newY >= ySize) {
                    newY = 0;
                }
            }
            
            return new Pos(newX, newY);
        }
        
        public void moveEast() {
            int[][] newBoard = new int[xSize][ySize];
            
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    // EAST
                    if (board[x][y] == 1) {
                        Pos adj = getAdjacentFacingPosition(x, y);
                        if (board[adj.x][adj.y] == 0) {
                            newBoard[adj.x][adj.y] = 1;
                        } else {
                            newBoard[x][y] = 1;
                        }
                    } else if (board[x][y] == 2) {
                        newBoard[x][y] = board[x][y];
                    }
                }   
            }
            
            board = newBoard;
        }
    
        public void moveSouth() {
            int[][] newBoard = new int[xSize][ySize];
        
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    // SOUTH
                    if (board[x][y] == 2) {
                        Pos adj = getAdjacentFacingPosition(x, y);
                        if (board[adj.x][adj.y] == 0) {
                            newBoard[adj.x][adj.y] = 2;
                        } else {
                            newBoard[x][y] = 2;
                        }
                    } else if (board[x][y] == 1) {
                        newBoard[x][y] = board[x][y];
                    }
                }
            }
        
            board = newBoard;
        }
        
        public String toString() {
            
            String string = "";
            
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    if (board[x][y] == 0) {
                        string += ".";
                    } else if (board[x][y] == 1) {
                        string += ">";
                    } else if (board[x][y] == 2) {
                        string += "v";
                    }
                }
                string += '\n';
            }
            
            return string;
        }
        
        public boolean equals(int[][] other) {
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    if (board[x][y] != other[x][y]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input25.txt")));
        
        Board board = new Board();
        
        int y = 0;
        
        while (true) {
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
    
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '>') {
                    board.board[x][y] = 1;
                } else if (line.charAt(x) == 'v') {
                    board.board[x][y] = 2;
                }
            }
            
            y++;
        }
        
        int count = 0;
        
        while (true) {
            
            int[][] oldBoard = board.board;
            
            board.moveEast();
            board.moveSouth();
    
            count++;
            
            if (board.equals(oldBoard)) {
                break;
            }
        }
    
        System.out.println(count);
        
    }
    
    public static void b() throws Exception {
        
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}