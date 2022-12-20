package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class _16 {
    
    private static class Edge {
        private Node dest;
        private int distance;
    
        public Edge(Node dest, int distance) {
            this.dest = dest;
            this.distance = distance;
        }
    }
    
    private static class Node {
        int value;
        String name;
        List<Node> neighbours = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        String neighbourNodeString;
        
        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name +
                    //", nodes=" + neighbours.stream().map(v->v.name).collect(Collectors.joining(",")) +
                    ", value=" + value +
                    '}';
        }
    }
    
    private static int getMax(int minutesLeft, Node startNode, Set<Node> alreadyOpened) {
        
        if (minutesLeft <= 0) {
            return 0;
        }
        
        int max = 0;
        Node selectedNode = null;
        
        for (Edge edge : startNode.edges) {
            
            if (edge.distance < minutesLeft && !alreadyOpened.contains(edge.dest)) {
                Set<Node> newAlreadyOpened = new HashSet<>(alreadyOpened);
                newAlreadyOpened.add(edge.dest);
                
                int amount = alreadyOpened.stream().mapToInt(v->v.value).sum() * (edge.distance + 1)
                        + getMax(minutesLeft - edge.distance - 1, edge.dest, newAlreadyOpened);
                if (amount > max) {
                    max = amount;
                    selectedNode = edge.dest;
                }
            }
        }
        
        if (selectedNode == null) {
            return max + alreadyOpened.stream().mapToInt(v->v.value).sum() * minutesLeft;
        } else {
            return max;
        }
        
    }
    
    private static class St {
        int minutesLeft;
        int minutes1InBag;
        int minutes2InBag;
        Node startNode1;
        Node startNode2;
        Set<Node> alreadyOpened;
    
        public St(
                int minutesLeft,
                int minutes1InBag,
                int minutes2InBag,
                Node startNode1,
                Node startNode2,
                Set<Node> alreadyOpened) {
            this.minutesLeft = minutesLeft;
            this.minutes1InBag = minutes1InBag;
            this.minutes2InBag = minutes2InBag;
            this.startNode1 = startNode1;
            this.startNode2 = startNode2;
            this.alreadyOpened = alreadyOpened;
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            St st = (St)o;
            return minutesLeft == st.minutesLeft && minutes1InBag == st.minutes1InBag
                    && minutes2InBag == st.minutes2InBag
                    && Objects.equals(startNode1, st.startNode1) && Objects.equals(
                    startNode2,
                    st.startNode2) && Objects.equals(alreadyOpened, st.alreadyOpened);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(minutesLeft, minutes1InBag, minutes2InBag, startNode1, startNode2, alreadyOpened);
        }
    }
    
    static Map<St, Integer> cache = new HashMap<>();
    
    private static int getMax2(int minutesLeft, int minutes1InBag, int minutes2InBag, Node startNode1, Node startNode2, Set<Node> alreadyOpened) {
    
        St key = new St(minutesLeft, minutes1InBag, minutes2InBag, startNode1, startNode2, alreadyOpened);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        
        if (minutesLeft <= 0) {
            return 0;
        }
        
        int max = 0;
        
        for (Edge edge1 : startNode1.edges) {
            for (Edge edge2 : startNode2.edges) {
                
                if (edge1.dest == edge2.dest) {
                    continue;
                }
    
                if (edge1.distance <= minutes1InBag && !alreadyOpened.contains(edge1.dest) && edge2.distance <= minutes2InBag && !alreadyOpened.contains(edge2.dest)) {
                    Set<Node> newAlreadyOpened = new HashSet<>(alreadyOpened);
                    newAlreadyOpened.add(edge1.dest);
                    newAlreadyOpened.add(edge2.dest);
    
                    //System.out.println("Opening: " + edge1.dest + " and " + edge2.dest );
                    
                    int amount = getMax2(minutesLeft - 1, minutes1InBag - edge1.distance, minutes2InBag - edge2.distance, edge1.dest, edge2.dest, newAlreadyOpened);
                    if (amount > max) {
                        max = amount;
                    }
                    
                } else if (edge1.distance <= minutes1InBag && !alreadyOpened.contains(edge1.dest)) {
    
                    //System.out.println("Opening 1: " + edge1.dest);
                    
                    Set<Node> newAlreadyOpened = new HashSet<>(alreadyOpened);
                    newAlreadyOpened.add(edge1.dest);
        
                    int amount = getMax2(minutesLeft - 1, minutes1InBag - edge1.distance, minutes2InBag+1, edge1.dest, startNode2, newAlreadyOpened);
                    if (amount > max) {
                        max = amount;
                    }
                } else if (edge2.distance <= minutes2InBag && !alreadyOpened.contains(edge2.dest)) {
    
                    //System.out.println("Opening 2: " + edge1.dest);
                    
                    Set<Node> newAlreadyOpened = new HashSet<>(alreadyOpened);
                    newAlreadyOpened.add(edge2.dest);
    
                    int amount = getMax2(minutesLeft - 1, minutes1InBag+1,minutes2InBag - edge2.distance, startNode1, edge2.dest, newAlreadyOpened);
                    if (amount > max) {
                        max = amount;
                    }
                } else {
                    int amount = getMax2(minutesLeft - 1, minutes1InBag+1,minutes2InBag+1, startNode1, startNode2, alreadyOpened);
                    if (amount > max) {
                        max = amount;
                    }
                }
                
            }
        }
    
        int i = alreadyOpened.stream().mapToInt(v -> v.value).sum() + max;
        
        cache.put(new St(minutesLeft, minutes1InBag, minutes2InBag, startNode1, startNode2, alreadyOpened), i);
        
        return i;
        
    }
    
    public static void a() throws Exception {
    
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input16.txt"));
        
        List<Node> allNodes = new ArrayList<>();
        Map<String, Node> allNodesByName = new HashMap<>();
        
        Node startNode = null;
        
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
            
            String valveName = line.substring(6, 8);
            String rate = line.substring(line.indexOf('=')+1, line.indexOf(';'));
            
            int index = line.indexOf("valves");
            
            if (index == -1) {
                index = line.indexOf("valve") + 6;
            } else {
                index += 7;
            }
    
            String toValves = line.substring(index);
    
            Node node = new Node();
            node.name = valveName;
            node.value = Integer.parseInt(rate);
            node.neighbourNodeString = toValves;
            
            allNodes.add(node);
            allNodesByName.put(valveName, node);
            
            if (valveName.equals("AA")) {
                startNode = node;
            }
        }
        
        for (Node node : allNodes) {
            String[] split = node.neighbourNodeString.split(", ");
            for (String s : split) {
                node.neighbours.add(allNodesByName.get(s));
            }
        }
        
        //simulateScenarios(allNodesByName);
        
        // Floyd–Warshall to get minimum distances between all pairs
        
        int[][] dists = new int[allNodes.size()][allNodes.size()];
        
        for (int j = 0; j < dists.length; j++) {
            for (int k = 0; k < dists.length; k++) {
                if (j == k) {
                    dists[j][k] = 0;
                } else {
                    Node node1 = allNodes.get(j);
                    Node node2 = allNodes.get(k);
                    
                    if (node1.neighbours.contains(node2)) {
                        dists[j][k] = 1;
                    } else {
                        dists[j][k] = 1_000_000;
                    }
                }
            }
        }
    
        for (int k = 0; k < dists.length; k++) {
            for (int i = 0; i < dists.length; i++) {
                for (int j = 0; j < dists.length; j++) {
                    if (dists[i][k] + dists[k][j] < dists[i][j])
                        dists[i][j] = dists[i][k] + dists[k][j];
                }
            }
        }
    
        /*System.out.println();
        
        for (int j = 0; j < dists.length; j++) {
            for (int k = 0; k < dists.length; k++) {
                System.out.print(dists[j][k] + " ");
            }
        
            System.out.println();
        }*/
    
        for (int j = 0; j < allNodes.size(); j++) {
            for (int k = 0; k < allNodes.size(); k++) {
                if (j != k && (allNodes.get(k).value != 0 || allNodes.get(k) == startNode)) {
                    allNodes.get(j).edges.add(new Edge(allNodes.get(k), dists[j][k]));
                }
            }
        }
        
        System.out.println(getMax(30, startNode, new HashSet<>()));
    }
    
    private static void simulateScenarios(Map<String, Node> allNodesByName) {
        //List<String> moves = Arrays.asList("DD", "openDD", "EE", "openEE", "FF", "GG", "HH", "openHH", "GG", "FF", "EE", "DD", "AA", "II", "JJ", "openJJ", "II", "AA", "BB", "openBB", "CC", "openCC");
        //List<String> moves = Arrays.asList("DD", "openDD", "EE", "FF", "GG", "HH", "openHH", "GG", "FF", "EE", "DD", "AA", "II", "JJ", "openJJ", "II", "AA", "BB", "openBB", "CC", "openCC", "DD", "EE", "openEE");
        //List<String> moves = Arrays.asList("II", "JJ", "openJJ", "II", "AA", "DD", "openDD", "EE", "FF", "GG", "HH", "openHH", "GG", "FF", "EE", "DD", "CC", "BB", "openBB", "CC", "openCC", "DD", "EE", "openEE");
        
        // Example
        List<String> moves = Arrays.asList("DD", "openDD", "CC", "BB", "openBB", "AA", "II", "JJ", "openJJ", "II", "AA", "DD", "EE", "FF", "GG", "HH", "openHH", "GG", "FF", "EE", "openEE", "DD", "CC", "openCC");
    
        //List<String> moves = Arrays.asList("DD", "openDD", "AA", "II", "JJ", "openJJ", "II", "AA", "BB", "openBB", "AA", "DD", "EE", "FF", "GG", "HH", "openHH", "GG", "FF", "EE", "openEE", "DD", "CC", "openCC");
        
        Set<String> allOpen = new HashSet<>();
        
        int totalPressure = 0;
        
        for (int j = 0; j < 30; j++) {
    
            System.out.println("== Minute " + (j+1) + " ==");
    
            Integer pressureAdded = allOpen.stream().map(v -> allNodesByName.get(v).value).reduce(0, (x, y) -> x + y);
            System.out.println("Adding: " + pressureAdded);
            
            totalPressure += pressureAdded;
            
            if (j >= moves.size()) {
                continue;
            }
            
            String move = moves.get(j);
            
            if (move.startsWith("open")) {
                String open = move.substring(move.indexOf("open") + 4);
                allOpen.add(open);
                System.out.println("You open: " + open);
            } else {
                System.out.println("You move to: " + move);
            }
            
        }
        
        System.out.println(totalPressure);
    }
    
    private static class State {
        
        private int minutesLeft;
        private int minutesAvailable1;
        private int minutesAvailable2;
        private Node location1;
        private Node location2;
        private Set<Node> openValves;
        private int pressureReleased;
        private String log = "";
    
        public State(
                int minutesLeft,
                int minutesAvailable1,
                int minutesAvailable2,
                Node location1,
                Node location2,
                Set<Node> openValves,
                int pressureReleased) {
            this.minutesLeft = minutesLeft;
            this.minutesAvailable1 = minutesAvailable1;
            this.minutesAvailable2 = minutesAvailable2;
            this.location1 = location1;
            this.location2 = location2;
            this.openValves = openValves;
            this.pressureReleased = pressureReleased;
        }
        
        public State clone() {
            State state = new State(minutesLeft,
                    minutesAvailable1,
                    minutesAvailable2,
                    location1,
                    location2,
                    new HashSet<>(openValves),
                    pressureReleased);
            //state.log = log;
            return state;
        }
    }
    
    public static void b() throws Exception {
    
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input16.txt"));
    
        List<Node> allNodes = new ArrayList<>();
        Map<String, Node> allNodesByName = new HashMap<>();
    
        Node startNode = null;
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            String valveName = line.substring(6, 8);
            String rate = line.substring(line.indexOf('=')+1, line.indexOf(';'));
        
            int index = line.indexOf("valves");
        
            if (index == -1) {
                index = line.indexOf("valve") + 6;
            } else {
                index += 7;
            }
        
            String toValves = line.substring(index);
        
            Node node = new Node();
            node.name = valveName;
            node.value = Integer.parseInt(rate);
            node.neighbourNodeString = toValves;
        
            allNodes.add(node);
            allNodesByName.put(valveName, node);
        
            if (valveName.equals("AA")) {
                startNode = node;
            }
        }
    
        for (Node node : allNodes) {
            String[] split = node.neighbourNodeString.split(", ");
            for (String s : split) {
                node.neighbours.add(allNodesByName.get(s));
            }
        }
    
        //simulateScenarios(allNodesByName);
    
        // Floyd–Warshall to get minimum distances between all pairs
    
        int[][] dists = new int[allNodes.size()][allNodes.size()];
    
        for (int j = 0; j < dists.length; j++) {
            for (int k = 0; k < dists.length; k++) {
                if (j == k) {
                    dists[j][k] = 0;
                } else {
                    Node node1 = allNodes.get(j);
                    Node node2 = allNodes.get(k);
                
                    if (node1.neighbours.contains(node2)) {
                        dists[j][k] = 1;
                    } else {
                        dists[j][k] = 1_000_000;
                    }
                }
            }
        }
    
        for (int k = 0; k < dists.length; k++) {
            for (int i = 0; i < dists.length; i++) {
                for (int j = 0; j < dists.length; j++) {
                    if (dists[i][k] + dists[k][j] < dists[i][j])
                        dists[i][j] = dists[i][k] + dists[k][j];
                }
            }
        }
    
        /*System.out.println();
        
        for (int j = 0; j < dists.length; j++) {
            for (int k = 0; k < dists.length; k++) {
                System.out.print(dists[j][k] + " ");
            }
        
            System.out.println();
        }*/
    
        for (int j = 0; j < allNodes.size(); j++) {
            for (int k = 0; k < allNodes.size(); k++) {
                if (j != k && (allNodes.get(k).value != 0 || allNodes.get(k) == startNode)) {
                    allNodes.get(j).edges.add(new Edge(allNodes.get(k), dists[j][k]));
                }
            }
        }
    
        Set<Node> alreadyOpen = new HashSet<>();
        alreadyOpen.add(startNode);
    
        /*State startState = new State(26, 0, 0, startNode, startNode, alreadyOpen, 0);
        
        Queue<State> allStates = new ArrayDeque<>();
        allStates.add(startState);
    
        List<State> endStates = new ArrayList<>();
    
        while (!allStates.isEmpty()) {
    
            State state = allStates.remove();
            
            boolean didMove = false;
    
            if (state.minutesLeft == 0) {
                endStates.add(state);
                continue;
            }
            
            for (Edge edge1 : state.location1.edges) {
                for (Edge edge2 : state.location2.edges) {
                    
                    if (edge1.dest == edge2.dest) {
                        continue;
                    }
                    
                    Node moveTo1 = null;
                    Node moveTo2 = null;
                    
                    if (edge1.distance <= state.minutesAvailable1 && !state.openValves.contains(edge1.dest)) {
                        moveTo1 = edge1.dest;
                    }
    
                    if (edge2.distance <= state.minutesAvailable2 && !state.openValves.contains(edge2.dest)) {
                        moveTo2 = edge2.dest;
                    }
                    
                    State newState = state.clone();
                    
                    newState.minutesLeft -= 1;
                    //newState.pressureReleased += state.openValves.stream().mapToInt(v->v.value).sum();
                    //newState.log += "Open valves: " + state.openValves + " adding: " + state.openValves.stream().mapToInt(v->v.value).sum() + "\n";
                    
                    if (moveTo1 != null) {
                        newState.location1 = moveTo1;
                        newState.minutesAvailable1 -= edge1.distance;
                        newState.openValves.add(moveTo1);
                        newState.pressureReleased += moveTo1.value * (newState.minutesLeft);
                        //newState.log += "Opening " + newState.location1 + "\n";
                        //newState.log += "Minutes left are now: " + newState.minutesLeft + "\n";
                    } else {
                        newState.minutesAvailable1 += 1;
                    }
    
                    if (moveTo2 != null) {
                        newState.location2 = moveTo2;
                        newState.minutesAvailable2 -= edge2.distance;
                        newState.openValves.add(moveTo2);
                        newState.pressureReleased += moveTo2.value * (newState.minutesLeft);
                        //newState.log += "Opening " + newState.location2 + "\n";
                        //newState.log += "Minutes left are now: " + newState.minutesLeft + "\n";
                    } else {
                        newState.minutesAvailable2 += 1;
                    }
                    
                    if (moveTo1 != null || moveTo2 != null) {
                        didMove = true;
                        allStates.add(newState);
                    }
        
                }
                
            }
            
            State newState = state.clone();
            newState.minutesLeft -= 1;
            //newState.log += "Nothing new opened, adding: " + state.openValves + ": " + state.openValves.stream().mapToInt(v->v.value).sum() + "\n";
            //newState.pressureReleased += state.openValves.stream().mapToInt(v->v.value).sum();
            newState.minutesAvailable1 += 1;
            newState.minutesAvailable2 += 1;
            
            allStates.add(newState);
        }
    
        System.out.println(endStates.stream().mapToInt(v->v.pressureReleased).max());
    
        int max = 0;
        State selectedState = null;
        
        for (State state : endStates) {
            if (state.pressureReleased > max) {
                max = state.pressureReleased;
                selectedState = state;
            }
            
        }
    
        System.out.println(selectedState.log);*/
        
        System.out.println(getMax2(26, 0, 0, startNode, startNode, alreadyOpen));
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
