package _2022;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;


public class _9_Visualization extends PApplet {
    
    private static final int SIZE = 1000;
    
    public void settings() {
        size(SIZE, SIZE);
        //background(0);
        smooth();
    }
    
    public void draw() {
        
        String line = null;
        try {
            line = fileReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (line == null) {
            return;
        }
    
        String[] split = line.split(" ");
    
        String dir = split[0];
        int stepCount = Integer.parseInt(split[1]);
    
        for (int j = 0; j < stepCount; j++) {
        
            Point head = points.get(0);
        
            if ("U".equals(dir)) {
                head.y = head.y - 1;
            } else if ("D".equals(dir)) {
                head.y = head.y + 1;
            } else if ("L".equals(dir)) {
                head.x = head.x - 1;
            } else if ("R".equals(dir)) {
                head.x = head.x + 1;
            }
        
            //System.out.println("head now at: " + head.x + " " + head.y);
        
            for (int k = 1; k < 50; k++) {
            
                Point currentHead = points.get(k-1);
                Point currentTail = points.get(k);
            
                if (!arePointsTouching(currentHead, currentTail)) {
                    moveTowards(currentTail, currentHead);
                }
            }
    
            fill(0);
            rect(0, 0, SIZE, SIZE);
    
            noStroke();
            
            for (int k = points.size()-1; k >= 0; k--) {
                Point point = points.get(k);
                fill(255-5*k);
                ellipse((point.x * 10 + 10000) % 1000, (point.y * 10 + 10000) % 1000, 20, 20);
            }
        
        }
    }
    
    private static void moveTowards(Point toMove, Point towards) {
        if (toMove.x == towards.x) {
            if (toMove.y < towards.y) {
                toMove.y++;
            } else {
                toMove.y--;
            }
        } else if (toMove.y == towards.y) {
            if (toMove.x < towards.x) {
                toMove.x++;
            } else {
                toMove.x--;
            }
        } else {
            
            if (toMove.x < towards.x && toMove.y < towards.y) {
                toMove.x++;
                toMove.y++;
            } else if (toMove.x > towards.x && toMove.y > towards.y) {
                toMove.x--;
                toMove.y--;
            } else if (toMove.x < towards.x && toMove.y > towards.y) {
                toMove.x++;
                toMove.y--;
            } else if (toMove.x > towards.x && toMove.y < towards.y) {
                toMove.x--;
                toMove.y++;
            }
            
        }
    }
    
    private static class Point {
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        int x;
        int y;
    }
    
    private static boolean arePointsTouching(Point a, Point b) {
        int xDist = Math.abs(a.x - b.x);
        int yDist = Math.abs(a.y - b.y);
        
        if (xDist == 0 && yDist == 1) {
            return true;
        }
        
        if (xDist == 1 && yDist == 0) {
            return true;
        }
        
        if (xDist == 1 && yDist == 1) {
            return true;
        }
        
        if (xDist == 0 && yDist == 0) {
            return true;
        }
        
        return false;
    }
    
    private static BufferedReader fileReader = null;
    
    private static List<Point> points = new ArrayList<>();
    
    static {
    
        for (int j = 0; j < 50; j++) {
            points.add(new Point(0, 0));
        }
    }
    
    public static void main(String... args) {
    
        try {
            fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input9.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    
        PApplet.main("_2022._9_Visualization");
    }
    
}

