package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _18 {

    private static class DirectionAndCount {
        private int direction;
        private int count;

        public DirectionAndCount(int direction, int count) {
            this.direction = direction;
            this.count = count;
        }

        public int getDirection() {
            return direction;
        }

        public int getCount() {
            return count;
        }
    }

    private static int[][] increments = new int[][]{{0, -1},{1, 0},{0, 1},{-1, 0}};

    private static int getBlockValueInDirection(int[][] grid, int x, int y, int direction) {

        int xx = x + increments[direction][0];
        int yy = y + increments[direction][1];

        return grid[xx][yy];
    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input18.txt"));

        List<DirectionAndCount> directionAndCountList = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            char directionC = line.charAt(0);
            int count = Integer.parseInt(line.split(" ")[1]);

            int direction = -1;

            if (directionC == 'U') {
                direction = 0;
            } else if (directionC == 'R') {
                direction = 1;
            } else if (directionC == 'D') {
                direction = 2;
            } else if (directionC == 'L') {
                direction = 3;
            }

            directionAndCountList.add(new DirectionAndCount(direction, count));

        }

        int size = 1000;

        int[][] grid = new int[size][size];

        int x = 400;
        int y = 400;

        grid[x][y] = 1;

        for (DirectionAndCount directionAndCount : directionAndCountList) {

            for (int j = 0; j < directionAndCount.getCount(); j++) {
                x += increments[directionAndCount.getDirection()][0];
                y += increments[directionAndCount.getDirection()][1];

                grid[x][y] = 1;
            }


        }

        /*for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                System.out.print((grid[k][j] == 1 ? "#": " ") + " ");
            }
            System.out.println();
        }*/

        int[][] newGrid = new int[size][size];

        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                newGrid[k][j] = grid[k][j];
            }
        }

        int count = 0;

        for (int j = 0; j < size; j++) {

            boolean topFound = false;
            boolean bottomFound = false;
            boolean inside = false;

            for (int k = 0; k < size; k++) {

                if (grid[k][j] == 1) {
                    count++;

                    boolean topLeft = getBlockValueInDirection(grid, k, j, 1) == 1 && getBlockValueInDirection(grid, k, j, 2) == 1;
                    boolean topRight = getBlockValueInDirection(grid, k, j, 2) == 1 && getBlockValueInDirection(grid, k, j, 3) == 1;
                    boolean bottomLeft = getBlockValueInDirection(grid, k, j, 0) == 1 && getBlockValueInDirection(grid, k, j, 1) == 1;
                    boolean bottomRight = getBlockValueInDirection(grid, k, j, 0) == 1 && getBlockValueInDirection(grid, k, j, 3) == 1;

                    boolean isStraight = getBlockValueInDirection(grid, k, j, 0) == 1 && getBlockValueInDirection(grid, k, j, 2) == 1;

                    if (topLeft || topRight) {
                        topFound = !topFound;
                    }

                    if (bottomLeft || bottomRight) {
                        bottomFound = !bottomFound;
                    }

                    if (topFound && bottomFound) {
                        inside = !inside;
                        topFound = false;
                        bottomFound = false;
                    }

                    if (isStraight) {
                        inside = !inside;
                    }

                } else {
                    if (inside) {
                        count++;
                        newGrid[k][j] = 1;
                    }
                }

            }
        }

        /*for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                System.out.print((newGrid[k][j] == 1 ? "#": " ") + " ");
            }
            System.out.println();
        }*/

        System.out.println(count);

    }

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input18.txt"));

        List<DirectionAndCount> directionAndCountList = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String hex = line.split(" ")[2];

            int count = Integer.parseInt(hex.substring(2, hex.length()-2), 16);
            int direction = Integer.parseInt(""+hex.charAt(hex.length()-2));

            direction = (direction + 1) % 4;

            directionAndCountList.add(new DirectionAndCount(direction, count));

        }

        List<Point> points = new ArrayList<>();

        int x = 0;
        int y = 0;
        long perimeter = 0;

        points.add(new Point(x, y));

        for (DirectionAndCount directionAndCount : directionAndCountList) {

            x += increments[directionAndCount.getDirection()][0] * directionAndCount.getCount();
            y += increments[directionAndCount.getDirection()][1] * directionAndCount.getCount();

            perimeter += directionAndCount.getCount();

            points.add(new Point(x, y));
        }

        long area = 0;

        for (int j = 0; j < points.size() - 1; j++) {

            Point point1 = points.get(j);
            Point point2 = points.get(j+1);

            int x1 = point1.getX();
            int x2 = point2.getX();
            int y1 = point1.getY();
            int y2 = point2.getY();

            area += (long)(x2-x1) * (y2+y1);
        }

        System.out.println(Math.abs(area)/2 + perimeter/2 + 1);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
