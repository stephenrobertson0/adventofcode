package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class _24 {
    
    private static final int XSIZE = 100;
    private static final int YSIZE = 35;
    
    //private static final int XSIZE = 6;
    //private static final int YSIZE = 4;
    
    private static int[][] myMoves = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
    
    
    private static class Blizzard {
        
        private int x;
        private int y;
        // Right 0, down 1, left 2, up 3
        private int direction;
        
        private int[][] moves = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        
        public Blizzard(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
        
        public Blizzard move() {
            
            int newX = x + moves[direction][0];
            int newY = y + moves[direction][1];
            
            if (newX < 0) {
                newX = XSIZE - 1;
            }
            
            if (newY < 0) {
                newY = YSIZE - 1;
            }
            
            if (newX == XSIZE) {
                newX = 0;
            }
            
            if (newY == YSIZE) {
                newY = 0;
            }
            
            return new Blizzard(newX, newY, direction);
            
        }
    }
    
    
    private static class BlizzardBoard {
        
        int[][] board = new int[XSIZE][YSIZE];
        
        public BlizzardBoard(List<Blizzard> blizzards) {
            for (Blizzard blizzard : blizzards) {
                board[blizzard.x][blizzard.y] = 1;
            }
        }
    }
    
    
    private static class State {
        
        private int x;
        private int y;
        private int timePassed;
        private Destination destination;
        private int destinationIndex;
        
        public State(
                int x,
                int y,
                int timePassed,
                Destination destination,
                int destinationIndex) {
            this.x = x;
            this.y = y;
            this.timePassed = timePassed;
            this.destination = destination;
            this.destinationIndex = destinationIndex;
        }
    
        @Override
        public String toString() {
            return "State{" +
                    "x=" + x +
                    ", y=" + y +
                    ", timePassed=" + timePassed +
                    ", destination=" + destination +
                    ", destinationIndex=" + destinationIndex +
                    '}';
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            State state = (State)o;
            return x == state.x && y == state.y && timePassed == state.timePassed
                    && destinationIndex == state.destinationIndex
                    && Objects.equals(destination, state.destination);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(x, y, timePassed, destination, destinationIndex);
        }
    }
    
    private static void printState(State state, List<Blizzard> blizzards) {
        
        int[][] board = new int[XSIZE][YSIZE];
        
        for (Blizzard blizzard : blizzards) {
            board[blizzard.x][blizzard.y] = blizzard.direction + 4;
        }
        
        if (state.y >= 0) {
            board[state.x][state.y] = 9;
        }
        
        for (int k = 0; k < YSIZE; k++) {
            for (int j = 0; j < XSIZE; j++) {
                System.out.print(board[j][k]);
            }
            System.out.println();
        }
        
        System.out.println();
        
    }
    
    private static class Destination {
        
        int destinationX;
        int destinationY;
        
        public Destination(int destinationX, int destinationY) {
            this.destinationX = destinationX;
            this.destinationY = destinationY;
        }
    }
    
    public static int getStepCount(List<Destination> destinations) throws Exception {
        
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input24.txt"));
        
        fileReader.readLine();
        
        List<Blizzard> blizzards = new ArrayList<>();
        Map<Integer, List<Blizzard>> blizzardsByTime = new HashMap<>();
        Map<Integer, BlizzardBoard> blizzardsBoardsByTime = new HashMap<>();
        
        int y = 0;
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            int x = 0;
            
            for (char c : line.toCharArray()) {
                
                if (c == '#') {
                    continue;
                }
                
                if (c == '.') {
                    x++;
                    continue;
                }
                
                int direction = -1;
                
                if (c == '>') {
                    direction = 0;
                } else if (c == 'v') {
                    direction = 1;
                } else if (c == '<') {
                    direction = 2;
                } else if (c == '^') {
                    direction = 3;
                }
                
                blizzards.add(new Blizzard(x, y, direction));
                
                blizzardsByTime.put(0, blizzards);
                
                x++;
            }
            
            y++;
        }
        
        for (int j = 1; j < 1000; j++) {
            
            List<Blizzard> newBlizzards = new ArrayList<>();
            
            for (Blizzard blizzard : blizzardsByTime.get(j - 1)) {
                newBlizzards.add(blizzard.move());
            }
            
            blizzardsByTime.put(j, newBlizzards);
            blizzardsBoardsByTime.put(j, new BlizzardBoard(newBlizzards));
            
        }
        
        State startState = new State(
                0,
                -1,
                0,
                destinations.get(0),
                0);
        
        List<State> allStates = new ArrayList<>();
        allStates.add(startState);
        
        while (true) {
            
            List<State> newAllStates = new ArrayList<>();
            
            for (State state : allStates) {
                
                //printState(state, blizzardsByTime.get(state.timePassed));
                
                for (int j = 0; j < 5; j++) {
                    
                    int newX = state.x + myMoves[j][0];
                    int newY = state.y + myMoves[j][1];
                    int newTimePassed = state.timePassed + 1;
                    
                    if (newX < 0 || newX >= XSIZE || newY < 0 || newY >= YSIZE) {
                        continue;
                    }
                    
                    BlizzardBoard blizzardBoard = blizzardsBoardsByTime.get(newTimePassed);
                    
                    boolean blizzardBlocking = false;
                    
                    if (blizzardBoard.board[newX][newY] == 1) {
                        blizzardBlocking = true;
                    }
                    
                    if (!blizzardBlocking) {
                        State newState = new State(
                                newX,
                                newY,
                                newTimePassed,
                                state.destination,
                                state.destinationIndex);
                        if (!newAllStates.contains(newState)) {
                            newAllStates.add(newState);
                        }
                    }
                    
                }
            }
            
            boolean finished = false;
            
            allStates = new ArrayList<>();
            
            for (State s : newAllStates) {
                if (s.x == s.destination.destinationX && s.y == s.destination.destinationY) {
                    s.y += s.destination.destinationY == 0 ? -1 : 1;
                    s.timePassed += 1;
                    s.destinationIndex += 1;
                    
                    if (s.destinationIndex != destinations.size()) {
                        s.destination = destinations.get(s.destinationIndex);
                    } else {
                        s.destination = null;
                        finished = true;
                    }
                }
                
                allStates.add(s);
            }
            
            if (finished) {
                break;
            }
            
        }
        
        return allStates.stream().mapToInt(v->v.timePassed).max().getAsInt();
        
    }
    
    public static void a() throws Exception {
        
        System.out.println(getStepCount(Arrays.asList(new Destination(XSIZE - 1, YSIZE - 1))));
        
    }
    
    public static void b() throws Exception {
        
        System.out.println(getStepCount(Arrays.asList(
                new Destination(XSIZE - 1, YSIZE - 1),
                new Destination(0, 0),
                new Destination(XSIZE - 1, YSIZE - 1))));
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
