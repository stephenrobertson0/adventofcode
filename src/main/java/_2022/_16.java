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
    
    public static void b() throws Exception {
    
    
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
