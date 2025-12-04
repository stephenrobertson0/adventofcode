package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class _4 {

    private static final int GRID_SIZE = 137;

    public static void a() throws Exception {
        char[][] grid = loadGrid();

        int total = countBoxesToRemove(grid, false);

        System.out.println(total);

    }

    private static char[][] loadGrid() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input4.txt"));

        char[][] grid = new char[GRID_SIZE][GRID_SIZE];

        int count = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < GRID_SIZE; j++) {
                char c = line.charAt(j);
                grid[j][count] = c;
            }

            count++;
        }
        return grid;
    }

    private static int countBoxesToRemove(char[][] grid, boolean remove) {
        int total = 0;

        int[][] dirs = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { -1, -1 }, { -1, 1 }, { 1, 1 }, { 1, -1 } };

        for (int j = 0; j < GRID_SIZE; j++) {
            for (int k = 0; k < GRID_SIZE; k++) {

                char c = grid[j][k];
                int boxCount = 0;

                if (c != '@') {
                    continue;
                }

                for (int m = 0; m < 8; m++) {

                    int x = j + dirs[m][0];
                    int y = k + dirs[m][1];

                    if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) {
                        continue;
                    }

                    if (grid[x][y] == '@') {
                        boxCount++;
                    }

                }

                if (boxCount < 4) {
                    total++;
                    if (remove) {
                        grid[j][k] = ' ';
                    }
                }

            }
        }
        return total;
    }

    public static void b() throws Exception {
        char[][] grid = loadGrid();

        int grandTotal = 0;

        while (true) {

            int total = countBoxesToRemove(grid, true);

            if (total == 0) {
                break;
            }
            grandTotal += total;
        }

        System.out.println(grandTotal);
    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
