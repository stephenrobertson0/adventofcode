package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class _12 {
    
    private static class Node {
        
        List<Node> neighbours = new ArrayList<>();
        char value;
        int distance = 1_000_000;
        
        public Node(char value) {
            this.value = value;
        }
    }
    
    static int xCount = 159;
    static int yCount = 41;
    private static Node[][] allNodes = new Node[xCount][yCount];
    private static int[][] dirs = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    
    private static void setupNodes() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input12.txt"));
        
        int kk = 0;
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
            
                allNodes[j][kk] = new Node(c);
            }
        
            kk++;
        }
    
        for (int k = 0; k < yCount; k++) {
        
            for (int j = 0; j < xCount; j++) {
                Node node = allNodes[j][k];
            
                for (int m = 0; m < 4; m ++) {
                    int x = j + dirs[m][0];
                    int y = k + dirs[m][1];
                
                    if (x < 0 || y < 0 || x >= xCount || y >= yCount) {
                        continue;
                    }
                
                    Node otherNode = allNodes[x][y];
                
                    char nodeValue = node.value;
                    if (nodeValue == 'S') {
                        nodeValue = 'a';
                    }
                    if (nodeValue == 'E') {
                        nodeValue = 'z';
                    }
                
                    char otherNodeValue = otherNode.value;
                    if (otherNodeValue == 'S') {
                        otherNodeValue = 'a';
                    }
                    if (otherNodeValue == 'E') {
                        otherNodeValue = 'z';
                    }
                
                    if (otherNodeValue - 1 <= nodeValue) {
                        node.neighbours.add(otherNode);
                    }
                }
            }
        }
    }
    
    private static void printAllNodes() {
        for (int k = 0; k < yCount; k++) {
            
            for (int j = 0; j < xCount; j++) {
                Node node = allNodes[j][k];
                
                if (("" + node.distance).length() == 1) {
                    System.out.print(" ");
                }
                System.out.print(node.distance + " ");
            }
            
            System.out.println();
        }
    }
    
    public static void a() throws Exception {
        
        setupNodes();
    
        Node startNode = null;
        Node endNode = null;
    
        for (int k = 0; k < yCount; k++) {
        
            for (int j = 0; j < xCount; j++) {
                Node node = allNodes[j][k];
                
                if (node.value == 'S') {
                    startNode = node;
                }
            
                if (node.value == 'E') {
                    endNode = node;
                }
            }
        }
        
        // Dijkstra
    
        Queue<Node> queue = new ArrayDeque<>();
        
        queue.add(startNode);
        startNode.distance = 0;
    
        Set<Node> alreadyVisited = new HashSet<>();
    
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
        
            if (!alreadyVisited.contains(currentNode)) {
                for (Node neighbour : currentNode.neighbours) {
    
                    if (currentNode.distance + 1 < neighbour.distance) {
                        neighbour.distance = currentNode.distance + 1;
                    }
                    
                    queue.add(neighbour);
                }
            
                alreadyVisited.add(currentNode);
            }
        
        }
    
        printAllNodes();
    
        System.out.println(endNode.distance);
    }
    
    public static void b() throws Exception {
        setupNodes();
    
        List<Node> startNodes = new ArrayList<>();
        Node endNode = null;
    
        for (int k = 0; k < yCount; k++) {
        
            for (int j = 0; j < xCount; j++) {
                Node node = allNodes[j][k];
            
                if (node.value == 'S' || node.value == 'a') {
                    startNodes.add(node);
                    node.distance = 0;
                }
            
                if (node.value == 'E') {
                    endNode = node;
                }
            }
        }
    
        // Dijkstra
    
        Queue<Node> queue = new ArrayDeque<>();
    
        for (Node startNode : startNodes) {
            queue.add(startNode);
        }
        
        Set<Node> alreadyVisited = new HashSet<>();
    
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
        
            if (!alreadyVisited.contains(currentNode)) {
                for (Node neighbour : currentNode.neighbours) {
                
                    if (currentNode.distance + 1 < neighbour.distance) {
                        neighbour.distance = currentNode.distance + 1;
                    }
                
                    queue.add(neighbour);
                }
            
                alreadyVisited.add(currentNode);
            }
        
        }
    
        printAllNodes();
    
        System.out.println(endNode.distance);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
