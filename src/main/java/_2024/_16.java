package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class _16 {

    private record XY(int x, int y, int direction) {}
    private record State(XY position, int score) {}
    private static int[][] directions = new int[][] {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    private record StateV2(XY position, int score, List<XY> visited) {}

    private static int SIZE = 141;

    private static int getLeftDirection(int direction) {
        int left = direction - 1;
        if (left < 0) {
            left = 3;
        }
        return left;
    }

    private static int getRightDirection(int direction) {
        int right = direction + 1;
        if (right > 3) {
            right = 0;
        }
        return right;
    }

    private static void printGrid(Character[][] grid) {
        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                System.out.print(grid[k][j]);
            }
            System.out.println();
        }
    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input16.txt"));

        Character[][] grid = new Character[SIZE][SIZE];

        int yIndex = 0;
        int direction = 1;
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
                    start = new XY(j, yIndex, direction);
                }

                if (c == 'E') {
                    end = new XY(j, yIndex, direction);
                }

            }
            yIndex++;

        }

        //printGrid(grid);

        Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.score));

        queue.add(new State(start, 0));

        Set<XY> alreadyVisited = new HashSet<>();

        long min = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (alreadyVisited.contains(state.position)) {
                continue;
            }

            if (state.position.x == end.x && state.position.y == end.y) {
                if (state.score < min) {
                    min = state.score;
                }
                continue;
            }

            XY forward = new XY(state.position.x + directions[state.position.direction][0], state.position.y + directions[state.position.direction][1], state.position.direction);
            XY left = new XY(state.position.x, state.position.y, getLeftDirection(state.position.direction));
            XY right = new XY(state.position.x, state.position.y, getRightDirection(state.position.direction));

            if (grid[forward.x][forward.y] != '#') {
                queue.add(new State(forward, state.score + 1));
            }

            queue.add(new State(left, state.score + 1000));

            queue.add(new State(right, state.score + 1000));

            alreadyVisited.add(state.position);

        }

        System.out.println(min);

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input16.txt"));

        Character[][] grid = new Character[SIZE][SIZE];

        int yIndex = 0;
        int direction = 1;
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
                    start = new XY(j, yIndex, direction);
                }

                if (c == 'E') {
                    end = new XY(j, yIndex, direction);
                }

            }
            yIndex++;

        }

        //printGrid(grid);

        Set<XY> bestPathPoints = new HashSet<>();

        Queue<StateV2> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.score));

        queue.add(new StateV2(start, 0, new ArrayList<>()));

        Map<XY, Integer> alreadyVisited = new HashMap<>();

        long min = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            StateV2 state = queue.poll();

            if (state.position.x == end.x && state.position.y == end.y) {
                if (state.score < min) {
                    min = state.score;
                }

                if (state.score == 82460) {
                    bestPathPoints.addAll(state.visited);
                }

                continue;
            }

            XY forward = new XY(state.position.x + directions[state.position.direction][0], state.position.y + directions[state.position.direction][1], state.position.direction);
            XY left = new XY(state.position.x, state.position.y, getLeftDirection(state.position.direction));
            XY right = new XY(state.position.x, state.position.y, getRightDirection(state.position.direction));

            if (grid[forward.x][forward.y] != '#') {
                int score = state.score + 1;

                int existingScore = alreadyVisited.getOrDefault(forward, Integer.MAX_VALUE);
                if (existingScore >= score) {
                    List<XY> newVisited = new ArrayList<>(state.visited);
                    newVisited.add(forward);
                    queue.add(new StateV2(forward, score, newVisited));
                    alreadyVisited.put(forward, score);
                }
            }

            int score = state.score + 1000;

            int existingScore = alreadyVisited.getOrDefault(left, Integer.MAX_VALUE);
            if (existingScore >= score) {
                List<XY> newVisited = new ArrayList<>(state.visited);
                newVisited.add(left);
                queue.add(new StateV2(left, score, newVisited));
                alreadyVisited.put(left, score);
            }

            existingScore = alreadyVisited.getOrDefault(right, Integer.MAX_VALUE);
            if (existingScore >= score) {
                List<XY> newVisited = new ArrayList<>(state.visited);
                newVisited.add(right);
                queue.add(new StateV2(right, score, newVisited));
                alreadyVisited.put(right, score);
            }

        }

        Set<String> uniquePoints = new HashSet<>();
        for (XY bestPathPoint : bestPathPoints) {
            uniquePoints.add(bestPathPoint.x + " " + bestPathPoint.y);
        }

        System.out.println(uniquePoints.size());
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
