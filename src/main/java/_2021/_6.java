package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;


public class _6 {
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input6.txt")));
        
        final String line = fileReader.readLine();
        
        List<String> input = Arrays.asList(line.split(","));
        
        List<Integer> fish = input.stream().map(v -> Integer.parseInt(v)).collect(Collectors.toList());
        List<Integer> newFish = new ArrayList<>();
        
        for (int k = 0; k < 80; k++) {
            for (int j = 0; j < fish.size(); j++) {
                
                if (fish.get(j) == 0) {
                    newFish.add(6);
                    newFish.add(8);
                } else {
                    newFish.add(fish.get(j) - 1);
                }
            }
            
            fish = newFish;
            newFish = new ArrayList<>();
        }
        
        System.out.println(fish.size());
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input6.txt")));
        
        final String line = fileReader.readLine();
        
        List<String> input = Arrays.asList(line.split(","));
        
        Map<Integer, Long> countsPerAge = new HashMap<>();
        
        for (int j = 0; j <= 8; j++) {
            countsPerAge.put(j, 0L);
        }
        
        List<Integer> fish = input.stream().map(v -> Integer.parseInt(v)).collect(Collectors.toList());
        
        for (int f : fish) {
            Long countForAge = countsPerAge.get(f);
            countsPerAge.put(f, countForAge + 1);
        }
        
        for (int j = 0; j < 256; j++) {
            long zeroPos = countsPerAge.get(0);
            countsPerAge.put(0, countsPerAge.get(1));
            countsPerAge.put(1, countsPerAge.get(2));
            countsPerAge.put(2, countsPerAge.get(3));
            countsPerAge.put(3, countsPerAge.get(4));
            countsPerAge.put(4, countsPerAge.get(5));
            countsPerAge.put(5, countsPerAge.get(6));
            countsPerAge.put(6, countsPerAge.get(7) + zeroPos);
            countsPerAge.put(7, countsPerAge.get(8));
            countsPerAge.put(8, zeroPos);
        }
        
        System.out.println(countsPerAge.values().stream().reduce(Long::sum).get());
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}