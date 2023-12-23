package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class _23 {

    private static class State {
        private int x;
        private int y;
        private int steps;
        private Set<String> visited;

        public State(int x, int y, int steps, Set<String> visited) {
            this.x = x;
            this.y = y;
            this.steps = steps;
            this.visited = visited;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getSteps() {
            return steps;
        }

        public Set<String> getVisited() {
            return visited;
        }
    }

    private static int[][] intervals = {{0,1},{0,-1},{1,0},{-1,0}};

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input23.txt"));

        int size = 141;

        char[][] forest = new char[size][size];

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int i = 0; i < size; i++) {
                forest[i][yIndex] = line.charAt(i);
            }

            yIndex++;

        }

        Set<String> startVisited = new HashSet<>();
        startVisited.add("1,0");
        State start = new State(1, 0, 0, startVisited);

        Queue<State> queue = new ArrayDeque<>();
        queue.add(start);

        int max = 0;

        while (!queue.isEmpty()) {

            State state = queue.remove();

            Set<String> visited = state.getVisited();

            if (state.getY() == size - 1) {

                if (state.getSteps() > max) {
                    max = state.getSteps();
                }

                continue;
            }

            char c = forest[state.getX()][state.getY()];

            int newX = state.getX();
            int newY = state.getY();

            if (c == '>') {
                newX ++;

                if (!visited.contains(newX + "," + newY)) {
                    queue.add(new State(newX, newY, state.getSteps() + 1, visited));
                    visited.add(newX + "," + newY);
                }
            } else if (c == '<') {
                newX --;

                if (!visited.contains(newX + "," + newY)) {
                    queue.add(new State(newX, newY, state.getSteps() + 1, visited));
                    visited.add(newX + "," + newY);
                }
            } else if (c == 'v') {
                newY ++;

                if (!visited.contains(newX + "," + newY)) {
                    queue.add(new State(newX, newY, state.getSteps() + 1, visited));
                    visited.add(newX + "," + newY);
                }
            } else if (c == '^') {
                newY --;

                if (!visited.contains(newX + "," + newY)) {
                    queue.add(new State(newX, newY, state.getSteps() + 1, visited));
                    visited.add(newX + "," + newY);
                }
            } else {

                for (int i = 0; i < 4; i++) {
                    newX = state.getX() + intervals[i][0];
                    newY = state.getY() + intervals[i][1];

                    if (newY == -1) {
                        continue;
                    }

                    if (forest[newX][newY] != '#' && !visited.contains(newX + "," + newY)) {
                        Set<String> newVisited = new HashSet<>(visited);
                        queue.add(new State(newX, newY, state.getSteps()+1, newVisited));
                        newVisited.add(newX + "," + newY);
                    }
                }
            }

        }

        System.out.println(max);

    }

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input23.txt"));

        int size = 141;

        char[][] forest = new char[size][size];

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int i = 0; i < size; i++) {
                forest[i][yIndex] = line.charAt(i);
            }

            yIndex++;

        }

        Set<String> visited = new HashSet<>();
        visited.add("1,0");
        State start = new State(1, 0, 0, visited);

        Queue<State> queue = new PriorityQueue<>((x,y)->new Integer(y.getSteps()).compareTo(x.getSteps()));
        queue.add(start);

        int max = 0;

        while (!queue.isEmpty()) {

            State state = queue.remove();

            if (state.getY() == size - 1) {
                System.out.println(state.getSteps());

                if (state.getSteps() > max) {
                    max = state.getSteps();
                }

                continue;
            }

            for (int i = 0; i < 4; i++) {
                int newX = state.getX() + intervals[i][0];
                int newY = state.getY() + intervals[i][1];

                if (newY == -1) {
                    continue;
                }

                if (forest[newX][newY] != '#' && !visited.contains(newX + "," + newY)) {
                    queue.add(new State(newX, newY, state.getSteps()+1, visited));
                    visited.add(newX + "," + newY);
                }
            }

        }

        System.out.println("max: " + max);


    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
