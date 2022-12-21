package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class _3 {
    
    private static class Coord {
        
        private final int x;
        private final int y;
        
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Coord move(char c) {
            int x = this.x;
            int y = this.y;
    
            if (c == '<') {
                x -=1;
            } else if (c == '>') {
                x += 1;
            } else if (c == '^') {
                y -= 1;
            } else if (c == 'v') {
                y += 1;
            }
            
            return new Coord(x, y);
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coord coord = (Coord)o;
            return x == coord.x && y == coord.y;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    
    private static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input3.txt"));
        
        Set<Coord> coords = new HashSet<>();
        
        Coord current = new Coord(0, 0);
        coords.add(current);
        
        final String line = fileReader.readLine();
        
        for (char c : line.toCharArray()) {
            
            Coord next = current.move(c);
            
            coords.add(next);
            
            current = next;
        }
    
        System.out.println(coords.size());
        
    }
    
    private static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input3.txt"));
    
        Set<Coord> coords = new HashSet<>();
    
        Coord current1 = new Coord(0, 0);
        Coord current2 = new Coord(0, 0);
        coords.add(current1);
        coords.add(current2);
    
        final String line = fileReader.readLine();
    
        boolean santaTurn = true;
        
        for (char c : line.toCharArray()) {
        
            Coord next;
            
            if (santaTurn) {
                next = current1.move(c);
                current1 = next;
            } else {
                next = current2.move(c);
                current2 = next;
            }
        
            coords.add(next);
            santaTurn = !santaTurn;
        }
    
        System.out.println(coords.size());
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}