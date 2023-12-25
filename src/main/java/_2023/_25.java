package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;


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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair pair = (Pair)o;
            return Objects.equals(item1, pair.item1) && Objects.equals(item2, pair.item2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(item1, item2);
        }
    }

    private static int getConnectedSize(Map<String, Set<String>> nodesByNeighbours) {
        Set<String> visited = new HashSet<>();

        Stack<String> stack = new Stack<>();

        stack.push(nodesByNeighbours.keySet().iterator().next());

        while (!stack.isEmpty()) {

            String value = stack.pop();

            if (visited.contains(value)) {
                continue;
            }

            visited.add(value);

            Set<String> neighbours = nodesByNeighbours.get(value);

            for (String neighbour : neighbours) {
                stack.push(neighbour);
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

    private static class State {
        private List<String> route;
        private int steps;

        public State(List<String> route, int steps) {
            this.route = route;
            this.steps = steps;
        }

        public List<String> getRoute() {
            return route;
        }

        public int getSteps() {
            return steps;
        }

        @Override
        public String toString() {
            return "State{" +
                    "route=" + route +
                    ", steps=" + steps +
                    '}';
        }
    }

    private static State getShortestPath(Pair pair, Map<String, Set<String>> nodesByNeighbours) {
        String start = pair.getItem1();
        String end = pair.getItem2();

        Set<String> visited = new HashSet<>();

        Queue<State> queue = new PriorityQueue<>((x,y)->new Integer(x.getSteps()).compareTo(y.getSteps()));

        queue.add(new State(Arrays.asList(start), 0));

        while (!queue.isEmpty()) {

            State state = queue.remove();

            String value = state.getRoute().get(state.getRoute().size()-1);

            if (value.equals(end)) {
                return state;
            }

            if (visited.contains(value)) {
                continue;
            }

            visited.add(value);

            Set<String> neighbours = nodesByNeighbours.get(value);

            for (String neighbour : neighbours) {

                if (!visited.contains(neighbour)) {
                    List<String> newRoute = new ArrayList<>(state.getRoute());
                    newRoute.add(neighbour);
                    queue.add(new State(newRoute, state.getSteps()+1));
                }
            }

        }

        return null;
    }

    private static Pair getMostTraversed(Map<String, Set<String>> nodesByNeighbours, Set<String> allNodes) {
        Map<Pair, Integer> counts = new HashMap<>();

        for (int j = 0; j < 10; j++) {

            List<String> nodes = new ArrayList<>(allNodes);

            String start = nodes.get((int)(Math.random() * nodes.size()));

            for (int k = 0; k < nodes.size(); k++) {
                String end = nodes.get(k);

                State state = getShortestPath(new Pair(start, end), nodesByNeighbours);

                for (int m = 0; m < state.getRoute().size()-1; m++) {

                    List<String> route =
                            Arrays.asList(state.getRoute().get(m), state.getRoute().get(m + 1)).stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());

                    Pair pair = new Pair(route.get(0), route.get(1));

                    Integer count = counts.get(pair);

                    if (count == null) {
                        counts.put(pair, 1);
                    } else {
                        counts.put(pair, count + 1);
                    }

                }
            }

        }

        return counts.entrySet().stream().sorted((x,y)->new Integer(y.getValue()).compareTo(x.getValue())).collect(Collectors.toList()).get(0).getKey();
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

        for (int j = 0; j < 3; j++) {

            Map<String, Set<String>> nodesByNeighbours = getNodesByNeighbours(pairs);

            Pair mostTraversed = getMostTraversed(nodesByNeighbours, allNodes);

            pairs.remove(new Pair(mostTraversed.getItem1(), mostTraversed.getItem2()));
            pairs.remove(new Pair(mostTraversed.getItem2(), mostTraversed.getItem1()));

            //System.out.println("Removing: " + mostTraversed);
        }

        Map<String, Set<String>> nodesByNeighbours = getNodesByNeighbours(pairs);

        int size = getConnectedSize(nodesByNeighbours);

        System.out.println(size * (allNodes.size()-size));

    }
    
    public static void main(String[] args) throws Exception {
        a();
    }
}
