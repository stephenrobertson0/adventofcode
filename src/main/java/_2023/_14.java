package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


public class _14 {

    private static void tiltNorth(char[][] rocks, int size) {
        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {

                int newYPos = 0;

                if (rocks[k][j] == 'O') {
                    for (int m = j-1; m >= 0; m--) {

                        if (rocks[k][m] != '.') {
                            newYPos = m+1;
                            break;
                        }

                    }

                    if (j != newYPos) {
                        rocks[k][newYPos] = rocks[k][j];
                        rocks[k][j] = '.';
                    }
                }
            }
        }
    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input14.txt"));

        int size = 100;

        char[][] rocks = new char[size][size];

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < line.length(); j++) {
                rocks[j][yIndex] = line.charAt(j);
            }

            yIndex++;

        }

        tiltNorth(rocks, size);

        long total = 0;

        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                if (rocks[k][j] == 'O') {

                    total += size-j;

                }
            }
        }

        System.out.println(total);

    }

    private static char[][] rotateClockWise(char[][] rocks, int size) {

        char[][] result = new char[size][size];

        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {

                result[size-1 - j][k] = rocks[k][j];

            }
        }

        return result;
    }

    private static String rocksToString(char[][] rocks, int size) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                stringBuilder.append(rocks[k][j]);
            }
        }

        return stringBuilder.toString();

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input14.txt"));

        int size = 100;

        char[][] rocks = new char[size][size];

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < line.length(); j++) {
                rocks[j][yIndex] = line.charAt(j);
            }

            yIndex++;

        }

        Map<String, Integer> map = new HashMap<>();

        int startPos = 0;
        int cycleSize = 0;

        char[][] rocksClone = rocks.clone();

        for (int k = 0; k < 10000; k++) {

            for (int j = 0; j < 4; j++) {
                tiltNorth(rocksClone, size);
                rocksClone = rotateClockWise(rocksClone, size);
            }

            if (map.containsKey(rocksToString(rocksClone, size))) {
                startPos = map.get(rocksToString(rocksClone, size)) + 1;
                cycleSize = k + 1 - startPos;
                break;
            }

            map.put(rocksToString(rocksClone, size), k);
        }

        for (int k = 0; k < startPos; k++) {

            for (int j = 0; j < 4; j++) {
                tiltNorth(rocks, size);
                rocks = rotateClockWise(rocks, size);
            }
        }

        long remainingCycles = (1000000000 - startPos) % cycleSize;

        for (int k = 0; k < remainingCycles; k++) {

            for (int j = 0; j < 4; j++) {
                tiltNorth(rocks, size);
                rocks = rotateClockWise(rocks, size);
            }
        }

        long total = 0;

        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                if (rocks[k][j] == 'O') {

                    total += size-j;

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
