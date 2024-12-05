package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _4 {

    private static final int SIZE = 140;

    private static List<String> getWordsStartingAt(int x, int y, Character[][] grid) {

        List<String> words = new ArrayList<>();

        int[][] directions = new int[][] {{0, 1}, {1, 0}, {-1, 0}, {0, -1}, {-1, -1}, {1, -1}, {-1, 1}, {1, 1}};

        for (int j = 0; j < directions.length; j++) {

            boolean found = true;
            String str = "";

            for (int k = 0; k < 4; k++) {
                int xIndex = x + k * directions[j][0];
                int yIndex = y + k * directions[j][1];

                if (xIndex >= SIZE || xIndex < 0 || yIndex >= SIZE || yIndex < 0) {
                    found = false;
                    break;
                }

                str += grid[xIndex][yIndex];
            }

            if (found) {
                words.add(str);
            }
        }

        return words;
    }

    private static boolean isXmasSquareAt(int x, int y, Character[][] grid) {

        String str1 = "";
        String str2 = "";

        for (int k = 0; k < 3; k++) {
            int xIndex1 = x + k;
            int yIndex1 = y + k;

            if (xIndex1 >= SIZE || xIndex1 < 0 || yIndex1 >= SIZE || yIndex1 < 0) {
                return false;
            }

            int xIndex2 = x + 2 - k;
            int yIndex2 = y + k;

            if (xIndex2 >= SIZE || xIndex2 < 0 || yIndex2 >= SIZE || yIndex2 < 0) {
                return false;
            }

            str1 += grid[xIndex1][yIndex1];
            str2 += grid[xIndex2][yIndex2];
        }

        return ((str1.equals("MAS") || str1.equals("SAM")) && (str2.equals("MAS") || str2.equals("SAM")));
    }

    public static void a() throws Exception {

        Character[][] grid = new Character[SIZE][SIZE];

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input4.txt"));

        int yIndex = 0;

        while (true) {
            String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < SIZE; j++) {
                grid[j][yIndex] = line.charAt(j);
            }
            yIndex++;
        }

        int total = 0;

        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                total += getWordsStartingAt(j, k, grid).stream().filter(v->v.equals("XMAS")).count();
            }
        }

        System.out.println(total);

    }
    
    public static void b() throws Exception {
        Character[][] grid = new Character[SIZE][SIZE];

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input4.txt"));

        int yIndex = 0;

        while (true) {
            String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < SIZE; j++) {
                grid[j][yIndex] = line.charAt(j);
            }
            yIndex++;
        }

        int total = 0;

        for (int j = 0; j < 140; j++) {
            for (int k = 0; k < 140; k++) {

                if (isXmasSquareAt(j, k, grid)) {
                    total++;
                }
            }
        }

        System.out.println(total);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
