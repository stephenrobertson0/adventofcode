package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class _5 {
    
    private static class Matrix {
        
        private int[][] matrix = new int[1000][1000];
        
        public void addLine(Line line) {
            
            if (line.isHorizontal()) {
                for (int j = Math.min(line.x1, line.x2); j <= Math.max(line.x1, line.x2); j++) {
                    matrix[j][line.y1]++;
                }
            } else if (line.isVertical()) {
                for (int j = Math.min(line.y1, line.y2); j <= Math.max(line.y1, line.y2); j++) {
                    matrix[line.x1][j]++;
                }
            } else if (line.isDiagonal()) {
                int xMin = Math.min(line.x1, line.x2);
                int yMin = Math.min(line.y1, line.y2);
                int xMax = Math.max(line.x1, line.x2);
                int yMax = Math.max(line.y1, line.y2);
                
                int length = Math.abs(line.x1 - line.x2);
                
                for (int j = 0; j <= length; j++) {
                    // Forward slash
                    if (line.y1 > line.y2 && line.x1 > line.x2 || line.y2 > line.y1 && line.x2 > line.x1) {
                        matrix[xMin + j][yMin + j]++;
                    }
                    // Back slash
                    else {
                        matrix[xMin + j][yMax - j]++;
                    }
                }
            }
        }
        
        public int count2OrAbove() {
            int count = 0;
            
            for (int j = 0; j < 1000; j++) {
                for (int k = 0; k < 1000; k++) {
                    if (matrix[j][k] >= 2) {
                        count++;
                    }
                }
            }
            
            return count;
        }
    }
    
    
    private static class Line {
        
        private int x1;
        private int y1;
        private int x2;
        private int y2;
        
        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        
        public boolean isHorizontal() {
            return y1 == y2;
        }
        
        public boolean isVertical() {
            return x1 == x2;
        }
        
        public boolean isDiagonal() {
            return Math.abs(x1 - x2) == Math.abs(y1 - y2);
        }
    }
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input5.txt")));
        
        Matrix matrix = new Matrix();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String[] nums = line.split(" -> ");
            
            int x1 = Integer.parseInt(nums[0].split(",")[0]);
            int y1 = Integer.parseInt(nums[0].split(",")[1]);
            
            int x2 = Integer.parseInt(nums[1].split(",")[0]);
            int y2 = Integer.parseInt(nums[1].split(",")[1]);
            
            Line aLine = new Line(x1, y1, x2, y2);
            
            if (aLine.isVertical() || aLine.isHorizontal()) {
                matrix.addLine(aLine);
            }
            
        }
        
        System.out.println(matrix.count2OrAbove());
        
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input5.txt")));
        
        Matrix matrix = new Matrix();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String[] nums = line.split(" -> ");
            
            int x1 = Integer.parseInt(nums[0].split(",")[0]);
            int y1 = Integer.parseInt(nums[0].split(",")[1]);
            
            int x2 = Integer.parseInt(nums[1].split(",")[0]);
            int y2 = Integer.parseInt(nums[1].split(",")[1]);
            
            Line aLine = new Line(x1, y1, x2, y2);
            
            if (aLine.isVertical() || aLine.isHorizontal() || aLine.isDiagonal()) {
                matrix.addLine(aLine);
            }
            
        }
        
        System.out.println(matrix.count2OrAbove());
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}