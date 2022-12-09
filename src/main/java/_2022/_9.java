package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class _9 {
    
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
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input9.txt"));
        
        Set<String> tailPoints = new HashSet<>();
        
        Point head = new Point(0, 0);
        Point tail = new Point(0, 0);
        
        tailPoints.add(tail.x + " " + tail.y);
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String[] split = line.split(" ");
            
            String dir = split[0];
            int stepCount = Integer.parseInt(split[1]);
            
            for (int j = 0; j < stepCount; j++) {
                
                int beforeHeadX = head.x;
                int beforeHeadY = head.y;
                
                if ("U".equals(dir)) {
                    head.y = head.y - 1;
                } else if ("D".equals(dir)) {
                    head.y = head.y + 1;
                } else if ("L".equals(dir)) {
                    head.x = head.x - 1;
                } else if ("R".equals(dir)) {
                    head.x = head.x + 1;
                }
                
                if (!arePointsTouching(head, tail)) {
                    tail.x = beforeHeadX;
                    tail.y = beforeHeadY;
                }
                
                tailPoints.add(tail.x + " " + tail.y);
                
            }
        }
        
        System.out.println(tailPoints.size());
        
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
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input9.txt"));
        
        Set<String> tailPoints = new HashSet<>();
        
        List<Point> points = new ArrayList<>();
        
        for (int j = 0; j < 10; j++) {
            points.add(new Point(0, 0));
        }
        
        tailPoints.add(points.get(9).x + " " + points.get(9).y);
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
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
    
                System.out.println("head now at: " + head.x + " " + head.y);
                
                for (int k = 1; k < 10; k++) {
                    
                    Point currentHead = points.get(k-1);
                    Point currentTail = points.get(k);
                    
                    if (!arePointsTouching(currentHead, currentTail)) {
                        moveTowards(currentTail, currentHead);
                    }
                }
    
                tailPoints.add(points.get(9).x + " " + points.get(9).y);
                
            }
        }
        
        System.out.println(tailPoints.size());
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
