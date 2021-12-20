package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;


public class _9 {
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input9.txt")));
        
        int[][] board = new int[100][100];
        
        int count = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            for (int j = 0; j < line.length(); j++) {
                board[j][count] = Integer.parseInt("" + line.charAt(j));
            }
            
            count++;
        }
        
        int total = 0;
        
        for (int j = 0; j < 100; j++) {
            for (int k = 0; k < 100; k++) {
                
                //System.out.print(board[j][k]);
                
                boolean lowest = true;
                
                // left
                if (j != 0) {
                    if (board[j - 1][k] <= board[j][k]) {
                        lowest = false;
                    }
                }
                
                // right
                if (j != 99) {
                    if (board[j + 1][k] <= board[j][k]) {
                        lowest = false;
                    }
                }
                
                // top
                if (k != 0) {
                    if (board[j][k - 1] <= board[j][k]) {
                        lowest = false;
                    }
                }
                
                // bottom
                if (k != 99) {
                    if (board[j][k + 1] <= board[j][k]) {
                        lowest = false;
                    }
                }
                
                if (lowest) {
                    
                    //System.out.println("Position " + j + " " + k + " is the lowest");
                    
                    //System.out.println("Value: " + board[j][k]);
                    
                    total += 1 + board[j][k];
                }
            }
            
            //System.out.println();
        }
        
        System.out.println(total);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input9.txt")));
        
        int[][] board = new int[100][100];
        
        int count = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            for (int j = 0; j < line.length(); j++) {
                board[j][count] = Integer.parseInt("" + line.charAt(j));
            }
            
            count++;
        }
        
        List<Integer> basinSizes = new ArrayList<>();
        
        for (int j = 0; j < 100; j++) {
            for (int k = 0; k < 100; k++) {
                
                //System.out.print(board[j][k]);
                
                boolean lowest = true;
                
                // left
                if (j != 0) {
                    if (board[j - 1][k] <= board[j][k]) {
                        lowest = false;
                    }
                }
                
                // right
                if (j != 99) {
                    if (board[j + 1][k] <= board[j][k]) {
                        lowest = false;
                    }
                }
                
                // top
                if (k != 0) {
                    if (board[j][k - 1] <= board[j][k]) {
                        lowest = false;
                    }
                }
                
                // bottom
                if (k != 99) {
                    if (board[j][k + 1] <= board[j][k]) {
                        lowest = false;
                    }
                }
                
                if (lowest) {
                    
                    //System.out.println("Position " + j + " " + k + " is the lowest");
                    
                    //System.out.println("Value: " + board[j][k]);
                    
                    Set<String> painted = new HashSet<>();
                    
                    basinSizes.add(getBasinSize(j, k, board, painted));
                }
            }
            
            //System.out.println();
        }
        
        //System.out.println(basinSizes);
        
        List<Integer> top3 = basinSizes.stream()
                .sorted(Integer::compareTo)
                .sorted(Collections.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());
        
        System.out.println(top3.get(0) * top3.get(1) * top3.get(2));
    }
    
    private static int getBasinSize(int j, int k, int[][] board, Set<String> painted) {
        if (painted.contains(j + "-" + k)) {
            return 0;
        }
        
        int size = 1;
        
        painted.add(j + "-" + k);
        
        //System.out.println("Checking value: " + board[j][k]);
        
        // left
        if (j != 0) {
            if (board[j - 1][k] != 9 && board[j - 1][k] > board[j][k]) {
                //System.out.println("Add position left");
                
                size += getBasinSize(j - 1, k, board, painted);
            }
        }
        
        // right
        if (j != 99) {
            if (board[j + 1][k] != 9 && board[j + 1][k] > board[j][k]) {
                //System.out.println("Add position right");
                
                size += getBasinSize(j + 1, k, board, painted);
            }
        }
        
        // top
        if (k != 0) {
            if (board[j][k - 1] != 9 && board[j][k - 1] > board[j][k]) {
                //System.out.println("Add position top");
                
                size += getBasinSize(j, k - 1, board, painted);
            }
        }
        
        // bottom
        if (k != 99) {
            if (board[j][k + 1] != 9 && board[j][k + 1] > board[j][k]) {
                //System.out.println("Add position bottom");
                
                size += getBasinSize(j, k + 1, board, painted);
            }
        }
        
        return size;
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}