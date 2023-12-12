package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class _11 {

    private static class Point {
        int x;
        int y;

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

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input11.txt"));

        int xSize = 140;
        int ySize = 140;

        int[][] galaxy = new int[xSize][ySize];

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j =0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    galaxy[j][yIndex] = 1;
                } else {
                    galaxy[j][yIndex] = 0;
                }
            }

            yIndex ++;

        }

        // Expand y

        List<List<Integer>> galaxyAsList = new ArrayList<>();

        for (int k = 0; k < ySize; k++) {

            boolean allZero = true;

            for (int j = 0; j < xSize; j++) {
                if (galaxy[j][k] != 0) {
                    allZero = false;
                    break;
                }
            }

            List<Integer> galaxyLine = new ArrayList<>();

            for (int j = 0; j < xSize; j++) {
                galaxyLine.add(galaxy[j][k]);
            }

            galaxyAsList.add(galaxyLine);

            if (allZero) {
                galaxyAsList.add(galaxyLine);
            }
        }

        ySize = galaxyAsList.size();
        galaxy = new int[xSize][ySize];

        for (int k = 0; k < ySize; k++) {

            List<Integer> galaxyLine = galaxyAsList.get(k);

            for (int j = 0; j < xSize; j++) {
                galaxy[j][k] = galaxyLine.get(j);
            }
        }

        // Expand x

        galaxyAsList = new ArrayList<>();

        for (int j = 0; j < xSize; j++) {

            boolean allZero = true;

            for (int k = 0; k < ySize; k++) {
                if (galaxy[j][k] != 0) {
                    allZero = false;
                    break;
                }
            }

            List<Integer> galaxyLine = new ArrayList<>();

            for (int k = 0; k < ySize; k++) {
                galaxyLine.add(galaxy[j][k]);
            }

            galaxyAsList.add(galaxyLine);

            if (allZero) {
                galaxyAsList.add(galaxyLine);
            }
        }

        xSize = galaxyAsList.size();
        galaxy = new int[xSize][ySize];

        for (int j = 0; j < xSize; j++) {

            List<Integer> galaxyLine = galaxyAsList.get(j);

            for (int k = 0; k < ySize; k++) {
                galaxy[j][k] = galaxyLine.get(k);
            }
        }



        for (int k = 0; k < ySize; k++) {

            for (int j = 0; j < xSize; j++) {
                if (galaxy[j][k] == 1) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }

        List<Point> points = new ArrayList<>();

        for (int k = 0; k < ySize; k++) {

            for (int j = 0; j < xSize; j++) {
                if (galaxy[j][k] == 1) {
                    points.add(new Point(j, k));
                }
            }
        }

        int total = 0;

        for (int k = 0; k < points.size(); k++) {

            for (int j = k+1; j < points.size(); j++) {

                Point point1 = points.get(j);
                Point point2 = points.get(k);

                int distance = Math.abs(point1.getX() - point2.getX()) + Math.abs(point1.getY() - point2.getY());

                total += distance;
            }
        }

        System.out.println(total);

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input11.txt"));

        int xSize = 140;
        int ySize = 140;

        int[][] galaxy = new int[xSize][ySize];

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j =0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    galaxy[j][yIndex] = 1;
                } else {
                    galaxy[j][yIndex] = 0;
                }
            }

            yIndex ++;

        }

        // Find empty indexes

        Set<Integer> emptyX = new HashSet<>();

        for (int k = 0; k < ySize; k++) {

            boolean allZero = true;

            for (int j = 0; j < xSize; j++) {
                if (galaxy[j][k] != 0) {
                    allZero = false;
                    break;
                }
            }

            if (allZero) {
                emptyX.add(k);
            }
        }

        Set<Integer> emptyY = new HashSet<>();

        for (int j = 0; j < xSize; j++) {

            boolean allZero = true;

            for (int k = 0; k < ySize; k++) {
                if (galaxy[j][k] != 0) {
                    allZero = false;
                    break;
                }
            }

            if (allZero) {
                emptyY.add(j);
            }
        }



        List<Point> points = new ArrayList<>();

        for (int k = 0; k < ySize; k++) {

            for (int j = 0; j < xSize; j++) {
                if (galaxy[j][k] == 1) {
                    points.add(new Point(j, k));
                }
            }
        }

        long total = 0;

        for (int k = 0; k < points.size(); k++) {

            for (int j = k+1; j < points.size(); j++) {

                Point point1 = points.get(j);
                Point point2 = points.get(k);

                int xMin = Math.min(point1.getX(), point2.getX());
                int xMax = Math.max(point1.getX(), point2.getX());

                int yMin = Math.min(point1.getY(), point2.getY());
                int yMax = Math.max(point1.getY(), point2.getY());

                long distance = 0;

                for (int m = xMin; m < xMax; m++) {
                    distance++;

                    if (emptyY.contains(m)) {
                        distance += 999999;
                    }
                }

                for (int m = yMin; m < yMax; m++) {
                    distance++;

                    if (emptyX.contains(m)) {
                        distance += 999999;
                    }
                }

                total += distance;
            }
        }

        System.out.println(total);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
