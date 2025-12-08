package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class _7 {

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input7.txt"));

        int gridXSize = 142;
        int gridYSize = 142;

        char[][] grid = new char[gridYSize][gridXSize];
        int count = 0;
        int startPos = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            if (line.contains("S")) {
                startPos = line.indexOf("S");
            }

            for (int i = 0; i < line.length(); i++) {
                grid[count][i] = line.charAt(i);
            }

            count++;

        }

        Set<Integer> xPos = new HashSet<>();
        xPos.add(startPos);

        int splitCount = 0;

        for (int j = 1; j < gridYSize; j++) {

            Set<Integer> newXPos = new HashSet<>();

            for (int k = 0; k < gridXSize; k++) {

                if (xPos.contains(k)) {
                    if (grid[j][k] == '^') {
                        newXPos.add(k - 1);
                        newXPos.add(k + 1);
                        splitCount++;
                    } else {
                        newXPos.add(k);
                    }
                }
            }

            xPos = new HashSet<>(newXPos);
        }

        System.out.println(splitCount);

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input7.txt"));

        int gridXSize = 142;
        int gridYSize = 142;

        char[][] grid = new char[gridYSize][gridXSize];
        int count = 0;
        int startPos = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            if (line.contains("S")) {
                startPos = line.indexOf("S");
            }

            for (int i = 0; i < line.length(); i++) {
                grid[count][i] = line.charAt(i);
            }

            count++;

        }

        Map<Integer, Long> xPos = new HashMap<>();
        xPos.put(startPos, 1L);

        for (int j = 1; j < gridYSize; j++) {

            Map<Integer, Long> newXPos = new HashMap<>();

            for (int k = 0; k < gridXSize; k++) {

                if (xPos.containsKey(k)) {
                    if (grid[j][k] == '^') {
                        newXPos.put(k - 1, Optional.ofNullable(newXPos.get(k - 1)).orElse(0L) + xPos.get(k));
                        newXPos.put(k + 1, Optional.ofNullable(newXPos.get(k + 1)).orElse(0L) + xPos.get(k));
                    } else {
                        newXPos.put(k, Optional.ofNullable(newXPos.get(k)).orElse(0L) + xPos.get(k));
                    }
                }
            }

            xPos = new HashMap<>(newXPos);
        }

        System.out.println(xPos.values().stream().reduce(0L, Long::sum));
    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
