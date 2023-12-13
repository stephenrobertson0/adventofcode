package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;
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

    private static List<Integer> getHashCountsIgnoringWildcards(String config) {

        List<Integer> result = new ArrayList<>();

        String s = config;

        s += ".";

        int count = 0;

        for (char c : s.toCharArray()) {

            if (c == '?') {
                break;
            }

            if (c == '#') {
                count++;
            }

            if (c == '.' && count > 0) {
                result.add(count);
                count = 0;
            }

        }

        //System.out.println("Hash counts: " + result);

        return result;

    }

    private static List<Integer> getCountsBetweenDots(String s, List<Integer> hashCounts) {

        s += ".";

        int hashCount = 0;

        for (int h : hashCounts) {
            hashCount+=h;
        }

        int strHashCount = 0;

        int j;

        for (j = 0; j < s.length(); j++) {
            if (s.charAt(j) == '#') {
                strHashCount++;
            }

            if (strHashCount == hashCount) {
                break;
            }
        }

        if (j != s.length()) {
            s = s.substring(j + 1);
        }

        List<Integer> result = new ArrayList<>();

        int count = 0;

        for (char c : s.toCharArray()) {

            if (c == '#' || c == '?') {
                count++;
            }

            if (c == '.' && count > 0) {
                result.add(count);
                count = 0;
            }

        }

        return result;
    }

    private static boolean couldFitIntoCounts(List<Integer> countsToMatch, List<Integer> counts) {

        int countsIndex = 0;

        for (int j = 0; j < countsToMatch.size(); j++) {

            if (countsIndex >= counts.size()) {
                return false;
            }

            if (countsToMatch.get(j) > counts.get(countsIndex)) {
                countsIndex++;
                j--;
                continue;
            }

            counts.set(countsIndex, counts.get(countsIndex) - countsToMatch.get(j) - 1);
        }

        return true;

    }

    private static int getMatchCount(Configuration configuration) {

        Stack<Configuration> toCheck = new Stack<>();
        toCheck.add(configuration);

        int count = 0;

        while (!toCheck.isEmpty()) {
            Configuration next = toCheck.pop();

            String str = next.getChars();

            if (!str.contains("?")) {

                if (getHashCounts(next.getChars()).equals(next.getNumbers())) {
                    count++;
                }

                continue;
            }

            String option1 = str.replaceFirst("\\?", ".");
            String option2 = str.replaceFirst("\\?", "#");

            // Option 1

            List<Integer> hashCounts = getHashCountsIgnoringWildcards(option1);

            boolean couldMatch = true;

            for (int j = 0; j < hashCounts.size(); j++) {

                if (j >= next.getNumbers().size()) {
                    couldMatch = false;
                    break;
                }

                if (next.getNumbers().get(j) != hashCounts.get(j)) {
                    couldMatch = false;
                    break;
                }

            }

            if (couldMatch) {
                List<Integer> remainingNumbers = new ArrayList<>();

                for (int j = hashCounts.size(); j < next.getNumbers().size(); j++) {
                    remainingNumbers.add(next.getNumbers().get(j));
                }

                if (!couldFitIntoCounts(remainingNumbers, getCountsBetweenDots(option1, hashCounts))) {
                    couldMatch = false;
                }

            }

            //System.out.println("option1: " + option1);
            //System.out.println("hash counts: " + hashCounts);
            //System.out.println("numbers    : " + next.getNumbers());
            //System.out.println("couldMatch: " + couldMatch);
            //System.out.println();

            if (couldMatch) {
                toCheck.add(new Configuration(option1, next.getNumbers()));
            }

            // Option 2

            hashCounts = getHashCountsIgnoringWildcards(option2);

            couldMatch = true;

            for (int j = 0; j < hashCounts.size(); j++) {

                if (j >= next.getNumbers().size()) {
                    couldMatch = false;
                    break;
                }

                if (next.getNumbers().get(j) != hashCounts.get(j)) {
                    couldMatch = false;
                    break;
                }

            }

            if (couldMatch) {
                List<Integer> remainingNumbers = new ArrayList<>();

                for (int j = hashCounts.size(); j < next.getNumbers().size(); j++) {
                    remainingNumbers.add(next.getNumbers().get(j));
                }

                if (!couldFitIntoCounts(remainingNumbers, getCountsBetweenDots(option2, hashCounts))) {
                    couldMatch = false;
                }

            }

            //System.out.println("option2: " + option2);
            //System.out.println("hash counts: " + hashCounts);
            //System.out.println("numbers    : " + next.getNumbers());
            //System.out.println("couldMatch: " + couldMatch);
            //System.out.println();

            if (couldMatch) {
                toCheck.push(new Configuration(option2, next.getNumbers()));
            }

        }

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

            long startTime = System.currentTimeMillis();

            int matchCount = getMatchCount(configuration);
            System.out.println(matchCount);

            System.out.println("Time taken: " + (System.currentTimeMillis() - startTime));

            total += matchCount;

        }

        System.out.println();

        System.out.println(total);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();

        //System.out.println(getCountsBetweenQuestions(".#....##......##..........#..#.#..???..????????.???..????????.???..??", Arrays.asList(1, 2, 2, 1, 1, 1)));
        //System.out.println(couldFitIntoCounts(Arrays.asList(1, 1, 1), Arrays.asList(2, 2, 2)));
        //System.out.println(couldFitIntoCounts(Arrays.asList(1, 1, 1), Arrays.asList(2, 2)));
        //System.out.println(couldFitIntoCounts(Arrays.asList(2, 2, 2), Arrays.asList(1, 1, 1)));
    }
}
