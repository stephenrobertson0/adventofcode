package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class _16 {
    
    private static class Node {
        int value;
        String name;
        List<Node> neighbours = new ArrayList<>();
        String neighbourNodeString;
        boolean isOpen = false;
        int distance = 1;
        double reward = -1;
        
        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name +
                    //", nodes=" + neighbours.stream().map(v->v.name).collect(Collectors.joining(",")) +
                    ", value=" + value +
                    ", reward=" + reward +
                    ", distance=" + distance +
                    '}';
        }
        
        public int getMax(int minutesLeft, Set<Node> allOpen, int level) {
            if (minutesLeft <= 0) {
                return 0;
            }
            
            if (allOpen.size() == 6) {
                int total = 0;
                
                for (Node open : allOpen) {
                    total+= open.value * (minutesLeft-2);
                }
                
                return total;
            }
            
            // Don't open can mean already open
            int dontOpenOption = 0;
            int openOption = -1;

            int maxChildVal = 0;

            for (Node child : neighbours) {
                int max = child.getMax(minutesLeft-1, allOpen, level+1);
                if (max > maxChildVal) {
                    maxChildVal = max;
                }
            }
    
            dontOpenOption = maxChildVal;
    
            if (level == 1) {
                System.out.println();
            }
            
            if (!isOpen && value != 0 && minutesLeft >=2) {
                
                int maxCVal = 0;
        
                this.isOpen = true;
                allOpen.add(this);
        
                for (Node child : neighbours) {
                    int max = child.getMax(minutesLeft-2, allOpen, level+1);
                    if (max > maxCVal) {
                        maxCVal = max;
                    }
                }
        
                openOption = maxCVal;
    
                this.isOpen = false;
                allOpen.remove(this);
        
            }
            
            if (dontOpenOption > openOption) {
                if (level == 1) {
                    System.out.println("Not opening: " + name);
                }
                
                return dontOpenOption;
            } else {
                if (level == 1) {
                    System.out.println("Opening: " + name);
                }
                
                this.isOpen = true;
                allOpen.add(this);
                return openOption + value*minutesLeft;
            }
        }
    }
    
    private static class NodeState {
    
        private Map<String, Node> allOpen;
        private Node node;
        private long totalFlow;
        
        public NodeState(Map<String, Node> allOpen, Node node, long totalFlow) {
            this.allOpen = allOpen;
            this.node = node;
            this.totalFlow = totalFlow;
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
        
        Set<NodeState> states = new HashSet<>();
        
        states.add(new NodeState(new HashMap<>(), startNode, 0));
        
        for (int j = 0; j < 30; j++) {
    
            Set<NodeState> newStates = new HashSet<>();
            
            for (NodeState state : states) {
                
                long flow = state.allOpen.values().stream().mapToLong(e -> e.value).sum() + state.totalFlow;
    
                if(state.node.value > 0 && !state.allOpen.containsKey(state.node.name)) {
                    Map<String, Node> newOpen = new HashMap<>(state.allOpen);
                    newOpen.put(state.node.name, state.node);
                    newStates.add(new NodeState(newOpen, state.node, flow));
                }
                
                for (int k = 0; k < state.node.neighbours.size(); k++) {
                    newStates.add(new NodeState(state.allOpen, state.node.neighbours.get(k), flow));
                }
            }
            
            states = newStates;
        
        }
    
        System.out.println(states.stream().mapToLong(v->v.totalFlow).max().getAsLong());
        
        //int distanceTravelled = 0;
        
        /*while (true) {
            
            if (distanceTravelled > 30) {
                break;
            }
            
            Set<Node> alreadyVisited = new HashSet<>();
    
            PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.distance - o2.distance);
    
            queue.add(startNode);
            startNode.distance = 0;
    
            while (!queue.isEmpty()) {
                Node currentNode = queue.poll();
        
                if (!alreadyVisited.contains(currentNode)) {
                    for (Node neighbour : currentNode.neighbours) {
                
                        if (neighbour.value / (currentNode.distance + 1) > neighbour.reward) {
                            neighbour.reward = neighbour.value / (currentNode.distance + 1);
                            neighbour.distance = currentNode.distance + 1;
                        }
                
                        queue.add(neighbour);
                    }
            
                    alreadyVisited.add(currentNode);
                }
        
            }
    
            for (Node node : allNodes) {
                System.out.println(node);
            }
    
            Collections.sort(allNodes, (x,y) -> Double.compare(y.reward, x.reward));
            
            Node destination = allNodes.get(0);
            
            System.out.println("Destination picked: " + destination);
            
            destination.value=0;
            distanceTravelled += destination.distance;
            distanceTravelled += 1;
            startNode = destination;
            
            for (Node node : allNodes) {
                node.reward = -1;
                node.distance = 1;
            }
        }*/
        
        //simulateScenarios(allNodesByName);
        
        /*int[][] dists = new int[allNodes.size()][allNodes.size()];
        
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
    
        for (int j = 0; j < dists.length; j++) {
            for (int k = 0; k < dists.length; k++) {
    
                System.out.print(dists[j][k] + " ");
            
            }
    
            System.out.println();
        }
    
        for (int k = 0; k < dists.length; k++) {
            for (int i = 0; i < dists.length; i++) {
                for (int j = 0; j < dists.length; j++) {
                    if (dists[i][k] + dists[k][j] < dists[i][j])
                        dists[i][j] = dists[i][k] + dists[k][j];
                }
            }
        }
    
        System.out.println();
        
        for (int j = 0; j < dists.length; j++) {
            for (int k = 0; k < dists.length; k++) {
            
                System.out.print(dists[j][k] + " ");
            
            }
        
            System.out.println();
        }
        
        List<Node> nonZeroNodes = new ArrayList<>();
        
        for (Node node : allNodes) {
            if (node.value != 0) {
                nonZeroNodes.add(node);
            }
        }
        */
        
        
        //System.out.println(allNodes);
    
        //System.out.println(startNode.getMax(30, new HashSet<>(), 0));
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
