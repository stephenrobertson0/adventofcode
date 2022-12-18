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
        
        for (int j = 0; j < allNodes.size(); j ++) {
            for (int k = j + 1; k < allNodes.size(); k ++) {
                
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
    
    public static void b() throws Exception {
        
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
