package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;


public class _12 {
    
    private static int countPaths(String startPos, Map<String, Set<String>> graph, List<String> existingVisits) {
        
        //System.out.println("startpos: " + startPos);
        //System.out.println("existing visits: " + existingVisits);
        
        if (startPos.equals("end")) {
            return 1;
        }
        
        if (existingVisits.contains(startPos) && startPos.toLowerCase().equals(startPos)) {
            return 0;
        }
        
        Set<String> destinations = graph.get(startPos);
        
        existingVisits.add(startPos);
        
        int count = 0;
        
        for (String destination : destinations) {
            count += countPaths(destination, graph, new ArrayList<>(existingVisits));
        }
        
        //System.out.println("total count: " + count);
        
        return count;
    }
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input12.txt")));
        
        Map<String, Set<String>> graph = new HashMap<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String point1 = line.split("-")[0];
            String point2 = line.split("-")[1];
            
            Set<String> set1 = graph.get(point1);
            
            if (set1 == null) {
                set1 = new HashSet<>();
                graph.put(point1, set1);
            }
            
            set1.add(point2);
            
            Set<String> set2 = graph.get(point2);
            
            if (set2 == null) {
                set2 = new HashSet<>();
                graph.put(point2, set2);
            }
            
            set2.add(point1);
        }
        
        List<String> existingVisits = new ArrayList<>();
        
        int count = countPaths("start", graph, existingVisits);
        
        System.out.println(count);
    }
    
    private static boolean anyLowercaseCaveCount2(List<String> existingVisits) {
        Set<String> lowerCaseVisits = new HashSet<>();
        
        for (String visit : existingVisits) {
            if (visit.toLowerCase().equals(visit)) {
                if (lowerCaseVisits.contains(visit)) {
                    return true;
                }
                lowerCaseVisits.add(visit);
            }
        }
        
        return false;
    }
    
    private static int countPathsb(String startPos, Map<String, Set<String>> graph, List<String> existingVisits) {
        
        //System.out.println("startpos: " + startPos);
        //System.out.println("existing visits: " + existingVisits);
        
        if (startPos.equals("end")) {
            return 1;
        }
        
        if (startPos.equals("start") && existingVisits.contains(startPos)) {
            return 0;
        }
        
        if (startPos.toLowerCase().equals(startPos) &&
                existingVisits.contains(startPos) &&
                anyLowercaseCaveCount2(existingVisits)
        ) {
            return 0;
        }
        
        Set<String> destinations = graph.get(startPos);
        
        existingVisits.add(startPos);
        
        int count = 0;
        
        for (String destination : destinations) {
            count += countPathsb(destination, graph, new ArrayList<>(existingVisits));
        }
        
        //System.out.println("total count: " + count);
        
        return count;
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input12.txt")));
        
        Map<String, Set<String>> graph = new HashMap<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String point1 = line.split("-")[0];
            String point2 = line.split("-")[1];
            
            Set<String> set1 = graph.get(point1);
            
            if (set1 == null) {
                set1 = new HashSet<>();
                graph.put(point1, set1);
            }
            
            set1.add(point2);
            
            Set<String> set2 = graph.get(point2);
            
            if (set2 == null) {
                set2 = new HashSet<>();
                graph.put(point2, set2);
            }
            
            set2.add(point1);
        }
        
        List<String> existingVisits = new ArrayList<>();
        
        int count = countPathsb("start", graph, existingVisits);
        
        System.out.println(count);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}