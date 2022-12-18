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
    
    private static int[][] board = new int[6][6];
    private static Node2[][] nodeBoard = new Node2[6][6];
    
    private static class Node2 {
        
        int x;
        int y;
        
        // 1 - Rock
        // 2 - Air
        int type;
        
        Set<Node2> neighbours = new HashSet<>();
        
        public Node2(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }
    
    static int[][] dirs = new int[][]{{0,1}, {0,-1}, {1,0}, {-1,0}};
    
    private static void setNodeAndNeighbourToExposed(Node2 node) {
        node.type = 2;
        board[node.x][node.y] = 2;
        
        for (Node2 neighbour : node.neighbours) {
            if (neighbour.type == 0) {
                setNodeAndNeighbourToExposed(neighbour);
            }
        }
    }
    
    public static void b() throws Exception {
        // 0 == Air Unknown
        // 1 == Rock
        // 2 == Air exposed to outside
        // 3 == Air not exposed to outside
        
        board[2][0] = 1;
        board[3][0] = 1;
        board[1][1] = 1;
        board[4][1] = 1;
        board[2][2] = 1;
        board[4][2] = 1;
        board[3][3] = 1;
        board[4][3] = 1;
        
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 6; k++) {
                System.out.print(board[k][j]);
            }
            System.out.println();
        }
        
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 6; k++) {
                nodeBoard[k][j] = new Node2(k, j, board[k][j]);
            }
        }
    
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 6; k++) {
                for (int m = 0; m < 4; m++) {
                    int x = k+dirs[m][0];
                    int y = j+dirs[m][1];
                    
                    if (x >=0 && x < 6 && y >=0 && y < 6) {
                        nodeBoard[k][j].neighbours.add(nodeBoard[x][y]);
                    }
                }
            }
        }
    
        setNodeAndNeighbourToExposed(nodeBoard[0][0]);
    
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 6; k++) {
                System.out.print(board[k][j]);
            }
            System.out.println();
        }
        
        
    }
    
    public static void main(String[] args) throws Exception {
        //a();
        b();
    }
}
