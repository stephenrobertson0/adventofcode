package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;


public class _16 {

    private static int size = 110;

    private static class Beam {
        private int x;
        private int y;

        private int direction;

        public Beam(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getDirection() {
            return direction;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Beam beam = (Beam)o;
            return x == beam.x && y == beam.y && direction == beam.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, direction);
        }
    }

    private static int[][] increments = new int[][] {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    private static char[][] cave = new char[size][size];

    private static int[][] result = new int[size][size];

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input16.txt"));

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < line.length(); j++) {
                cave[j][yIndex] = line.charAt(j);
            }

            yIndex++;

        }

        System.out.println(getCountForStartingBeam(new Beam(-1, 0, 1)));

    }

    private static int getCountForStartingBeam(Beam start) {
        Stack<Beam> beams = new Stack<>();

        beams.add(start);

        Set<String> allPositions = new HashSet<>();
        Set<Beam> allBeams = new HashSet<>();

        while (!beams.isEmpty()) {

            Beam beam = beams.pop();

            int newX = beam.getX() + increments[beam.getDirection()][0];
            int newY = beam.getY() + increments[beam.getDirection()][1];

            //System.out.println("New x: " + newX);
            //System.out.println("New y: " + newY);

            if (newX < 0 || newY < 0 || newX >= size || newY >= size) {
                continue;
            }

            if (allBeams.contains(new Beam(newX, newY, beam.getDirection()))) {
                continue;
            }

            allPositions.add(newX + "," + newY);
            allBeams.add(new Beam(newX, newY, beam.getDirection()));

            result[newX][newY] = 1;

            int direction = beam.getDirection();
            char c = cave[newX][newY];

            if (c == '.') {
                beams.push(new Beam(newX, newY, beam.getDirection()));
            } else if (c == '/') {

                int newDirection = -1;

                if (direction == 0) {
                    newDirection = 1;
                } else if (direction == 1) {
                    newDirection = 0;
                } else if (direction == 2) {
                    newDirection = 3;
                } else if (direction == 3) {
                    newDirection = 2;
                }

                beams.push(new Beam(newX, newY, newDirection));
            } else if (c == '\\') {

                int newDirection = -1;

                if (direction == 0) {
                    newDirection = 3;
                } else if (direction == 1) {
                    newDirection = 2;
                } else if (direction == 2) {
                    newDirection = 1;
                } else if (direction == 3) {
                    newDirection = 0;
                }

                beams.push(new Beam(newX, newY, newDirection));
            } else if (c == '-') {

                if (direction == 1 || direction == 3) {
                    beams.push(new Beam(newX, newY, direction));
                } else {
                    beams.push(new Beam(newX, newY, 1));
                    beams.push(new Beam(newX, newY, 3));
                }

            } else if (c == '|') {

                //System.out.println("Is pipe");
                //System.out.println("Direction: " + direction);

                if (direction == 0 || direction == 2) {
                    beams.push(new Beam(newX, newY, direction));
                } else {
                    beams.push(new Beam(newX, newY, 0));
                    beams.push(new Beam(newX, newY, 2));
                }

            }
        }

        /*for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                if (result[k][j] == 1) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }*/

        return allPositions.size();
    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input16.txt"));

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < line.length(); j++) {
                cave[j][yIndex] = line.charAt(j);
            }

            yIndex++;

        }

        List<Beam> startPositions = new ArrayList<>();

        for (int j = 0; j < size; j++) {
            startPositions.add(new Beam(-1, j, 1));
            startPositions.add(new Beam(size, j, 3));
            startPositions.add(new Beam(j, -1, 2));
            startPositions.add(new Beam(j, size, 0));
        }

        int max = 0;

        for (Beam beam : startPositions) {

            int count = getCountForStartingBeam(beam);

            if (count > max) {
                max = count;
            }

        }

        System.out.println(max);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
