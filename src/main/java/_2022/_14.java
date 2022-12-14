package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class _14 {
    
    private static class Coord {
    
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    
        int x;
        int y;
    }
    
    private static int[][] setupBoard() throws IOException {
        int[][] board = new int[1000][500];
        
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input14.txt"));
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            List<Coord> coords = new ArrayList<>();
            
            String[] split = line.split(" -> ");
            
            for (int j = 0; j < split.length; j++) {
                String[] split2 = split[j].split(",");
                int x = Integer.parseInt(split2[0]);
                int y = Integer.parseInt(split2[1]);
                
                Coord coord = new Coord(x, y);
                coords.add(coord);
            }
            
            for (int j = 0; j < coords.size() - 1; j++) {
                Coord coord1 = coords.get(j);
                Coord coord2 = coords.get(j+1);
                
                if (coord1.x == coord2.x) {
                    int minY = Math.min(coord1.y, coord2.y);
                    int maxY = Math.max(coord1.y, coord2.y);
                    
                    for (int k = minY; k <= maxY; k++) {
                        board[coord1.x][k] = 1;
                    }
                } else if (coord1.y == coord2.y) {
                    int minX = Math.min(coord1.x, coord2.x);
                    int maxX = Math.max(coord1.x, coord2.x);
                    
                    for (int k = minX; k <= maxX; k++) {
                        board[k][coord1.y] = 1;
                    }
                } else {
                    throw new RuntimeException("Diagonal not expected");
                }
            }
        }
        
        return board;
    }
    
    public static void a() throws Exception {
    
        int[][] board = setupBoard();
    
        /*for (int j = 0; j < 50; j ++) {
            for (int k = 400; k < 600; k ++) {
                System.out.print(board[k][j]);
            }
            System.out.println();
        }*/
    
        int sandCount = 0;
        boolean finished = false;
        
        while (!finished) {
            Coord sand = new Coord(500, 0);
            
            while (!finished) {
        
                if (sand.y > 400) {
                    finished = true;
                    break;
                }
        
                if (board[sand.x][sand.y+1] == 0) {
                    sand.y++;
                } else {
                    if (board[sand.x-1][sand.y+1] == 0) {
                        sand.y++;
                        sand.x--;
                    } else if (board[sand.x+1][sand.y+1] == 0) {
                        sand.y++;
                        sand.x++;
                    } else {
                        // Came to rest
                        board[sand.x][sand.y] = 1;
                        break;
                    }
                }
        
            }
            
            sandCount++;
        }
        
        System.out.println(sandCount-1);
        
    }
    
    public static void b() throws Exception {
    
        int[][] board = setupBoard();
    
        /*for (int j = 0; j < 50; j ++) {
            for (int k = 400; k < 600; k ++) {
                System.out.print(board[k][j]);
            }
            System.out.println();
        }*/
    
        int maxY = 0;
        
        for (int j = 0; j < 500; j ++) {
            for (int k = 0; k < 1000; k ++) {
                if (board[k][j] == 1) {
                    maxY = j;
                }
            }
        }
    
        //System.out.println(maxY);
        
        int floorHeight = maxY + 2;
    
        int sandCount = 0;
        boolean finished = false;
    
        while (!finished) {
            Coord sand = new Coord(500, 0);
        
            while (!finished) {
                
                // Came to rest because of floor
                if (sand.y + 1 == floorHeight) {
                    board[sand.x][sand.y] = 2;
                    break;
                }
                
                if (board[sand.x][sand.y+1] == 0) {
                    sand.y++;
                } else {
                    if (board[sand.x-1][sand.y+1] == 0) {
                        sand.y++;
                        sand.x--;
                    } else if (board[sand.x+1][sand.y+1] == 0) {
                        sand.y++;
                        sand.x++;
                    } else {
                        // Came to rest
                        board[sand.x][sand.y] = 2;
    
                        if (sand.x == 500 && sand.y == 0) {
                            finished = true;
                        }
                        
                        break;
                    }
                }
            
            }
        
            sandCount++;
        }
    
        /*for (int j = 0; j < 50; j ++) {
            for (int k = 400; k < 600; k ++) {
                System.out.print(board[k][j]);
            }
            System.out.println();
        }*/
    
        System.out.println(sandCount);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
