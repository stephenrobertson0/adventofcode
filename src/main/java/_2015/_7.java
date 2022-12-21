package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;


public class _7 {
    
    private static class Wire {
    
        public Wire(String name, String str) {
            this.name = name;
            this.str = str;
        }
    
        public Wire(int value) {
            this.value = value;
        }
    
        String name;
        
        Wire input1;
        Wire input2;
        
        int value;
        
        String str;
        
        BiFunction<Wire, Wire, Integer> function;
        
        Integer cachedValue;
        
        public int apply() {
            
            if (cachedValue != null) {
                return cachedValue;
            }
            
            if (input1 == null && input2 == null) {
                return value;
            }
    
            Integer apply = function.apply(input1, input2);
            cachedValue = apply;
            return apply;
        }
    
    }
    
    private static Map<String, Wire> parseWires() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader("./src/main/java/_2015/input/input7.txt"));
        
        List<Wire> wires = new ArrayList<>();
        Map<String, Wire> wiresByName = new HashMap<>();
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            String[] split = line.split(" -> ");
    
            Wire wire = new Wire(split[1], split[0]);
            wires.add(wire);
            wiresByName.put(wire.name, wire);
        }
        
        for (Wire wire : wires) {
        
            String name1 = null;
            String name2 = null;
            
            if (wire.str.contains("AND")) {
                wire.function = (x,y) -> x.apply() & y.apply();
                
                String[] split = wire.str.split(" AND ");
                
                name1 = split[0];
                name2 = split[1];
                
                if (name1.charAt(0) < 59) {
                    wire.input1 = new Wire(Integer.parseInt(name1));
                    name1 = null;
                }
                
            } else if (wire.str.contains("OR")) {
                wire.function = (x,y) -> y.apply() | x.apply();
    
                String[] split = wire.str.split(" OR ");
                name1 = split[0];
                name2 = split[1];
                
            } else if (wire.str.contains("LSHIFT")) {
                wire.function = (x,y) -> x.apply() << y.apply();
    
                String[] split = wire.str.split(" LSHIFT ");
                name1 = split[0];
                Wire nameless = new Wire(Integer.parseInt(split[1]));
                wire.input2 = nameless;
                
            } else if (wire.str.contains("RSHIFT")) {
                wire.function = (x,y) -> x.apply() >> y.apply();
    
                String[] split = wire.str.split(" RSHIFT ");
                name1 = split[0];
                Wire nameless = new Wire(Integer.parseInt(split[1]));
                wire.input2 = nameless;
            
            } else if (wire.str.contains("NOT")) {
                wire.function = (x,y) -> ~x.apply();
    
                name1 = wire.str.substring(4);
                
            } else if (wire.str.charAt(0) > 58) {
                wire.function = (x,y) -> x.apply();
                name1 = wire.str;
            } else {
                wire.function = (x,y) -> x.value;
                wire.value = Integer.parseInt(wire.str);
            }
            
            if (name1 != null) {
                wire.input1 = wiresByName.get(name1);
            }
    
            if (name2 != null) {
                wire.input2 = wiresByName.get(name2);
            }
        
        }
        
        return wiresByName;
    }
    
    private static void a() throws Exception {
    
        Map<String, Wire> wiresByName = parseWires();
    
        System.out.println(wiresByName.get("a").apply());
        
    }
    
    private static void b() throws Exception {
        Map<String, Wire> wiresByName = parseWires();
    
        int answerA = wiresByName.get("a").apply();
    
        wiresByName = parseWires();
        
        wiresByName.get("b").value = answerA;
        
        System.out.println(wiresByName.get("a").apply());
    }
    
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}