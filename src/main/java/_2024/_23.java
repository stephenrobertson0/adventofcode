package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;


public class _23 {

    private static Map<String, Set<String>> parseInput() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input23.txt"));

        Map<String, Set<String>> compAndNeighbours = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String comp1 = line.split("-")[0];
            String comp2 = line.split("-")[1];

            Set<String> neighbours = compAndNeighbours.getOrDefault(comp1, new HashSet<>());
            neighbours.add(comp2);
            compAndNeighbours.put(comp1, neighbours);

            Set<String> neighbours2 = compAndNeighbours.getOrDefault(comp2, new HashSet<>());
            neighbours2.add(comp1);
            compAndNeighbours.put(comp2, neighbours2);

        }

        return compAndNeighbours;
    }

    public static void a() throws Exception {

        Map<String, Set<String>> compAndNeighbours = parseInput();

        Set<Set<String>> uniqueThrees = new HashSet<>();

        for (Map.Entry<String, Set<String>> entry : compAndNeighbours.entrySet()) {

            for (String neighbour : entry.getValue()) {
                Set<String> neighbours = compAndNeighbours.get(neighbour);

                for (String neighbour2 : neighbours) {

                    Set<String> neighbours2 = compAndNeighbours.get(neighbour2);

                    if (neighbours2.contains(entry.getKey())) {

                        Set<String> unique3 = new HashSet<>();
                        unique3.add(entry.getKey());
                        unique3.add(neighbour);
                        unique3.add(neighbour2);

                        if (unique3.size() == 3) {
                            uniqueThrees.add(unique3);
                        }
                    }
                }

            }

        }

        //System.out.println(uniqueThrees);

        int count = 0;

        for (Set<String> set : uniqueThrees) {

            for (String comp : set) {
                if (comp.startsWith("t")) {
                    count++;
                    break;
                }
            }

        }

        System.out.println(count);

    }

    public static void b() throws Exception {

        Map<String, Set<String>> compAndNeighbours = parseInput();


        Set<Set<String>> all = new HashSet<>();


        for (Map.Entry<String, Set<String>> outerEntry : compAndNeighbours.entrySet()) {

            Stack<Set<String>> stack = new Stack<>();

            stack.push(Set.of(outerEntry.getKey()));

            while (!stack.isEmpty()) {
                Set<String> currentSet = stack.pop();

                for (Map.Entry<String, Set<String>> entry : compAndNeighbours.entrySet()) {

                    boolean allMatch = true;

                    for (String current : currentSet) {
                        if (!entry.getValue().contains(current)) {
                            allMatch = false;
                        }
                    }

                    if (allMatch) {
                        Set<String> newSet = new HashSet<>(currentSet);
                        newSet.add(entry.getKey());

                        if (!all.contains(newSet)) {
                            all.add(newSet);
                            stack.push(newSet);
                        }
                    }
                }

            }
        }

        int maxSize = 0;
        Set<String> maxResult = null;

        for (Set<String> result : all) {

            if (result.size() > maxSize) {

                maxSize = result.size();
                maxResult = result;
            }
        }

        System.out.println(maxResult.stream().sorted().collect(Collectors.joining(",")));

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
