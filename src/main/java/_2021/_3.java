package _2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class _3 {
    
    private static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input3.txt")));
    
        int[] counts = new int[12];
    
        int totalCount = 0;
    
        while (true) {
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            for (int j = 0; j < 12; j++) {
                if (line.charAt(j) == '1') {
                    counts[j]++;
                }
            }
        
            totalCount++;
        }
    
        int gamma = 0;
        int epsilon = 0;
    
        for (int j = 0; j < 12; j++) {
            if (counts[j] > totalCount / 2) {
                gamma += Math.pow(2, 11 - j);
            } else {
                epsilon += Math.pow(2, 11 - j);
            }
        }
    
        System.out.println(gamma * epsilon);
    }
    
    private static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input3.txt")));
    
        Set<String> allCounts1 = new HashSet<>();
        Set<String> allCounts2 = new HashSet<>();
    
        while (true) {
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            allCounts1.add(line);
            allCounts2.add(line);
        }
    
        for (int j = 0; j < 12; j++) {
            int zeroCount = 0;
            int oneCount = 0;
        
            for (String count : allCounts1) {
                if (count.charAt(j) == '0') {
                    zeroCount++;
                } else {
                    oneCount++;
                }
            }
        
            List<String> toRemoveList = new ArrayList<>();
        
            for (String count : allCounts1) {
                if (zeroCount > oneCount) {
                    if (count.charAt(j) == '0') {
                        toRemoveList.add(count);
                    }
                } else {
                    if (count.charAt(j) == '1') {
                        toRemoveList.add(count);
                    }
                }
            }
        
            for (String toRemove : toRemoveList) {
                allCounts1.remove(toRemove);
            
                if (allCounts1.size() == 1) {
                    break;
                }
            }
        
            if (allCounts1.size() == 1) {
                break;
            }
        }
    
        for (int j = 0; j < 12; j++) {
            int zeroCount = 0;
            int oneCount = 0;
        
            for (String count : allCounts2) {
                if (count.charAt(j) == '0') {
                    zeroCount++;
                } else {
                    oneCount++;
                }
            }
        
            List<String> toRemoveList = new ArrayList<>();
        
            for (String count : allCounts2) {
                if (zeroCount <= oneCount) {
                    if (count.charAt(j) == '0') {
                        toRemoveList.add(count);
                    }
                } else {
                    if (count.charAt(j) == '1') {
                        toRemoveList.add(count);
                    }
                }
            }
        
            for (String toRemove : toRemoveList) {
                allCounts2.remove(toRemove);
            
                if (allCounts2.size() == 1) {
                    break;
                }
            }
        
            if (allCounts2.size() == 1) {
                break;
            }
        }
    
        String oxString = allCounts1.iterator().next();
        String co2String = allCounts2.iterator().next();
    
        int oxygen = 0;
        int co2 = 0;
    
        for (int j = 0; j < 12; j++) {
            if (oxString.charAt(j) == '1') {
                oxygen += Math.pow(2, 11 - j);
            }
        
            if (co2String.charAt(j) == '1') {
                co2 += Math.pow(2, 11 - j);
            }
        }
    
        System.out.println(oxygen * co2);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}