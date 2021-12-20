package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;


public class _8 {
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input8.txt")));
        
        int totalCount = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String part2 = line.substring(line.indexOf("|"));
            
            List<String> input = Arrays.asList(part2.trim().split(" "));
            
            for (String s : input) {
                if (s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7) {
                    totalCount++;
                }
            }
        }
        
        System.out.println(totalCount);
        
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input8.txt")));
        
        int totalCount = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String part1 = line.substring(0, line.indexOf("|"));
            String part2 = line.substring(line.indexOf("|"));
            
            Map<Integer, Set<String>> digits = new HashMap<>();
            
            Set<Set<String>> fiveSegments = new HashSet<>();
            Set<Set<String>> sixSegments = new HashSet<>();
            
            List<String> digitsStr = Arrays.asList(part1.trim().split(" "));
            
            for (String s : digitsStr) {
                if (s.length() == 2) {
                    Set<String> set = new HashSet<>();
                    
                    for (int j = 0; j < s.length(); j++) {
                        set.add("" + s.charAt(j));
                    }
                    
                    digits.put(1, set);
                } else if (s.length() == 3) {
                    Set<String> set = new HashSet<>();
                    
                    for (int j = 0; j < s.length(); j++) {
                        set.add("" + s.charAt(j));
                    }
                    
                    digits.put(7, set);
                } else if (s.length() == 4) {
                    Set<String> set = new HashSet<>();
                    
                    for (int j = 0; j < s.length(); j++) {
                        set.add("" + s.charAt(j));
                    }
                    
                    digits.put(4, set);
                } else if (s.length() == 7) {
                    Set<String> set = new HashSet<>();
                    
                    for (int j = 0; j < s.length(); j++) {
                        set.add("" + s.charAt(j));
                    }
                    
                    digits.put(8, set);
                } else if (s.length() == 5) {
                    
                    Set<String> set = new HashSet<>();
                    
                    for (int j = 0; j < s.length(); j++) {
                        set.add("" + s.charAt(j));
                    }
                    
                    fiveSegments.add(set);
                    
                } else if (s.length() == 6) {
                    
                    Set<String> set = new HashSet<>();
                    
                    for (int j = 0; j < s.length(); j++) {
                        set.add("" + s.charAt(j));
                    }
                    
                    sixSegments.add(set);
                }
            }
            
            Set<String> fourRemoveOne = new HashSet<>();
            
            for (String s : digits.get(4)) {
                if (!digits.get(1).contains(s)) {
                    fourRemoveOne.add(s);
                }
            }
            
            Set<String> toRemove = null;
            
            for (Set<String> s : fiveSegments) {
                if (s.containsAll(digits.get(1))) {
                    digits.put(3, s);
                    toRemove = s;
                }
            }
            
            fiveSegments.remove(toRemove);
            
            for (Set<String> s : fiveSegments) {
                if (s.containsAll(fourRemoveOne)) {
                    digits.put(5, s);
                    toRemove = s;
                }
            }
            
            fiveSegments.remove(toRemove);
            
            digits.put(2, fiveSegments.iterator().next());
            
            for (Set<String> s : sixSegments) {
                if (!s.containsAll(digits.get(1))) {
                    digits.put(6, s);
                    toRemove = s;
                }
            }
            
            sixSegments.remove(toRemove);
            
            for (Set<String> s : sixSegments) {
                if (s.containsAll(fourRemoveOne)) {
                    digits.put(9, s);
                    toRemove = s;
                }
            }
            
            sixSegments.remove(toRemove);
            
            digits.put(0, sixSegments.iterator().next());
            
            List<String> numbersStr = Arrays.asList(part2.trim().split(" "));
            
            String numberString = "";
            
            for (String s : numbersStr) {
                Set<String> set = new HashSet<>();
                
                for (int j = 0; j < s.length(); j++) {
                    set.add("" + s.charAt(j));
                }
                
                for (Map.Entry<Integer, Set<String>> digitSet : digits.entrySet()) {
                    if (digitSet.getValue().equals(set)) {
                        numberString += digitSet.getKey();
                    }
                }
            }
            
            totalCount += Integer.parseInt(numberString);
        }
        
        System.out.println(totalCount);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}