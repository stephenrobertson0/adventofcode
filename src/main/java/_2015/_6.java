package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _6 {
    
    private static class Step {
        int startX;
        int startY;
        int endX;
        int endY;
        int operation;
    
        public Step(int startX, int startY, int endX, int endY, int operation) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.operation = operation;
        }
    }
    
    private static List<Step> parseSteps() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input6.txt"));
        
        List<Step> steps = new ArrayList<>();
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            String restOfLine;
            int operation;
        
            if (line.startsWith("turn on")) {
                operation = 1;
                restOfLine = line.substring(8);
            } else if (line.startsWith("turn off")) {
                operation = 0;
                restOfLine = line.substring(9);
            } else {
                operation = 2;
                restOfLine = line.substring(7);
            }
        
            String[] split = restOfLine.split(" through ");
            String[] startSplit = split[0].split(",");
            String[] endSplit = split[1].split(",");
        
            int startX = Integer.parseInt(startSplit[0]);
            int startY = Integer.parseInt(startSplit[1]);
            int endX = Integer.parseInt(endSplit[0]);
            int endY = Integer.parseInt(endSplit[1]);
        
            steps.add(new Step(startX, startY, endX, endY, operation));
        }
        
        return steps;
    }
    
    private static void a() throws Exception {
        
        int[][] board = new int[1000][1000];
        
        List<Step> steps = parseSteps();
        
        for (Step step : steps) {
            for (int j = step.startX; j <= step.endX; j++) {
                for (int k = step.startY; k <= step.endY; k++) {
                    
                    if (step.operation == 2) {
                        board[j][k] = board[j][k] == 1 ? 0 : 1;
                    } else {
                        board[j][k] = step.operation;
                    }
                    
                }
            }
        }
        
        int sum = 0;
        
        for (int j = 0; j < 1000; j++) {
            for (int k = 0; k < 1000; k++) {
                
                sum += board[j][k];
                
            }
        }
        
        System.out.println(sum);
        
    }
    
    private static void b() throws Exception {
        int[][] board = new int[1000][1000];
    
        List<Step> steps = parseSteps();
        
        for (Step step : steps) {
            for (int j = step.startX; j <= step.endX; j++) {
                for (int k = step.startY; k <= step.endY; k++) {
            
                    if (step.operation == 0) {
                        if (board[j][k] != 0) {
                            board[j][k]--;
                        }
                    } else {
                        board[j][k] += step.operation;
                    }
            
                }
            }
        }
    
        int sum = 0;
    
        for (int j = 0; j < 1000; j++) {
            for (int k = 0; k < 1000; k++) {
            
                sum += board[j][k];
            
            }
        }
    
        System.out.println(sum);
    }
    
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}