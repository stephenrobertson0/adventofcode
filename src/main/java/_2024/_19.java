package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class _19 {

    private record Possible(boolean possible, long count){}

    private static Map<String, Possible> isPossible = new HashMap<>();

    private static Possible isPossible(String towel, List<String> patterns) {

        if (towel.isEmpty()) {
            return new Possible(true, 1);
        }

        if (isPossible.containsKey(towel)) {
            return isPossible.get(towel);
        }

        long possibilitiesCount = 0;

        for (String pattern : patterns) {

            if (towel.startsWith(pattern)) {

                Possible possible = isPossible(towel.substring(pattern.length()), patterns);
                if (possible.possible) {

                    possibilitiesCount+=possible.count;
                    isPossible.put(towel.substring(pattern.length()), new Possible(true, possible.count));
                }
            }

        }

        if (possibilitiesCount > 0) {
            return new Possible(true, possibilitiesCount);
        }

        isPossible.put(towel, new Possible(false, 0));

        return new Possible(false, 0);

    }

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input19.txt"));

        String line = fileReader.readLine();

        List<String> patterns = Arrays.stream(line.split(", ")).toList();

        fileReader.readLine();

        List<String> towels = new ArrayList<>();

        while (true) {
            line = fileReader.readLine();

            if (line == null) {
                break;
            }

            towels.add(line);

        }

        long count = 0;

        for (String towel : towels) {

            Possible possible = isPossible(towel, patterns);

            if (possible.possible) {
                count ++;
            }

        }

        System.out.println(count);
    }

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input19.txt"));

        String line = fileReader.readLine();

        List<String> patterns = Arrays.stream(line.split(", ")).toList();

        fileReader.readLine();

        List<String> towels = new ArrayList<>();

        while (true) {
            line = fileReader.readLine();

            if (line == null) {
                break;
            }

            towels.add(line);

        }

        long count = 0;

        for (String towel : towels) {

            Possible possible = isPossible(towel, patterns);

            if (possible.possible) {

                count += possible.count;

            }

        }

        System.out.println(count);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
