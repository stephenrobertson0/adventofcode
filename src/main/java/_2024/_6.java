package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;


public class _6 {

    private static final int SIZE = 130;

    public static void a() throws Exception {

        Character[][] grid = new Character[SIZE][SIZE];
        int[][] directions = new int[][] {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input6.txt"));

        int startX = 0, startY = 0;

        int yIndex = 0;

        while (true) {
            String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < SIZE; j++) {
                grid[j][yIndex] = line.charAt(j);

                if (line.charAt(j) == '^') {
                    startX = j;
                    startY = yIndex;
                }
            }
            yIndex++;
        }

        Set<String> positions = new HashSet<>();

        int direction = 0;

        while (true) {

            positions.add(startX + "," + startY);

            int nextX = startX + directions[direction][0];
            int nextY = startY + directions[direction][1];

            if (nextX < 0 || nextX >= SIZE || nextY < 0 || nextY >= SIZE) {
                break;
            }

            if (grid[nextX][nextY] == '#') {
                direction++;
                if (direction == 4) {
                    direction = 0;
                }
            } else {
                startX = nextX;
                startY = nextY;
            }

        }

        System.out.println(positions.size());

    }

    private static boolean isStuck(Character[][] grid, int startX, int startY) {
        int[][] directions = new int[][] {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

        int direction = 0;

        for (int j = 0; j < 100_000; j++) {

            int nextX = startX + directions[direction][0];
            int nextY = startY + directions[direction][1];

            if (nextX < 0 || nextX >= SIZE || nextY < 0 || nextY >= SIZE) {
                return false;
            }

            if (grid[nextX][nextY] == '#') {
                direction++;
                if (direction == 4) {
                    direction = 0;
                }
            } else {
                startX = nextX;
                startY = nextY;
            }

        }

        return true;
    }
    
    public static void b() throws Exception {

        Character[][] grid = new Character[SIZE][SIZE];

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input6.txt"));

        int startX = 0, startY = 0;

        int yIndex = 0;

        while (true) {
            String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < SIZE; j++) {
                grid[j][yIndex] = line.charAt(j);

                if (line.charAt(j) == '^') {
                    startX = j;
                    startY = yIndex;
                }
            }
            yIndex++;
        }

        int count = 0;

        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                if (grid[j][k] != '#' && grid[j][k] != '^') {
                    grid[j][k] = '#';
                    if (isStuck(grid, startX, startY)) {
                        count++;
                    }
                    grid[j][k] = '.';
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
