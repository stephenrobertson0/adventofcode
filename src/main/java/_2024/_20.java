package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class _20 {

    private record XY(int x, int y) {}
    private record State(XY position, int distance) {}
    private record StateV2(XY position, int distance, List<State> path) {}
    private static int[][] directions = new int[][] {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    private record Input(Character[][] grid, XY start, XY end) {}

    private static int SIZE = 141;

    private static void printGrid(Character[][] grid) {
        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                System.out.print(grid[k][j]);
            }
            System.out.println();
        }
    }

    private static Input parseInput() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input20.txt"));

        Character[][] grid = new Character[SIZE][SIZE];

        int yIndex = 0;
        XY start = null;
        XY end = null;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < SIZE; j++) {
                char c = line.charAt(j);
                grid[j][yIndex] = c;

                if (c == 'S') {
                    start = new XY(j, yIndex);
                }

                if (c == 'E') {
                    end = new XY(j, yIndex);
                }

            }
            yIndex++;

        }

        return new Input(grid, start, end);

    }

    public static void a() throws Exception {

        int count = 0;

        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {

                Input input = parseInput();

                XY start = input.start;
                XY end = input.end;
                Character[][] grid = input.grid;

                //printGrid(grid);

                grid[j][k] = '.';

                Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));

                queue.add(new State(start, 0));

                Set<XY> alreadyVisited = new HashSet<>();

                long min = Integer.MAX_VALUE;

                while (!queue.isEmpty()) {
                    State state = queue.poll();

                    if (alreadyVisited.contains(state.position)) {
                        continue;
                    }

                    if (state.position.x == end.x && state.position.y == end.y) {
                        if (state.distance < min) {
                            min = state.distance;
                        }

                        continue;
                    }

                    for (int i = 0; i < directions.length; i++) {

                        int newX = state.position.x + directions[i][0];
                        int newY = state.position.y + directions[i][1];

                        if (newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE && grid[newX][newY] != '#') {
                            queue.add(new State(new XY(newX, newY), state.distance + 1));
                        }
                    }

                    alreadyVisited.add(state.position);
                }

                if (min != 9388) {
                    if (9388 - min >= 100) {
                        count++;
                    }
                }

            }
        }

        System.out.println(count);
    }

    public static void b() throws Exception {
        int count = 0;

        Input input = parseInput();

        XY start = input.start;
        XY end = input.end;
        Character[][] grid = input.grid;

        //printGrid(grid);

        Queue<StateV2> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));

        List<State> path = new ArrayList<>();
        path.add(new State(start, 0));
        queue.add(new StateV2(start, 0, path));

        Set<XY> alreadyVisited = new HashSet<>();

        StateV2 min = null;

        while (!queue.isEmpty()) {
            StateV2 state = queue.poll();

            if (alreadyVisited.contains(state.position)) {
                continue;
            }

            if (state.position.x == end.x && state.position.y == end.y) {
                if (min == null || state.distance < min.distance) {
                    min = state;
                }

                continue;
            }

            for (int i = 0; i < directions.length; i++) {

                int newX = state.position.x + directions[i][0];
                int newY = state.position.y + directions[i][1];

                if (newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE && grid[newX][newY] != '#') {

                    List<State> newPath = new ArrayList<>(state.path);
                    newPath.add(new State(new XY(newX, newY), state.distance + 1));

                    queue.add(new StateV2(new XY(newX, newY), state.distance + 1, newPath));
                }
            }

            alreadyVisited.add(state.position);
        }

        List<State> minPath = min.path;

        for (int j = 0; j < minPath.size(); j++) {
            for (int k = j+1; k < minPath.size(); k++) {

                State state1 = minPath.get(j);
                State state2 = minPath.get(k);

                int directDistance = Math.abs(state1.position.x - state2.position.x) + Math.abs(state1.position.y - state2.position.y);

                if (directDistance <= 20 && directDistance < state2.distance - state1.distance - 100 + 1) {
                    count++;
                }

            }
        }

        System.out.println(count);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
