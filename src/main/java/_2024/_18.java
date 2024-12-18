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


public class _18 {

    private static int SIZE = 71;
    private static int DROP_COUNT = 1024;
    private record XY(int x, int y) {}
    private record State(XY position, int distance) {}
    private static int[][] directions = new int[][] {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    private static Character[][] grid = new Character[SIZE][SIZE];

    private static void printGrid(Character[][] grid) {
        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                System.out.print(grid[k][j]);
            }
            System.out.println();
        }
    }

    private static List<XY> parseInput() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input18.txt"));

        List<XY> points = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            int x = Integer.parseInt(line.split(",")[0]);
            int y = Integer.parseInt(line.split(",")[1]);

            points.add(new XY(x, y));
        }

        return points;
    }

    private static int getMinDistance(Character[][] grid) {

        Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));

        XY start = new XY(0, 0);

        queue.add(new State(start, 0));

        Set<XY> alreadyVisited = new HashSet<>();

        int min = Integer.MAX_VALUE;

        XY end = new XY(SIZE-1, SIZE-1);

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

        return min;
    }

    public static void a() throws Exception {

        List<XY> points = parseInput();

        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                grid[k][j] = '.';
            }
        }

        for (int j = 0; j < DROP_COUNT; j++) {

            XY point = points.get(j);

            grid[point.x][point.y] = '#';

        }

        //printGrid(grid);

        System.out.println(getMinDistance(grid));
    }

    public static void b() throws Exception {

        List<XY> points = parseInput();

        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                grid[k][j] = '.';
            }
        }

        for (int j = 0; j < points.size(); j++) {

            XY point = points.get(j);

            grid[point.x][point.y] = '#';

            int min = getMinDistance(grid);

            if (min == Integer.MAX_VALUE) {
                System.out.println(point.x + "," + point.y);
                break;
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
