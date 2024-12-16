package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _18 {

    private static int SIZE = 100;
    private static int STEP_COUNT = 100;

    private static void count(boolean cornersStayOn) throws Exception {

        Character[][] grid = new Character[SIZE][SIZE];

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input18.txt"));

        int yIndex = 0;

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int i = 0; i < SIZE; i++) {
                grid[i][yIndex] = line.charAt(i);
            }

            yIndex++;

        }

        int[][] directions = new int[][] {{-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}};

        for (int i = 0; i < STEP_COUNT; i++) {

            if (cornersStayOn) {
                grid[0][0] = '#';
                grid[SIZE-1][0] = '#';
                grid[0][SIZE-1] = '#';
                grid[SIZE-1][SIZE-1] = '#';
            }

            Character[][] newGrid = new Character[SIZE][SIZE];

            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {

                    List<Character> neighbours = new ArrayList<>();
                    for (int direction = 0; direction < directions.length; direction++) {
                        int x = j + directions[direction][0];
                        int y = k + directions[direction][1];

                        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                            neighbours.add(grid[x][y]);
                        } else {
                            neighbours.add('.');
                        }
                    }

                    int count = (int)neighbours.stream().filter(v->v.equals('#')).count();

                    if (grid[j][k] == '#') {
                        if (count == 2 || count == 3) {
                            newGrid[j][k] = '#';
                        } else {
                            newGrid[j][k] = '.';
                        }

                    } else {
                        if (count == 3) {
                            newGrid[j][k] = '#';
                        } else {
                            newGrid[j][k] = '.';
                        }
                    }

                }
            }

            grid = newGrid;

            if (cornersStayOn) {
                grid[0][0] = '#';
                grid[SIZE-1][0] = '#';
                grid[0][SIZE-1] = '#';
                grid[SIZE-1][SIZE-1] = '#';
            }

            /*for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    System.out.print(grid[k][j]);
                }
                System.out.println();
            }
            System.out.println();*/
        }

        int count = 0;

        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                if (grid[j][k] == '#') {
                    count++;
                }
            }
        }
        System.out.println(count);

    }

    private static void a() throws Exception {
        count(false);
    }

    private static void b() throws Exception {
        count(true);
    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }

}