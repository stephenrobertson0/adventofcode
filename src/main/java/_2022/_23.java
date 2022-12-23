package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class _23 {
    
    private static class Elf {
        
        int x;
        int y;
        
        public Elf(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    
    private static class Coord {
        
        int x;
        int y;
        
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
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
    
    private static class Move {
        Elf elf;
        Coord destination;
    
        public Move(Elf elf, Coord destination) {
            this.elf = elf;
            this.destination = destination;
        }
    }
    
    private static class Board {
    
        private static final int OFFSET = 5000;
        int[][] board = new int[10000][10000];
        
        int minX;
        int maxX;
        int minY;
        int maxY;
        
        private int getValue(int x, int y) {
            return board[x+OFFSET][y+OFFSET];
        }
    
        private void setValue(int x, int y, int value) {
            
            if (value == 1) {
                if (x < minX) {
                    minX = x;
                }
    
                if (x > maxX) {
                    maxX = x;
                }
    
                if (y < minY) {
                    minY = y;
                }
    
                if (y > maxY) {
                    maxY = y;
                }
            }
            
            board[x+OFFSET][y+OFFSET] = value;
        }
        
        public int countBlanks() {
            int count = 0;
            
            for (int j = minX; j <= maxX; j++) {
                for (int k = minY; k <= maxY; k++) {
                    if (getValue(j, k) == 0) {
                        count++;
                    }
                }
            }
            
            return count;
            
        }
        
        public void print() {
    
            for (int j = minX; j <= maxX; j++) {
                for (int k = minY; k <= maxY; k++) {
                    System.out.print(getValue(j, k) == 1 ? "#" : ".");
                }
                System.out.println();
            }
    
            System.out.println();
        }
    }
    
    private static class SimulationResult {
        private int rounds;
        private Board board;
    
        public SimulationResult(int rounds, Board board) {
            this.rounds = rounds;
            this.board = board;
        }
    }
    
    public static SimulationResult simulate(int maxRounds) throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input23.txt"));
    
        List<Elf> elves = new ArrayList<>();
    
        Board board = new Board();
    
        int y = 0;
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '.') {
                    board.setValue(x, y, 0);
                }
                if (c == '#') {
                    board.setValue(x, y, 1);
                
                    elves.add(new Elf(x, y));
                }
                x++;
            }
        
            y++;
        }
    
        //board.print();
    
        List<Coord> neighboursDiag = Arrays.asList(
                new Coord(-1, -1),
                new Coord(-1, 0),
                new Coord(-1, 1),
                new Coord(0, -1),
                new Coord(0, 1),
                new Coord(1, -1),
                new Coord(1, 0),
                new Coord(1, 1));
    
        List<List<Coord>> toCheckForDirection = Arrays.asList(
                Arrays.asList(new Coord(0, -1), new Coord(-1, -1), new Coord(1, -1)),
                Arrays.asList(new Coord(0, 1), new Coord(-1, 1), new Coord(1, 1)),
                Arrays.asList(new Coord(-1, 0), new Coord(-1, -1), new Coord(-1, 1)),
                Arrays.asList(new Coord(1, 0), new Coord(1, -1), new Coord(1, 1)));
    
        int orderToCheck = 0;
    
        int roundCount = 0;
    
        while (true) {
        
            List<Move> moves = new ArrayList<>();
            Set<Coord> destinations = new HashSet<>();
            Set<Coord> duplicateDestinations = new HashSet<>();
        
            for (Elf elf : elves) {
            
                boolean elfCanMove = false;
            
                for (Coord neighbour : neighboursDiag) {
                
                    int xx = neighbour.x + elf.x;
                    int yy = neighbour.y + elf.y;
                
                    if (board.getValue(xx, yy) == 1) {
                        elfCanMove = true;
                        break;
                    }
                
                }
            
                if (!elfCanMove) {
                    continue;
                }
            
                for (int j = 0; j < toCheckForDirection.size(); j++) {
                    int directionToCheck = (j + orderToCheck) % 4;
                    List<Coord> neighbours = toCheckForDirection.get(directionToCheck);
                
                    int xx0 = neighbours.get(0).x + elf.x;
                    int yy0 = neighbours.get(0).y + elf.y;
                
                    int xx1 = neighbours.get(1).x + elf.x;
                    int yy1 = neighbours.get(1).y + elf.y;
                
                    int xx2 = neighbours.get(2).x + elf.x;
                    int yy2 = neighbours.get(2).y + elf.y;
                
                    if (board.getValue(xx0, yy0) == 0 && board.getValue(xx1, yy1) == 0 && board.getValue(xx2, yy2) == 0) {
                    
                        Coord destination = new Coord(xx0, yy0);
                        moves.add(new Move(elf, destination));
                    
                        if (destinations.contains(destination)) {
                            duplicateDestinations.add(destination);
                        }
                    
                        destinations.add(destination);
                    
                        break;
                    }
                }
            
            }
        
            // Actually move
    
            boolean didMove = false;
            
            for (Move move : moves) {
                if (!duplicateDestinations.contains(move.destination)) {
                    board.setValue(move.elf.x, move.elf.y, 0);
                
                    move.elf.x = move.destination.x;
                    move.elf.y = move.destination.y;
                
                    board.setValue(move.elf.x, move.elf.y, 1);
    
                    didMove = true;
                }
            }
        
            //board.print();
        
            orderToCheck++;
        
            roundCount ++;
    
            if (!didMove) {
                break;
            }
        
            if (roundCount == maxRounds) {
                break;
            }
        }
        
        return new SimulationResult(roundCount, board);
    }
    
    public static void a() throws Exception {
        
        SimulationResult simulationResult = simulate(10);
        
        System.out.println(simulationResult.board.countBlanks());
        
    }
    
    public static void b() throws Exception {
        SimulationResult simulationResult = simulate(-1);
    
        System.out.println(simulationResult.rounds);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
