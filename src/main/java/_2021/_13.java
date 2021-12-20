package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _13 {
    
    private static class Point {
        
        int x;
        int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    
    private static class Fold {
        
        boolean vertical;
        int position;
        
        public Fold(boolean vertical, int position) {
            this.vertical = vertical;
            this.position = position;
        }
    }
    
    
    private static class Paper {
        
        int[][] board = new int[2000][2000];
        
        public Paper fold(boolean vertical, int position) {
            Paper paper = new Paper();
            
            if (vertical) {
                
                for (int j = 0; j < 2000; j++) {
                    for (int k = 0; k < position; k++) {
                        // top
                        paper.addPoint(new Point(j, k), board[j][k]);
                        // bottom
                        paper.addPoint(new Point(j, k), board[j][position * 2 - k]);
                    }
                }
                
            } else {
                for (int j = 0; j < position; j++) {
                    for (int k = 0; k < 2000; k++) {
                        // left
                        paper.addPoint(new Point(j, k), board[j][k]);
                        // right
                        paper.addPoint(new Point(j, k), board[position * 2 - j][k]);
                    }
                }
            }
            
            return paper;
        }
        
        public void addPoints(List<Point> points) {
            for (Point point : points) {
                board[point.x][point.y]++;
            }
        }
        
        public void addPoint(Point point, int amount) {
            board[point.x][point.y] += amount;
        }
        
        public void print() {
            for (int j = 0; j < 400; j++) {
                for (int k = 0; k < 400; k++) {
                    System.out.print(board[k][j] >= 1 ? "#" : ".");
                }
                System.out.println();
            }
        }
        
        public int countPoints() {
            
            int count = 0;
            
            for (int j = 0; j < 2000; j++) {
                for (int k = 0; k < 2000; k++) {
                    if (board[k][j] >= 1) {
                        count++;
                    }
                }
            }
            
            return count;
            
        }
    }
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input13.txt")));
        
        List<Point> points = new ArrayList<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line.isEmpty()) {
                break;
            }
            
            int x = Integer.parseInt(line.split(",")[0]);
            int y = Integer.parseInt(line.split(",")[1]);
            
            points.add(new Point(x, y));
        }
        
        List<Fold> folds = new ArrayList<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String f = line.substring(11);
            
            String[] f2 = f.split("=");
            
            folds.add(new Fold(f2[0].equals("y"), Integer.parseInt(f2[1])));
        }
        
        Paper paper = new Paper();
        
        paper.addPoints(points);
        
        //paper.print();
        
        //System.out.println();
        
        Paper newPaper = paper.fold(folds.get(0).vertical, folds.get(0).position);
        
        //newPaper.print();
        
        System.out.println(newPaper.countPoints());
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input13.txt")));
        
        List<Point> points = new ArrayList<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line.isEmpty()) {
                break;
            }
            
            int x = Integer.parseInt(line.split(",")[0]);
            int y = Integer.parseInt(line.split(",")[1]);
            
            points.add(new Point(x, y));
        }
        
        List<Fold> folds = new ArrayList<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String f = line.substring(11);
            
            String[] f2 = f.split("=");
            
            folds.add(new Fold(f2[0].equals("y"), Integer.parseInt(f2[1])));
        }
        
        Paper paper = new Paper();
        
        paper.addPoints(points);
        
        for (Fold fold : folds) {
            paper = paper.fold(fold.vertical, fold.position);
        }
        
        paper.print();
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}