package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class _25 {

    private static class Pair {
        private String item1;
        private String item2;

        public Pair(String item1, String item2) {
            this.item1 = item1;
            this.item2 = item2;
        }

        public String getItem1() {
            return item1;
        }

        public String getItem2() {
            return item2;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "item1='" + item1 + '\'' +
                    ", item2='" + item2 + '\'' +
                    '}';
        }
    }

    private static int getConnectedSize(Set<String> allNodes, Map<String, Set<String>> nodesByNeighbours) {
        Set<String> visited = new HashSet<>();

        Stack<String> stack = new Stack<>();

        stack.push(allNodes.iterator().next());

        while (!stack.isEmpty()) {

            String value = stack.pop();

            if (visited.contains(value)) {
                continue;
            }

            visited.add(value);

            Set<String> neighbours = nodesByNeighbours.get(value);

            for (String neighbour : neighbours) {

                if (!visited.contains(neighbour)) {
                    stack.push(neighbour);
                    visited.add(neighbour);
                }
            }

        }

        return visited.size();
    }

    private static Map<String, Set<String>> getNodesByNeighbours(List<Pair> pairs) {

        Map<String, Set<String>> nodesByNeighbours = new HashMap<>();

        for (Pair pair : pairs) {

            Set<String> set1 = nodesByNeighbours.get(pair.getItem1());
            if (set1 == null) {
                set1 = new HashSet<>();
                nodesByNeighbours.put(pair.getItem1(), set1);
            }
            set1.add(pair.getItem2());

            Set<String> set2 = nodesByNeighbours.get(pair.getItem2());
            if (set2 == null) {
                set2 = new HashSet<>();
                nodesByNeighbours.put(pair.getItem2(), set2);
            }
            set2.add(pair.getItem1());

        }

        return nodesByNeighbours;
    }

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input25.txt"));

        Set<String> allNodes = new HashSet<>();
        List<Pair> pairs = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String node = line.split(":")[0];
            String[] neighbours = line.split(":")[1].trim().split(" ");

            allNodes.add(node);

            for (String neighbour : neighbours) {
                allNodes.add(neighbour);
                pairs.add(new Pair(node, neighbour));
            }

        }

        System.out.println(allNodes);
        System.out.println(pairs);

        int result = 0;

        for (int j = 0; j < pairs.size()-2; j++) {
            for (int k = j+1; k < pairs.size()-1; k++) {
                for (int m = k+1; m < pairs.size(); m++) {

                    List<Pair> pairClone = new ArrayList<>(pairs);
                    pairClone.remove(pairs.get(j));
                    pairClone.remove(pairs.get(k));
                    pairClone.remove(pairs.get(m));

                    Map<String, Set<String>> nodesByNeighbours = getNodesByNeighbours(pairClone);

                    int connectedSize = getConnectedSize(allNodes, nodesByNeighbours);

                    if (connectedSize != allNodes.size()) {
                        result = connectedSize;
                    }

                }
            }
        }

        System.out.println(result * (allNodes.size()-result));
    }

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input25.txt"));

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

        }

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
