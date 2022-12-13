package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class _13 {
    
    private static class Node {
        Node parent;
        List<Node> nodes = new ArrayList<>();
        Integer value;
    
        @Override
        public String toString() {
            return "Node{" +
                    "nodes=" + nodes +
                    ", value=" + value +
                    '}';
        }
    }
    
    private static int compareTo(Node left, Node right) {
        
        if (left.value != null && right.value != null) {
            //System.out.println("comparing: " + left.value + " and " + right.value);
            
            return right.value - left.value;
        }
        
        if (left.value == null && right.value == null) {
            
            int index = 0;
            
            while (true) {
                if (index >= left.nodes.size()) {
                    // Scenario where both lists ran out of items
                    if (index >= right.nodes.size()) {
                        return 0;
                    }
                    
                    return 1;
                }
    
                if (index >= right.nodes.size()) {
                    // Scenario where both lists ran out of items
                    if (index >= left.nodes.size()) {
                        return 0;
                    }
                    
                    return -1;
                }
        
                int compare = compareTo(left.nodes.get(index), right.nodes.get(index));
                if (compare != 0) {
                    return compare;
                }
                
                index++;
            }
        }
        
        if (left.value != null) {
            Node newLeft = new Node();
            newLeft.value = left.value;
            left.value = null;
            
            left.nodes.add(newLeft);
            
            return compareTo(left, right);
        }
    
        if (right.value != null) {
            Node newRight = new Node();
            newRight.value = right.value;
            right.value = null;
            
            right.nodes.add(newRight);
        
            return compareTo(left, right);
        }
    
        throw new RuntimeException();
    }
    
    private static Node parseNodes(String line) {
        
        Node current = new Node();
        
        for (int j = 0; j < line.length(); j ++) {
            char c = line.charAt(j);
            
            if (c == '[') {
                Node node = new Node();
                node.parent = current;
                current.nodes.add(node);
                current = node;
            } else if (c == ',') {
                continue;
            } else if (c == ']') {
                current = current.parent;
            } else {
                Node node = new Node();
                
                String s = ""+c;
                
                // Cater for 2 digit number
                if (j+1 < line.length() && line.charAt(j+1) != ',' && line.charAt(j+1) != ']') {
                    j++;
                    s += line.charAt(j);
                }
                
                node.value = Integer.parseInt(s);
                current.nodes.add(node);
            }
        }
        
        return current;
    }
    
    public static void a() throws Exception {
    
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input13.txt"));
        
        int index = 1;
        long total = 0;
        
        while (true) {
    
            final String lineLeft = fileReader.readLine();
            final String lineRight = fileReader.readLine();
    
            Node left = parseNodes(lineLeft);
            Node right = parseNodes(lineRight);
    
            //System.out.println("COMPARE: " + compareTo(left, right));
            
            if (compareTo(left, right) > 0) {
                total += index;
            }
    
            index ++;
            final String space = fileReader.readLine();
    
            if (space == null) {
                break;
            }
        }
    
        System.out.println(total);
        
    }
    
    public static void b() throws Exception {
    
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input13.txt"));
        
        List<Node> allNodes = new ArrayList<>();
    
        while (true) {
        
            final String lineLeft = fileReader.readLine();
            final String lineRight = fileReader.readLine();
        
            Node left = parseNodes(lineLeft);
            Node right = parseNodes(lineRight);
        
            allNodes.add(left);
            allNodes.add(right);
            
            final String space = fileReader.readLine();
        
            if (space == null) {
                break;
            }
        }
        
        Node node1 = parseNodes("[[2]]");
        Node node2 = parseNodes("[[6]]");
        
        allNodes.add(node1);
        allNodes.add(node2);
    
        Collections.sort(allNodes, (x,y)->compareTo(y, x));
        
        System.out.println((allNodes.indexOf(node1)+1) * (allNodes.indexOf(node2)+1));
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
