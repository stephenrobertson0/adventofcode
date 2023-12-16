package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class _12 {

    private static class Configuration {
        private String chars;
        private List<Integer> numbers;

        public Configuration(String chars, List<Integer> numbers) {
            this.chars = chars;
            this.numbers = numbers;
        }

        public String getChars() {
            return chars;
        }

        public List<Integer> getNumbers() {
            return numbers;
        }
    }

    private static List<char[]> getAllConfigs(char[] chars) {
        List<Integer> charPositions = new ArrayList<>();

        for (int j = 0; j < chars.length; j++) {

            char c = chars[j];

            if (c == '?') {
                charPositions.add(j);
            }
        }

        List<char[]> result = new ArrayList<>();

        for (int j = 0; j < Math.pow(2, charPositions.size()); j++) {

            char[] charsClone = chars.clone();

            String binaryString = Integer.toBinaryString(j);

            binaryString = IntStream.range(0, charPositions.size() - binaryString.length()).boxed().map(v->"0").collect(Collectors.joining()) + binaryString;

            char[] binaryStringChars = binaryString.toCharArray();

            for (int k = 0; k < binaryStringChars.length; k++) {

                char b = binaryStringChars[k];

                if (b == '0') {
                    charsClone[charPositions.get(k)] = '.';
                } else {
                    charsClone[charPositions.get(k)] = '#';
                }

            }

            result.add(charsClone);

        }

        return result;

    }

    private static List<Integer> getHashCounts(String config) {

        List<Integer> result = new ArrayList<>();

        String s = config;

        s += ".";

        int count = 0;

        for (char c : s.toCharArray()) {

            if (c == '#') {
                count++;
            }

            if (c == '.' && count > 0) {
                result.add(count);
                count = 0;
            }

        }

        return result;

    }

    private static Map<String, Long> cache = new HashMap<>();

    private static long getMatchCount(Configuration configuration, int currentPosInString, int currentPosInBlocks, int lengthOfCurrentHashes) {

        String key = currentPosInString + "," + currentPosInBlocks + "," + lengthOfCurrentHashes;

        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        if (currentPosInString == configuration.getChars().length()) {
            if (currentPosInBlocks == configuration.getNumbers().size() && lengthOfCurrentHashes == 0) {
                return 1;
            } else if (currentPosInBlocks == configuration.getNumbers().size() - 1 && configuration.getNumbers().get(currentPosInBlocks) == lengthOfCurrentHashes) {
                return 1;
            } else {
                return 0;
            }
        }

        long count = 0;

        for (char c : Arrays.asList('.', '#')) {

            if (c == configuration.getChars().charAt(currentPosInString) || configuration.getChars().charAt(currentPosInString) == '?') {
                if (c == '.' && lengthOfCurrentHashes == 0) {
                    count += getMatchCount(configuration, currentPosInString + 1, currentPosInBlocks, 0);
                } else if (c == '.' && lengthOfCurrentHashes > 0 && currentPosInBlocks < configuration.getNumbers().size()
                        && configuration.getNumbers().get(currentPosInBlocks) == lengthOfCurrentHashes) {
                    count += getMatchCount(configuration, currentPosInString + 1, currentPosInBlocks + 1, 0);
                } else if (c == '#') {
                    count += getMatchCount(configuration, currentPosInString + 1, currentPosInBlocks, lengthOfCurrentHashes + 1);
                }
            }

        }

        cache.put(key, count);

        return count;

    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input12.txt"));

        List<Configuration> configurations = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String chars = line.split(" ")[0];
            String numbers = line.split(" ")[1];

            configurations.add(new Configuration(chars, Arrays.stream(numbers.split(",")).map(v -> Integer.parseInt(v)).collect(Collectors.toList())));

        }

        long total = 0;

        for (Configuration configuration : configurations) {

            List<char[]> allConfigs = getAllConfigs(configuration.getChars().toCharArray());

            int count = 0;

            for (char[] config : allConfigs) {

                List<Integer> hashCounts = getHashCounts(new String(config));

                if (hashCounts.equals(configuration.getNumbers())) {
                    count++;
                }

            }

            total += count;

        }

        System.out.println(total);

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input12.txt"));

        List<Configuration> configurations = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String chars = line.split(" ")[0];
            String numbers = line.split(" ")[1];

            String newChars = IntStream.range(0, 5).boxed().map(v->chars).collect(Collectors.joining("?"));
            String newNumbers = IntStream.range(0, 5).boxed().map(v->numbers).collect(Collectors.joining(","));

            configurations.add(new Configuration(newChars, Arrays.stream(newNumbers.split(",")).map(v -> Integer.parseInt(v)).collect(Collectors.toList())));

        }

        long total = 0;

        System.out.println();

        for (Configuration configuration : configurations) {

            cache = new HashMap<>();

            long matchCount = getMatchCount(configuration, 0, 0, 0);

            total += matchCount;

        }

        System.out.println();

        System.out.println(total);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
