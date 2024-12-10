package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public class _10 {

    private static final int SIZE = 43;

    private static int[][] directions = new int[][] {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    private record Spot (int x, int y, int height, Spot previousSpot){}

    private static int getScore(Spot spot, Integer[][] grid) {

        Stack<Spot> spots = new Stack<>();
        spots.push(spot);

        Set<String> visited = new HashSet<>();

        while (!spots.empty()) {
            Spot focusSpot = spots.pop();

            for (int j = 0; j < directions.length; j++) {
                int x = focusSpot.x + directions[j][0];
                int y = focusSpot.y + directions[j][1];

                if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                    Spot neighbourSpot = new Spot(x, y, grid[x][y], null);

                    if (neighbourSpot.height - focusSpot.height == 1) {
                        if (neighbourSpot.height == 9) {
                            visited.add(neighbourSpot.x + " " + neighbourSpot.y);
                        } else {
                            spots.push(neighbourSpot);
                        }
                    }
                }
            }
        }

        return visited.size();
    }

    private static int getScoreV2(Spot spot, Integer[][] grid) {

        Stack<Spot> spots = new Stack<>();
        spots.push(spot);

        Set<String> visited = new HashSet<>();

        while (!spots.empty()) {
            Spot focusSpot = spots.pop();

            for (int j = 0; j < directions.length; j++) {
                int x = focusSpot.x + directions[j][0];
                int y = focusSpot.y + directions[j][1];

                if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                    Spot neighbourSpot = new Spot(x, y, grid[x][y], focusSpot);

                    if (neighbourSpot.height - focusSpot.height == 1) {
                        if (neighbourSpot.height == 9) {

                            String path = "";
                            Spot currentSpot = neighbourSpot;

                            for (int k = 0; k < 9; k++) {
                                path += currentSpot.x + " " + currentSpot.y;
                                currentSpot = currentSpot.previousSpot;
                            }

                            visited.add(path);
                        } else {
                            spots.push(neighbourSpot);
                        }
                    }
                }
            }
        }

        return visited.size();
    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input10.txt"));

        Integer[][] grid = new Integer[SIZE][SIZE];

        int yIndex = 0;
        List<Spot> trailHeads = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < SIZE; j++) {
                int height = Integer.parseInt("" + line.charAt(j));
                grid[j][yIndex] = height;

                if (height == 0) {
                    trailHeads.add(new Spot(j, yIndex, height, null));
                }
            }
            yIndex++;
        }

        int score = 0;

        for (Spot trailHead : trailHeads) {
            score += getScore(trailHead, grid);
        }

        System.out.println(score);

    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input10.txt"));

        Integer[][] grid = new Integer[SIZE][SIZE];

        int yIndex = 0;
        List<Spot> trailHeads = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < SIZE; j++) {
                int height = Integer.parseInt("" + line.charAt(j));
                grid[j][yIndex] = height;

                if (height == 0) {
                    trailHeads.add(new Spot(j, yIndex, height, null));
                }
            }
            yIndex++;
        }

        int score = 0;

        for (Spot trailHead : trailHeads) {
            score += getScoreV2(trailHead, grid);
        }

        System.out.println(score);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
