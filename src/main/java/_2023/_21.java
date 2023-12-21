package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


public class _21 {

    private static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position)o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class State {
        private Position position;
        private int stepsTaken;

        public State(Position position, int stepsTaken) {
            this.position = position;
            this.stepsTaken = stepsTaken;
        }

        public Position getPosition() {
            return position;
        }

        public int getStepsTaken() {
            return stepsTaken;
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
            return stepsTaken == state.stepsTaken && Objects.equals(position, state.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, stepsTaken);
        }
    }

    private static int[][] intervals = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input21.txt"));

        int size = 131;

        char[][] garden = new char[size][size];

        Position current = null;

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < line.length(); j++) {

                if (line.charAt(j) == 'S') {
                    current = new Position(j, yIndex);
                    garden[j][yIndex] = '.';
                } else {
                    garden[j][yIndex] = line.charAt(j);
                }
            }

            yIndex++;

        }

        Stack<State> states = new Stack<>();
        states.add(new State(current, 0));

        int max = 64;

        Set<Position> success = new HashSet<>();

        Set<State> visited = new HashSet<>();

        while (!states.isEmpty()) {

            State state = states.pop();

            if (visited.contains(state)) {
                continue;
            }

            if (state.getStepsTaken() == max) {
                success.add(state.getPosition());
                continue;
            }

            visited.add(state);

            for (int j = 0; j < 4; j++) {

                int x = state.getPosition().getX() + intervals[j][0];
                int y = state.getPosition().getY() + intervals[j][1];

                if (x >= 0 && x < size && y >= 0 && y < size) {

                    if (garden[x][y] == '#') {
                        continue;
                    }

                    State newState = new State(new Position(x, y), state.getStepsTaken()+1);

                    states.add(newState);
                }

            }

        }

        System.out.println(success.size());

    }

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input21.txt"));

        int size = 131;

        char[][] garden = new char[size][size];

        Position current = null;

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < line.length(); j++) {

                if (line.charAt(j) == 'S') {
                    current = new Position(j, yIndex);
                    garden[j][yIndex] = '.';
                } else {
                    garden[j][yIndex] = line.charAt(j);
                }
            }

            yIndex++;

        }

        Queue<State> states = new ArrayDeque<>();
        states.add(new State(current, 0));

        int max = 2000;

        Set<Position> success = new HashSet<>();
        Set<Position> visited = new HashSet<>();

        while (!states.isEmpty()) {

            State state = states.remove();

            if (visited.contains(state.getPosition())) {
                continue;
            }

            if (state.getStepsTaken() <= max && state.getStepsTaken() % 2 == 0) {
                success.add(state.getPosition());
            }

            if (state.getStepsTaken() == max) {
                continue;
            }

            visited.add(state.getPosition());

            for (int j = 0; j < 4; j++) {

                int x = state.getPosition().getX() + intervals[j][0];
                int y = state.getPosition().getY() + intervals[j][1];

                int xx = x;
                int yy = y;

                if (x < 0 || x >= size) {
                    xx = (x + size*1000) % size;
                }

                if (y < 0 || y >= size) {
                    yy = (y + size*1000) % size;
                }

                if (garden[xx][yy] == '#') {
                    continue;
                }

                State newState = new State(new Position(x, y), state.getStepsTaken()+1);

                if (!visited.contains(new Position(x, y))) {
                    states.add(newState);
                }

            }

        }

        System.out.println(success.size());

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
