package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class _18 {
    
    private static class Node {
        
        int x;
        int y;
        int z;
        
        int type;
        
        Set<Node> neighbours = new HashSet<>();
        
        public Node(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    
    private static boolean areNodesTouching(Node node1, Node node2) {
        
        if (node1.x == node2.x && node1.y == node2.y) {
            return Math.abs(node1.z - node2.z) == 1;
        }
        
        if (node1.y == node2.y && node1.z == node2.z) {
            return Math.abs(node1.x - node2.x) == 1;
        }
        
        if (node1.x == node2.x && node1.z == node2.z) {
            return Math.abs(node1.y - node2.y) == 1;
        }
        
        return false;
    }
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input18.txt"));
        
        List<Node> allNodes = new ArrayList<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String[] split = line.split(",");
            
            allNodes.add(new Node(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }
        
        for (int j = 0; j < allNodes.size(); j++) {
            for (int k = j + 1; k < allNodes.size(); k++) {
                
                if (areNodesTouching(allNodes.get(j), allNodes.get(k))) {
                    allNodes.get(j).neighbours.add(allNodes.get(k));
                    allNodes.get(k).neighbours.add(allNodes.get(j));
                }
                
            }
        }
        
        int total = 0;
        
        for (Node node : allNodes) {
            total += 6 - node.neighbours.size();
        }
        
        System.out.println(total);
    }
    
    private static Node[][][] nodeBoard = new Node[25][25][25];
    
    static int[][] dirs = new int[][]{{1,0,0}, {-1,0,0}, {0,1,0}, {0,-1,0}, {0,0,1}, {0,0,-1}};
    
    private static void setNodeAndNeighbourToExposed(Node node) {
        node.type = 2;
        
        for (Node neighbour : node.neighbours) {
            if (neighbour.type == 0) {
                setNodeAndNeighbourToExposed(neighbour);
            }
        }
    }
    
    public static void b() throws Exception {
    
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input18.txt"));
    
        while (true) {
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            String[] split = line.split(",");
    
            // Add one to each coordinate because there are some zero indexes and we want the zero
            // index to represent air.
            Node node = new Node(Integer.parseInt(split[0])+1, Integer.parseInt(split[1])+1, Integer.parseInt(split[2])+1);
            node.type = 1;
            nodeBoard[node.x][node.y][node.z] = node;
        }
        
        // 0 == Air Unknown
        // 1 == Rock
        // 2 == Air exposed to outside
        // 3 == Air not exposed to outside
        
        for (int j = 0; j < 25; j++) {
            for (int k = 0; k < 25; k++) {
                for (int m = 0; m < 25; m++) {
                    if (nodeBoard[m][k][j] == null) {
                        nodeBoard[m][k][j] = new Node(m, k, j);
                    }
                }
            }
        }
    
        for (int j = 0; j < 25; j++) {
            for (int k = 0; k < 25; k++) {
                for (int m = 0; m < 25; m++) {
                    for (int n = 0; n < 6; n++) {
                        int x = m + dirs[n][0];
                        int y = k + dirs[n][1];
                        int z = j + dirs[n][2];
        
                        if (x >= 0 && x < 25 && y >= 0 && y < 25 && z >= 0 && z < 25) {
                            nodeBoard[m][k][j].neighbours.add(nodeBoard[x][y][z]);
                        }
                    }
                }
            }
        }
    
        setNodeAndNeighbourToExposed(nodeBoard[0][0][0]);
        
        /*for (int j = 0; j < 25; j++) {
            for (int k = 0; k < 25; k++) {
                for (int m = 0; m < 25; m++) {
                    if (nodeBoard[m][k][j].type == 0) {
                        nodeBoard[m][k][j].type = 3;
                        System.out.println("Bubble at: " + m + " " + k + " " + j);
                    }
                }
            }
        }*/
    
        int total = 0;
        
        for (int j = 0; j < 25; j++) {
            for (int k = 0; k < 25; k++) {
                for (int m = 0; m < 25; m++) {
                    if (nodeBoard[m][k][j].type == 1) {
                        total += nodeBoard[m][k][j].neighbours.stream().filter(v -> v.type == 2).count();
                    }
                }
            }
        }
    
        System.out.println(total);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
