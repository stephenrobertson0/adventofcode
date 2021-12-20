package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;


public class _15 {
    
    private static class Node {
        int risk;
        List<Node> neighbours = new ArrayList<>();
        int distance = 1_000_000;
        
        public Node(int risk) {
            this.risk = risk;
        }
    }
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input15.txt")));
        
        int size = 100;
        
        int[][] board = new int[size][size];
        
        int yPos = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            for (int x = 0; x < line.length(); x++) {
                board[x][yPos] = Integer.parseInt("" + line.charAt(x));
            }
            
            yPos++;
        }
    
        Map<String, Node> allNodes = new HashMap<>();
        
        Node startNode = null;
        Node endNode = null;
        
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
    
                Node node = allNodes.get(x+"-"+y);
                if (node == null) {
                    node = new Node(board[x][y]);
                    allNodes.put(x+"-"+y, node);
                }
                
                if (x != 0) {
                    Node neighbour = allNodes.get(x-1+"-"+y);
                    if (neighbour == null) {
                        neighbour = new Node(board[x-1][y]);
                        allNodes.put(x-1+"-"+y, neighbour);
                    }
                    node.neighbours.add(neighbour);
                }
    
                if (x != size-1) {
                    Node neighbour = allNodes.get(x+1+"-"+y);
                    if (neighbour == null) {
                        neighbour = new Node(board[x+1][y]);
                        allNodes.put(x+1+"-"+y, neighbour);
                    }
                    node.neighbours.add(neighbour);
                }
    
                if (y != 0) {
                    Node neighbour = allNodes.get(x+"-"+(y-1));
                    if (neighbour == null) {
                        neighbour = new Node(board[x][y-1]);
                        allNodes.put(x+"-"+(y-1), neighbour);
                    }
                    node.neighbours.add(neighbour);
                }
    
                if (y != size-1) {
                    Node neighbour = allNodes.get(x+"-"+(y+1));
                    if (neighbour == null) {
                        neighbour = new Node(board[x][y+1]);
                        allNodes.put(x+"-"+(y+1), neighbour);
                    }
                    node.neighbours.add(neighbour);
                }
    
                if (x == 0 && y == 0) {
                    startNode = node;
                }
    
                if (x == size-1 && y == size-1) {
                    endNode = node;
                }
                
            }
        }
    
        startNode.distance = 0;
        
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.distance - o2.distance);
        
        queue.add(startNode);
        
        Set<Node> alreadyVisited = new HashSet<>();
        
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            
            if (!alreadyVisited.contains(currentNode)) {
                for (Node neighbour : currentNode.neighbours) {
        
                    if (currentNode.risk + currentNode.distance < neighbour.distance) {
                        neighbour.distance = currentNode.risk + currentNode.distance;
                    }
        
                    queue.add(neighbour);
                }
    
                alreadyVisited.add(currentNode);
            }
    
        }
    
        System.out.println(endNode.distance + endNode.risk - startNode.risk);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input15.txt")));
        
        int size = 500;
        
        int[][] board = new int[size][size];
        
        int yPos = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            for (int x = 0; x < line.length(); x++) {
                board[x][yPos] = Integer.parseInt("" + line.charAt(x));
            }
            
            yPos++;
        }
        
        // Complete board
        
        for (int x = size / 5; x < size; x++) {
            for (int y = 0; y < size / 5; y++) {
                int num = board[x - size / 5][y] + 1;
                
                if (num > 9) {
                    num = 1;
                }
                
                board[x][y] = num;
            }
        }
        
        for (int x = 0; x < size; x++) {
            for (int y = size / 5; y < size; y++) {
                int num = board[x][y - size / 5] + 1;
                
                if (num > 9) {
                    num = 1;
                }
                
                board[x][y] = num;
            }
        }
    
        Map<String, Node> allNodes = new HashMap<>();
    
        Node startNode = null;
        Node endNode = null;
    
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
            
                Node node = allNodes.get(x+"-"+y);
                if (node == null) {
                    node = new Node(board[x][y]);
                    allNodes.put(x+"-"+y, node);
                }
            
                if (x != 0) {
                    Node neighbour = allNodes.get(x-1+"-"+y);
                    if (neighbour == null) {
                        neighbour = new Node(board[x-1][y]);
                        allNodes.put(x-1+"-"+y, neighbour);
                    }
                    node.neighbours.add(neighbour);
                }
            
                if (x != size-1) {
                    Node neighbour = allNodes.get(x+1+"-"+y);
                    if (neighbour == null) {
                        neighbour = new Node(board[x+1][y]);
                        allNodes.put(x+1+"-"+y, neighbour);
                    }
                    node.neighbours.add(neighbour);
                }
            
                if (y != 0) {
                    Node neighbour = allNodes.get(x+"-"+(y-1));
                    if (neighbour == null) {
                        neighbour = new Node(board[x][y-1]);
                        allNodes.put(x+"-"+(y-1), neighbour);
                    }
                    node.neighbours.add(neighbour);
                }
            
                if (y != size-1) {
                    Node neighbour = allNodes.get(x+"-"+(y+1));
                    if (neighbour == null) {
                        neighbour = new Node(board[x][y+1]);
                        allNodes.put(x+"-"+(y+1), neighbour);
                    }
                    node.neighbours.add(neighbour);
                }
            
                if (x == 0 && y == 0) {
                    startNode = node;
                }
            
                if (x == size-1 && y == size-1) {
                    endNode = node;
                }
            
            }
        }
    
        startNode.distance = 0;
    
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.distance - o2.distance);
    
        queue.add(startNode);
    
        Set<Node> alreadyVisited = new HashSet<>();
    
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
        
            if (!alreadyVisited.contains(currentNode)) {
                for (Node neighbour : currentNode.neighbours) {
                
                    if (currentNode.risk + currentNode.distance < neighbour.distance) {
                        neighbour.distance = currentNode.risk + currentNode.distance;
                    }
                
                    queue.add(neighbour);
                }
            
                alreadyVisited.add(currentNode);
            }
        
        }
    
        System.out.println(endNode.distance + endNode.risk - startNode.risk);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}