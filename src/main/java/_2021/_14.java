package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class _14 {
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input14.txt")));
        
        String initialLine = fileReader.readLine();
        
        fileReader.readLine();
        
        List<String> overallList = new ArrayList<>();
        
        for (int j = 0; j < initialLine.length(); j++) {
            overallList.add("" + initialLine.charAt(j));
        }
        
        Map<String, String> pairs = new HashMap<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String a = line.split(" -> ")[0];
            String b = line.split(" -> ")[1];
            
            pairs.put(a, b);
        }
        
        
        // Steps
        
        for (int j = 0; j < 10; j++) {
            
            List<String> newList = new ArrayList<>();
            
            for (int k = 0; k < overallList.size() - 1; k++) {
                
                String compareThis = overallList.get(k) + overallList.get(k + 1);
                
                newList.add(overallList.get(k));
                
                if (pairs.containsKey(compareThis)) {
                    newList.add(pairs.get(compareThis));
                }
            }
            
            newList.add(overallList.get(overallList.size() - 1));
            
            overallList = newList;
        }
        
        // Count
        
        Map<String, Integer> counts = new HashMap<>();
        
        for (String s : overallList) {
            Integer count = counts.get(s);
            
            if (count == null) {
                counts.put(s, 1);
            } else {
                counts.put(s, count + 1);
            }
        }
    
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
    
        for (long count : counts.values()) {
            if (count < min) {
                min = count;
            }
        
            if (count > max) {
                max = count;
            }
        }
    
        System.out.println(max - min);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input14.txt")));
        
        String initialLine = fileReader.readLine();
        
        fileReader.readLine();
        
        List<String> overallList = new ArrayList<>();
        
        for (int j = 0; j < initialLine.length(); j++) {
            overallList.add("" + initialLine.charAt(j));
        }
        
        Map<String, String> substitutionPairs = new HashMap<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String a = line.split(" -> ")[0];
            String b = line.split(" -> ")[1];
            
            substitutionPairs.put(a, b);
        }
        
        // Keep count of each pair
        Map<String, Long> allPairs = new HashMap<>();
        
        for (int k = 0; k < overallList.size() - 1; k++) {
            
            String compareThis = overallList.get(k) + overallList.get(k + 1);
            
            Long count = allPairs.get(compareThis);
            
            if (count == null) {
                allPairs.put(compareThis, 1L);
            } else {
                allPairs.put(compareThis, count + 1);
            }
            
        }
        
        // Steps
        
        for (int j = 0; j < 40; j++) {
            
            Map<String, Long> newAllPairs = new HashMap<>();
            
            for (String pair : allPairs.keySet()) {
                
                if (substitutionPairs.containsKey(pair)) {
                    
                    String stringToAdd = substitutionPairs.get(pair);
                    
                    //System.out.println("string to add: " + stringToAdd);
                    
                    String newPair1 = "" + pair.charAt(0) + stringToAdd;
                    String newPair2 = "" + stringToAdd + pair.charAt(1);
                    
                    //System.out.println("new pair 1: " + newPair1);
                    //System.out.println("new pair 2: " + newPair2);
                    
                    long oldPairCount = allPairs.get(pair);
                    
                    //System.out.println("Old pair count = " + oldPairCount);
                    
                    Long count1 = newAllPairs.get(newPair1);
                    Long count2 = newAllPairs.get(newPair2);
                    
                    if (count1 == null) {
                        newAllPairs.put(newPair1, oldPairCount);
                    } else {
                        newAllPairs.put(newPair1, count1 + oldPairCount);
                    }
                    
                    if (count2 == null) {
                        newAllPairs.put(newPair2, oldPairCount);
                    } else {
                        newAllPairs.put(newPair2, count2 + oldPairCount);
                    }
                    
                    //System.out.println("All pairs at the end: " + newAllPairs);
                }
                
            }
            
            allPairs = newAllPairs;
        }
        
        //System.out.println(allPairs);
        
        // Count
        
        Map<String, Long> counts = new HashMap<>();
        
        for (String s : allPairs.keySet()) {
            
            String element1 = "" + s.charAt(0);
            String element2 = "" + s.charAt(1);
            
            Long count1 = counts.get(element1);
            
            if (count1 == null) {
                counts.put(element1, allPairs.get(s));
            } else {
                counts.put(element1, count1 + allPairs.get(s));
            }
            
            Long count2 = counts.get(element2);
            
            if (count2 == null) {
                counts.put(element2, allPairs.get(s));
            } else {
                counts.put(element2, count2 + allPairs.get(s));
            }
        }
        
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        
        for (long count : counts.values()) {
            long numToCompare = count % 2 == 0 ? count / 2 : (count + 1) / 2;
            
            if (numToCompare < min) {
                min = numToCompare;
            }
            
            if (numToCompare > max) {
                max = numToCompare;
            }
        }
        
        System.out.println(max - min);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}