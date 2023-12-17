package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;


public class _17 {

    private static int size = 141;

    private static int[][] land = new int[size][size];

    private static int[][] increments = new int[][] {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    private static class Position {
        int x;
        int y;
        int forwardCount;
        int direction;
        int heatCount;

        public Position(int x, int y, int forwardCount, int direction, int heatCount) {
            this.x = x;
            this.y = y;
            this.forwardCount = forwardCount;
            this.direction = direction;
            this.heatCount = heatCount;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getForwardCount() {
            return forwardCount;
        }

        public int getDirection() {
            return direction;
        }

        public int getHeatCount() {
            return heatCount;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    ", forwardCount=" + forwardCount +
                    ", direction=" + direction +
                    ", heatCount=" + heatCount +
                    '}';
        }
    }

    private static int getMinHeatLoss(int minStraight, int maxStraight) throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input17.txt"));

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < line.length(); j++) {
                land[j][yIndex] = Integer.parseInt(""+line.charAt(j));
            }

            yIndex++;

        }

        /*for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                System.out.print(land[k][j] + " ");
            }
            System.out.println();
        }*/

        Queue<Position> positions = new ArrayDeque<>();

        positions.add(new Position(0, 0, 0, 1, 0));
        positions.add(new Position(0, 0, 0, 2, 0));

        Map<String, Integer> visited = new HashMap<>();

        int min = Integer.MAX_VALUE;

        while (!positions.isEmpty()) {

            Position position = positions.remove();

            if (position.getX() == size - 1 && position.getY() == size - 1) {
                if (position.getHeatCount() < min) {
                    min = position.getHeatCount();
                }
                continue;
            }

            int newDir1 = (position.getDirection() + 1) % 4;
            int newDir2 = (position.getDirection() + 3) % 4;

            int newX1 = position.getX() + increments[newDir1][0];
            int newY1 = position.getY() + increments[newDir1][1];

            if (!(newX1 < 0 || newX1 >= size || newY1 < 0 || newY1 >= size) && position.getForwardCount() >= minStraight) {

                int newHeatCount = position.getHeatCount() + land[newX1][newY1];

                int existingHeatCount = Integer.MAX_VALUE;

                String key = newX1 + "," + newY1 + ",1" + "," + newDir1;

                if (visited.containsKey(key)) {
                    existingHeatCount = visited.get(key);
                }

                if (newHeatCount < existingHeatCount) {
                    positions.add(new Position(newX1, newY1, 1, newDir1, newHeatCount));
                    visited.put(key, newHeatCount);
                }
            }

            int newX2 = position.getX() + increments[newDir2][0];
            int newY2 = position.getY() + increments[newDir2][1];

            if (!(newX2 < 0 || newX2 >= size || newY2 < 0 || newY2 >= size) && position.getForwardCount() >= minStraight) {

                int newHeatCount = position.getHeatCount() + land[newX2][newY2];

                int existingHeatCount = Integer.MAX_VALUE;

                String key = newX2 + "," + newY2 + ",1" + "," + newDir2;

                if (visited.containsKey(key)) {
                    existingHeatCount = visited.get(key);
                }

                if (newHeatCount < existingHeatCount) {
                    positions.add(new Position(newX2, newY2, 1, newDir2, newHeatCount));
                    visited.put(key, newHeatCount);
                }
            }

            // Same direction
            int newX3 = position.getX() + increments[position.getDirection()][0];
            int newY3 = position.getY() + increments[position.getDirection()][1];

            if (!(newX3 < 0 || newX3 >= size || newY3 < 0 || newY3 >= size) && position.getForwardCount() < maxStraight) {

                int newHeatCount = position.getHeatCount() + land[newX3][newY3];

                int existingHeatCount = Integer.MAX_VALUE;

                String key = newX3 + "," + newY3 + "," + (position.getForwardCount()+1) + "," + position.getDirection();

                if (visited.containsKey(key)) {
                    existingHeatCount = visited.get(key);
                }

                if (newHeatCount < existingHeatCount) {
                    positions.add(new Position(newX3, newY3, position.getForwardCount() + 1, position.getDirection(), newHeatCount));
                    visited.put(key, newHeatCount);
                }
            }

        }

        return min;
    }

    public static void a() throws Exception {
        System.out.println(getMinHeatLoss(0, 3));
    }

    public static void b() throws Exception {
        System.out.println(getMinHeatLoss(4, 10));
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
