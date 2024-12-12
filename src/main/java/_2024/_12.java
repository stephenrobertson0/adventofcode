package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public class _12 {

    private static final int SIZE = 140;
    private static int[][] directions = new int[][] { { 0, -1 }, { 1, 0 }, { 0, 1 }, { -1, 0 } };
    private record Input(Character[][] grid, List<Region> regions) {}
    private record XY(int x, int y) {}
    private record Region(Character c, Set<XY> points) {}
    private static final int PERIMETER_POINT_SPREAD_FACTOR = 4;

    private static Input parseInput() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input12.txt"));

        Character[][] grid = new Character[SIZE][SIZE];

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < SIZE; j++) {
                grid[j][yIndex] = line.charAt(j);
            }
            yIndex++;
        }

        // Isolate regions
        List<Region> regions = new ArrayList<>();
        Set<XY> visited = new HashSet<>();

        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {

                if (visited.contains(new XY(j, k))) {
                    continue;
                }

                char c = grid[j][k];

                Set<XY> regionPoints = new HashSet<>();
                regionPoints.add(new XY(j, k));

                Stack<XY> stack = new Stack<>();
                stack.push(new XY(j, k));
                visited.add(new XY(j, k));

                while (!stack.isEmpty()) {
                    XY xy = stack.pop();
                    for (int d = 0; d < directions.length; d++) {
                        int x = xy.x + directions[d][0];
                        int y = xy.y + directions[d][1];

                        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                            if (grid[x][y] == c && !regionPoints.contains(new XY(x, y))) {
                                regionPoints.add(new XY(x, y));
                                visited.add(new XY(x, y));
                                stack.push(new XY(x, y));
                            }
                        }
                    }
                }

                regions.add(new Region(c, regionPoints));
            }
        }

        return new Input(grid, regions);

    }

    public static void a() throws Exception {

        Input input = parseInput();
        Character[][] grid = input.grid;
        List<Region> regions = input.regions;

        long total = 0;

        for (Region region : regions) {

            Set<XY> set = region.points;
            char c = region.c;

            int perimeter = 0;

            for (XY xy : set) {
                for (int j = 0; j < directions.length; j++) {
                    int x = xy.x + directions[j][0];
                    int y = xy.y + directions[j][1];

                    if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                        if (grid[x][y] != c) {
                            perimeter += 1;
                        }
                    } else {
                        perimeter += 1;
                    }

                }
            }

            total += perimeter * region.points.size();
        }

        System.out.println(total);

    }

    private static List<Region> sideCounts(Set<XY> perimeterXYs) {

        Set<XY> perimeterVisited = new HashSet<>();
        List<Region> perimeterRegions = new ArrayList<>();

        for (XY perimeterXY : perimeterXYs) {

            if (perimeterVisited.contains(perimeterXY)) {
                continue;
            }

            Set<XY> perimeterRegion = new HashSet<>();

            Stack<XY> stack = new Stack<>();

            stack.push(perimeterXY);
            perimeterRegion.add(perimeterXY);
            perimeterVisited.add(perimeterXY);

            while (!stack.isEmpty()) {
                XY xy = stack.pop();
                for (int d = 0; d < directions.length; d++) {
                    int x = xy.x + directions[d][0] * PERIMETER_POINT_SPREAD_FACTOR;
                    int y = xy.y + directions[d][1] * PERIMETER_POINT_SPREAD_FACTOR;

                    if (perimeterXYs.contains(new XY(x, y)) && !perimeterRegion.contains(new XY(x, y))) {
                        perimeterRegion.add(new XY(x, y));
                        perimeterVisited.add(new XY(x, y));
                        stack.push(new XY(x, y));
                    }
                }
            }

            perimeterRegions.add(new Region(' ', perimeterRegion));

        }

        return perimeterRegions;
    }

    public static void b() throws Exception {
        Input input = parseInput();
        Character[][] grid = input.grid;
        List<Region> regions = input.regions;

        long total = 0;

        for (Region region : regions) {

            Set<XY> set = region.points;
            char c = region.c;

            Set<XY> perimeterXYs = new HashSet<>();

            for (XY xy : set) {
                for (int j = 0; j < directions.length; j++) {
                    int x = xy.x + directions[j][0];
                    int y = xy.y + directions[j][1];

                    int adjustedX = xy.x * PERIMETER_POINT_SPREAD_FACTOR + directions[j][0];
                    int adjustedY = xy.y * PERIMETER_POINT_SPREAD_FACTOR + directions[j][1];

                    if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                        if (grid[x][y] != c) {
                            perimeterXYs.add(new XY(adjustedX, adjustedY));
                        }
                    } else {
                        perimeterXYs.add(new XY(adjustedX, adjustedY));
                    }

                }
            }

            List<Region> perimeterRegions = sideCounts(perimeterXYs);

            total += perimeterRegions.size() * region.points.size();
        }

        System.out.println(total);
    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
