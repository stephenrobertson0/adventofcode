package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class _15_incorrect {
    
    private static int getMinPath(int x, int y, int[][] board, Map<String, Integer> cache, int size) {
        
        if (cache.containsKey(x + "-" + y)) {
            return cache.get(x + "-" + y);
        }
        
        if (x == size - 1 && y == size - 1) {
            return board[x][y];
        }
        
        // Don't count first position
        int risk = x == 0 && y == 0 ? 0 : board[x][y];
        
        if (y == size - 1) {
            return risk + getMinPath(x + 1, y, board, cache, size);
        }
        
        if (x == size - 1) {
            return risk + getMinPath(x, y + 1, board, cache, size);
        }
        
        int path1 = getMinPath(x + 1, y, board, cache, size);
        int path2 = getMinPath(x, y + 1, board, cache, size);
        
        if (path1 > path2) {
            return risk + path2;
        } else {
            return risk + path1;
        }
    }
    
    private static void constructMinimumPathCache(int[][] board, Map<String, Integer> cache, int size) {
        
        for (int reducingPosition = size - 1; reducingPosition >= 0; reducingPosition--) {
            
            for (int j = reducingPosition; j < size; j++) {
                
                cache.put(reducingPosition + "-" + j, getMinPath(reducingPosition, j, board, cache, size));
                cache.put(j + "-" + reducingPosition, getMinPath(j, reducingPosition, board, cache, size));
                
            }
        }
        
    }
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input15.txt")));
        
        int size = 100;
        
        int[][] board = new int[size][size];
        
        int yPos = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            for (int x = 0; x < line.length(); x++) {
                board[x][yPos] = Integer.parseInt("" + line.charAt(x));
            }
            
            yPos++;
        }
        
        Map<String, Integer> cache = new HashMap<>();
        
        constructMinimumPathCache(board, cache, size);
        
        System.out.println(cache.get("0-0"));
        
        // 686 - Wrong
        // 685 - Correct
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input15.txt")));
        
        int size = 500;
        
        int[][] board = new int[size][size];
        
        int yPos = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            for (int x = 0; x < line.length(); x++) {
                board[x][yPos] = Integer.parseInt("" + line.charAt(x));
            }
            
            yPos++;
        }
        
        // Complete board
        
        for (int x = size / 5; x < size; x++) {
            for (int y = 0; y < size / 5; y++) {
                int num = board[x - size / 5][y] + 1;
                
                if (num > 9) {
                    num = 1;
                }
                
                board[x][y] = num;
            }
        }
        
        for (int x = 0; x < size; x++) {
            for (int y = size / 5; y < size; y++) {
                int num = board[x][y - size / 5] + 1;
                
                if (num > 9) {
                    num = 1;
                }
                
                board[x][y] = num;
            }
        }
        
        Map<String, Integer> cache = new HashMap<>();
        
        constructMinimumPathCache(board, cache, size);
        
        //System.out.println(cache.get("0-0"));
        
        //System.out.println(cache.get("0-1"));
        
        //System.out.println(cache.get("1-0"));
        
        // Testing
        
        for (int y = 0; y < 500; y++) {
            for (int x = 0; x < 500; x++) {
                System.out.print(cache.get(x + "-" + y) + " ");
            }
            System.out.println();
        }
        
        Set<String> selectedPath = new HashSet<>();
        
        int x = 0;
        int y = 0;
        
        while (x != size - 1 || y != size - 1) {
            selectedPath.add(x + "-" + y);
            
            int thisNum = cache.get(x + "-" + y);
            
            if (x == size - 1) {
                y++;
                continue;
            }
            
            if (y == size - 1) {
                x++;
                continue;
            }
            
            int rightNum = cache.get(x + 1 + "-" + y);
            int bottomNum = cache.get(x + "-" + (y + 1));
            
            int numToCheck = x == 0 && y == 0 ? 0 : board[x][y];
            
            if (thisNum - rightNum == numToCheck) {
                x++;
            } else {
                y++;
            }
            
        }
        
        selectedPath.add(x + "-" + y);
        
        int countChosenPathToVerify = 0;
        
        for (int yy = 0; yy < size; yy++) {
            for (int xx = 0; xx < size; xx++) {
                
                if (selectedPath.contains(xx + "-" + yy)) {
                    System.out.print("<b>" + board[xx][yy] + "</b>");
                    countChosenPathToVerify += xx == 0 && yy == 0 ? 0 : board[xx][yy];
                } else {
                    System.out.print(board[xx][yy]);
                }
            }
            System.out.println("<br>");
        }
        
        //System.out.println(selectedPath);
        
        System.out.println(countChosenPathToVerify);
        
        // 3012 - Wrong
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}