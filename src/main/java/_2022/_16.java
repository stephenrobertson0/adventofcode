package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;


public class _16 {
    
    private static class Node {
        Integer value;
        String name;
        List<Node> nodes = new ArrayList<>();
        String childNodeString;
        boolean isOpen = false;
        
        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name +
                    ", nodes=" + nodes.stream().map(v->v.name).collect(Collectors.joining(",")) +
                    ", value=" + value +
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

            for (Node child : nodes) {
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
        
                for (Node child : nodes) {
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
            node.childNodeString = toValves;
            
            allNodes.add(node);
            allNodesByName.put(valveName, node);
            
            if (valveName.equals("AA")) {
                startNode = node;
            }
        }
        
        for (Node node : allNodes) {
            String[] split = node.childNodeString.split(", ");
            for (String s : split) {
                node.nodes.add(allNodesByName.get(s));
            }
        }
    
        //System.out.println(allNodes);
    
        System.out.println(startNode.getMax(30, new HashSet<>(), 0));
    }
    
    public static void b() throws Exception {
    
    
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
