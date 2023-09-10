package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class _9 {

    private static class Node {
        private final String name;
        private Map<Node, Integer> neighbourAndDistance = new HashMap<>();

        public Node(String name) {
            this.name = name;
        }

        public Map<Node, Integer> getNeighbourAndDistance() {
            return neighbourAndDistance;
        }

        public void setNeighbourAndDistance(Map<Node, Integer> neighbourAndDistance) {
            this.neighbourAndDistance = neighbourAndDistance;
        }
    }

    private static Node parseNodes() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input9.txt"));

        Map<String, Node> nodesByName = new HashMap<>();

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            int index1 = line.indexOf(" to ");
            int index2 = line.indexOf(" = ");

            String name1 = line.substring(0, index1);
            String name2 = line.substring(index1+4, index2);
            String distance = line.substring(index2+3);

            Node node1 = nodesByName.get(name1);
            Node node2 = nodesByName.get(name2);

            if (node1 == null) {
                node1 = new Node(name1);
                nodesByName.put(name1, node1);
            }

            if (node2 == null) {
                node2 = new Node(name2);
                nodesByName.put(name2, node2);
            }

            node1.getNeighbourAndDistance().put(node2, Integer.parseInt(distance));
            node2.getNeighbourAndDistance().put(node1, Integer.parseInt(distance));

        }

        Node node0 = new Node("Zero");

        for (Node node : nodesByName.values()) {
            node0.getNeighbourAndDistance().put(node, 0);
        }

        return node0;
    }

    private static int getMinDistance(Node node, Set<Node> visited) {

        if (node.getNeighbourAndDistance().keySet().stream().map(n->!visited.contains(n) ? 1 : 0).reduce((n1, n2)-> n1 + n2).get() == 1) {
            return node.getNeighbourAndDistance().entrySet().stream().filter(n->!visited.contains(n.getKey())).findFirst().get().getValue();
        } else {
            List<Node> notVisited = node.getNeighbourAndDistance().keySet().stream().filter(n->!visited.contains(n)).collect(Collectors.toList());

            int minDistance = Integer.MAX_VALUE;

            for (Node notVisitedNode : notVisited) {
                Set<Node> newVisited = new HashSet<>(visited);
                newVisited.add(notVisitedNode);
                int distance = node.getNeighbourAndDistance().get(notVisitedNode);
                distance += getMinDistance(notVisitedNode, newVisited);

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }

            return minDistance;

        }
    }

    private static int getMaxDistance(Node node, Set<Node> visited) {

        if (node.getNeighbourAndDistance().keySet().stream().map(n->!visited.contains(n) ? 1 : 0).reduce((n1, n2)-> n1 + n2).get() == 1) {
            return node.getNeighbourAndDistance().entrySet().stream().filter(n->!visited.contains(n.getKey())).findFirst().get().getValue();
        } else {
            List<Node> notVisited = node.getNeighbourAndDistance().keySet().stream().filter(n->!visited.contains(n)).collect(Collectors.toList());

            int maxDistance = 0;

            for (Node notVisitedNode : notVisited) {
                Set<Node> newVisited = new HashSet<>(visited);
                newVisited.add(notVisitedNode);
                int distance = node.getNeighbourAndDistance().get(notVisitedNode);
                distance += getMaxDistance(notVisitedNode, newVisited);

                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }

            return maxDistance;

        }
    }

    private static void a() throws Exception {

        Node node0 = parseNodes();

        Set<Node> visited = new HashSet<>();
        System.out.println(getMinDistance(node0, visited));

    }
    
    private static void b() throws Exception {
        Node node0 = parseNodes();

        Set<Node> visited = new HashSet<>();
        System.out.println(getMaxDistance(node0, visited));
    }
    
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}